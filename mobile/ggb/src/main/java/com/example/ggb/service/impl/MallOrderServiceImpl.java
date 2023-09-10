
package com.example.ggb.service.impl;


import com.example.ggb.common.*;
import com.example.ggb.controller.vo.MallOrderDetailVO;
import com.example.ggb.controller.vo.MallOrderItemVO;
import com.example.ggb.controller.vo.MallOrderListVO;
import com.example.ggb.controller.vo.MallShoppingCartItemVO;
import com.example.ggb.entity.*;
import com.example.ggb.repository.*;
import com.example.ggb.service.MallOrderService;
import com.example.ggb.util.BeanUtil;
import com.example.ggb.util.NumberUtil;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class MallOrderServiceImpl implements MallOrderService {


    @Autowired
    private GoodsMapper mallGoodsMapper;

    @Autowired
    private MallShoppingCartItemMapper mallShoppingCartItemMapper;

    @Autowired
    private MallOrderAddressMapper mallOrderAddressMapper;

    @Autowired
    private MallOrderItemMapper mallOrderItemMapper;
    @Autowired
    private MallOrderMapper mallOrderMapper;

    @Override
    @Transactional
    public String saveOrder(MallUser loginMallUser, MallUserAddress address, List<MallShoppingCartItemVO> myShoppingCartItems) {
        List<Long> itemIdList = myShoppingCartItems.stream().map(MallShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> goodsIds = myShoppingCartItems.stream().map(MallShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
        List<MallGoods> mallGoods = mallGoodsMapper.selectByPrimaryKeys(goodsIds);
        //检查是否包含已下架商品
        List<MallGoods> goodsListNotSelling = mallGoods.stream()
                .filter(mallGoodsTemp -> mallGoodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
            //goodsListNotSelling 对象非空则表示有下架商品
            MallException.fail(goodsListNotSelling.get(0).getGoodsName() + "已下架，无法生成订单");
        }
        Map<Long, MallGoods> mallGoodsMap = mallGoods.stream().collect(Collectors.toMap(MallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        //判断商品库存
        for (MallShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
            //查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
            if (!mallGoodsMap.containsKey(shoppingCartItemVO.getGoodsId())) {
                MallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            //存在数量大于库存的情况，直接返回错误提醒
            if (shoppingCartItemVO.getGoodsCount() > mallGoodsMap.get(shoppingCartItemVO.getGoodsId()).getStockNum()) {
                MallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        //删除购物项
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(mallGoods)) {
            if (mallShoppingCartItemMapper.deleteBatch(itemIdList) > 0) {
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);
                int updateStockNumResult = mallGoodsMapper.updateStockNum(stockNumDTOS);
                if (updateStockNumResult < 1) {
                    MallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //生成订单号
                String orderNo = NumberUtil.genOrderNo();
                int priceTotal = 0;
                //保存订单
                MallOrder mallOrder = new MallOrder();
                mallOrder.setOrderNo(orderNo);
                mallOrder.setUserId(loginMallUser.getUserId());
                //总价
                for (MallShoppingCartItemVO mallShoppingCartItemVO : myShoppingCartItems) {
                    priceTotal += mallShoppingCartItemVO.getGoodsCount() * mallShoppingCartItemVO.getSellingPrice();
                }
                if (priceTotal < 1) {
                    MallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                mallOrder.setTotalPrice(priceTotal);
                String extraInfo = "";
                mallOrder.setExtraInfo(extraInfo);
                //生成订单项并保存订单项纪录
                if (mallOrderMapper.insertSelective(mallOrder) > 0) {
                    //生成订单收货地址快照，并保存至数据库
                    MallOrderAddress mallOrderAddress = new MallOrderAddress();
                    BeanUtil.copyProperties(address, mallOrderAddress);
                    mallOrderAddress.setOrderId(mallOrder.getOrderId());
                    //生成所有的订单项快照，并保存至数据库
                    List<MallOrderItem> mallOrderItems = new ArrayList<>();
                    for (MallShoppingCartItemVO mallShoppingCartItemVO : myShoppingCartItems) {
                        MallOrderItem mallOrderItem = new MallOrderItem();
                        //使用BeanUtil工具类将mallShoppingCartItemVO中的属性复制到mallOrderItem对象中
                        BeanUtil.copyProperties(mallShoppingCartItemVO, mallOrderItem);
                        //MallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
                        mallOrderItem.setOrderId(mallOrder.getOrderId());
                        mallOrderItems.add(mallOrderItem);
                    }
                    //保存至数据库
                    if (mallOrderItemMapper.insertBatch(mallOrderItems) > 0 && mallOrderAddressMapper.insertSelective(mallOrderAddress) > 0) {
                        //所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
                        return orderNo;
                    }
                    MallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                MallException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            MallException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        MallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }



    @Override
    public String paySuccess(String orderNo, int payType) {
       MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder != null) {
            //订单状态判断 非待支付状态下不进行修改操作
            if (mallOrder.getOrderStatus().intValue() != MallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            mallOrder.setOrderStatus((byte) MallOrderStatusEnum.ORDER_PAID.getOrderStatus());
            mallOrder.setPayType((byte) payType);
            mallOrder.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            mallOrder.setPayTime(new Date());
            mallOrder.setUpdateTime(new Date());
            if (mallOrderMapper.updateByPrimaryKeySelective(mallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }


    @Override
    public MallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        if (!userId.equals(mallOrder.getUserId())) {
            MallException.fail(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        List<MallOrderItem> orderItems = mallOrderItemMapper.selectByOrderId(mallOrder.getOrderId());
        //获取订单项数据
        if (CollectionUtils.isEmpty(orderItems)) {
            MallException.fail(ServiceResultEnum.ORDER_ITEM_NOT_EXIST_ERROR.getResult());
        }
        List<MallOrderItemVO> mallOrderItemVOS = BeanUtil.copyList(orderItems, MallOrderItemVO.class);
        MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
        BeanUtil.copyProperties(mallOrder, mallOrderDetailVO);
        mallOrderDetailVO.setOrderStatusString(MallOrderStatusEnum.getMallOrderStatusEnumByStatus(mallOrderDetailVO.getOrderStatus()).getName());
        mallOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(mallOrderDetailVO.getPayType()).getName());
        mallOrderDetailVO.setMallOrderItemVOS(mallOrderItemVOS);
        return mallOrderDetailVO;
    }


    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = mallOrderMapper.getTotalMallOrders(pageUtil);
        List<MallOrder> mallOrders = mallOrderMapper.findMallOrderList(pageUtil);
        List<MallOrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //数据转换 将实体类转成vo
            orderListVOS = BeanUtil.copyList(mallOrders, MallOrderListVO.class);
            //设置订单状态中文显示值
            for (MallOrderListVO mallOrderListVO : orderListVOS) {
                mallOrderListVO.setOrderStatusString(MallOrderStatusEnum.getMallOrderStatusEnumByStatus(mallOrderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = mallOrders.stream().map(MallOrder::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<MallOrderItem> orderItems = mallOrderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<MallOrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(MallOrderItem::getOrderId));
                for (MallOrderListVO mallOrderListVO : orderListVOS) {
                    //封装每个订单列表对象的订单项数据
                    if (itemByOrderIdMap.containsKey(mallOrderListVO.getOrderId())) {
                        List<MallOrderItem> orderItemListTemp = itemByOrderIdMap.get(mallOrderListVO.getOrderId());
                        //将MallOrderItem对象列表转换成MallOrderItemVO对象列表
                        List<MallOrderItemVO> mallOrderItemVOS = BeanUtil.copyList(orderItemListTemp, MallOrderItemVO.class);
                        mallOrderListVO.setMallOrderItemVOS(mallOrderItemVOS);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }


    @Override
    @Transactional
    public String cancelOrder(String orderNo, Long userId) {
       MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(mallOrder.getUserId())) {
               MallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            }
            //订单状态判断
            if (mallOrder.getOrderStatus().intValue() ==MallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus()
                    || mallOrder.getOrderStatus().intValue() ==MallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()
                    || mallOrder.getOrderStatus().intValue() ==MallOrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
                    || mallOrder.getOrderStatus().intValue() ==MallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            //修改订单状态&&恢复库存
            if (mallOrderMapper.closeOrder(Collections.singletonList(mallOrder.getOrderId()),MallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0 && recoverStockNum(Collections.singletonList(mallOrder.getOrderId()))) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String finishOrder(String orderNo, Long userId) {
       MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(mallOrder.getUserId())) {
                return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
            }
            //订单状态判断 非出库状态下不进行修改操作
            if (mallOrder.getOrderStatus().intValue() !=MallOrderStatusEnum.ORDER_EXPRESS.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            mallOrder.setOrderStatus((byte)MallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            mallOrder.setUpdateTime(new Date());
            if (mallOrderMapper.updateByPrimaryKeySelective(mallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }


    /**
     * 恢复库存
     *
     * @param orderIds
     * @return
     */
    public Boolean recoverStockNum(List<Long> orderIds) {
        //查询对应的订单项
        List<MallOrderItem> mallOrderItems = mallOrderItemMapper.selectByOrderIds(orderIds);
        //获取对应的商品id和商品数量并赋值到StockNumDTO对象中
        List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(mallOrderItems, StockNumDTO.class);
        //执行恢复库存的操作
        int updateStockNumResult = mallGoodsMapper.recoverStockNum(stockNumDTOS);
        if (updateStockNumResult < 1) {
            MallException.fail(ServiceResultEnum.CLOSE_ORDER_ERROR.getResult());
            return false;
        } else {
            return true;
        }
    }



}
