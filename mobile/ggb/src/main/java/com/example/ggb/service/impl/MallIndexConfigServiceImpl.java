package com.example.ggb.service.impl;

import com.example.ggb.controller.vo.MallIndexConfigGoodsVO;
import com.example.ggb.entity.IndexConfig;
import com.example.ggb.entity.MallGoods;
import com.example.ggb.repository.GoodsMapper;
import com.example.ggb.repository.IndexConfigMapper;
import com.example.ggb.service.MallIndexConfigService;
import com.example.ggb.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MallIndexConfigServiceImpl implements MallIndexConfigService {

    @Autowired
    IndexConfigMapper indexConfigMapper;

    @Autowired
    GoodsMapper goodsMapper;

@Override
public List<MallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number) {
    List<MallIndexConfigGoodsVO> MallIndexConfigGoodsVOS = new ArrayList<>(number);
    List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigsByTypeAndNum(configType,number);
    if (!CollectionUtils.isEmpty(indexConfigs)){
        //取出所有的goodsId
        List<Long> goodsIds = indexConfigs.stream().map(IndexConfig::getGoodsId).collect(Collectors.toList());
        List<MallGoods>MallGoods = goodsMapper.selectByPrimaryKeys(goodsIds);
        MallIndexConfigGoodsVOS = BeanUtil.copyList(MallGoods,MallIndexConfigGoodsVO.class);
        for(MallIndexConfigGoodsVO mallIndexConfigGoodsVO : MallIndexConfigGoodsVOS ) {

                String goodsName = mallIndexConfigGoodsVO.getGoodsName();
                String goodsIntro = mallIndexConfigGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 30) {
                    goodsName = goodsName.substring(0, 30) + "...";
                    mallIndexConfigGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 22) {
                    goodsIntro = goodsIntro.substring(0, 22) + "...";
                    mallIndexConfigGoodsVO.setGoodsIntro(goodsIntro);
                }

        }


    }


    return MallIndexConfigGoodsVOS;
}






}
