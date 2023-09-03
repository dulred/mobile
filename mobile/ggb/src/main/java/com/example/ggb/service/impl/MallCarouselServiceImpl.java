package com.example.ggb.service.impl;

import com.example.ggb.controller.vo.MallIndexCarouselVO;
import com.example.ggb.entity.Carousel;
import com.example.ggb.repository.CarouselMapper;
import com.example.ggb.service.MallCarouselService;
import com.example.ggb.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MallCarouselServiceImpl implements MallCarouselService {

    @Autowired
    CarouselMapper carouselMapper;
    @Override
    public List<MallIndexCarouselVO> getCarouselsForIndex(int number) {
        List<MallIndexCarouselVO> mallIndexCarouselVOS = new ArrayList<>(number);
        List<Carousel> carousels = carouselMapper.findCarouselsByNum(number);

        if (!CollectionUtils.isEmpty(carousels)){
            mallIndexCarouselVOS = BeanUtil.copyList(carousels,MallIndexCarouselVO.class);
        }

        return mallIndexCarouselVOS;
    }






}
