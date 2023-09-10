
package com.example.ggb.service;


import com.example.ggb.controller.vo.MallOrderDetailVO;
import com.example.ggb.controller.vo.MallShoppingCartItemVO;
import com.example.ggb.entity.MallUser;
import com.example.ggb.entity.MallUserAddress;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;

import java.util.List;

public interface MallOrderService {


    String saveOrder(MallUser loginMallUser, MallUserAddress address, List<MallShoppingCartItemVO> itemsForSave);
//模拟支付接口
    String paySuccess(String orderNo, int payType);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
  MallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);


    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);


    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);




}
