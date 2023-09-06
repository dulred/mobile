package com.example.ggb.repository;

import com.example.ggb.entity.MallGoods;
import com.example.ggb.util.PageQueryUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper {
    List<MallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<MallGoods> findMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalMallGoodsBySearch(PageQueryUtil pageUtil);

    MallGoods selectByPrimaryKey(Long goodsId);


}
