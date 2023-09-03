package com.example.ggb.controller;

import com.example.ggb.common.Constants;
import com.example.ggb.common.IndexConfigTypeEnum;
import com.example.ggb.controller.vo.IndexInfoVO;
import com.example.ggb.controller.vo.MallIndexCarouselVO;
import com.example.ggb.controller.vo.MallIndexConfigGoodsVO;
import com.example.ggb.service.MallCarouselService;
import com.example.ggb.service.MallIndexConfigService;
import com.example.ggb.util.Result;
import com.example.ggb.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1" ,tags="商城首页接口")
@RequestMapping("/api/v1")
public class MallIndexAPI {
    @Resource
    private MallCarouselService newBeeMallCarouselService;

    @Resource
    private MallIndexConfigService newBeeMallIndexConfigService;

    @GetMapping("/index-infos")
    @ApiOperation(value = "获取首页数据", notes = "轮播图、新品、推荐等")
    public Result<IndexInfoVO> indexInfo() {
        IndexInfoVO indexInfoVO = new IndexInfoVO();
        List<MallIndexCarouselVO> carousels = newBeeMallCarouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<MallIndexConfigGoodsVO> hotGoodses = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
        List<MallIndexConfigGoodsVO> newGoodses = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
        List<MallIndexConfigGoodsVO> recommendGoodses = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);
        indexInfoVO.setCarousels(carousels);
        indexInfoVO.setHotGoodses(hotGoodses);
        indexInfoVO.setNewGoodses(newGoodses);
        indexInfoVO.setRecommendGoodses(recommendGoodses);
        return ResultGenerator.genSuccessResult(indexInfoVO);
    }
}
