package com.example.ggb.repository;

import com.example.ggb.entity.MallGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper {
    List<MallGoods> selectByPrimaryKeys(List<Long> goodsIds);
}
