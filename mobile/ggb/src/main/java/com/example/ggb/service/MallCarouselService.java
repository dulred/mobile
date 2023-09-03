package com.example.ggb.service;

import com.example.ggb.controller.vo.MallIndexCarouselVO;

import java.util.List;

public interface MallCarouselService {

    /**
     *
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return @return {@code List<MallIndexCarouselVO> }
     * @author dulred
     * @date 2023/09/02
     */
    List<MallIndexCarouselVO> getCarouselsForIndex(int number);
}
