
package com.example.ggb.controller;

import com.example.ggb.common.Constants;
import com.example.ggb.common.MallException;
import com.example.ggb.config.annotation.TokenToMallUser;
import com.example.ggb.controller.vo.MallSearchGoodsVO;
import com.example.ggb.entity.MallUser;
import com.example.ggb.service.MallGoodsService;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;
import com.example.ggb.util.Result;
import com.example.ggb.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "商品相关接口")
@RequestMapping("/api/v1")
public class MallGoodsAPI {

    private static final Logger logger = LoggerFactory.getLogger(MallGoodsAPI.class);

    @Resource
    private MallGoodsService newBeeMallGoodsService;

    @GetMapping("/search")
    @ApiOperation(value = "商品搜索接口", notes = "根据关键字和分类id进行搜索")
    public Result<PageResult<List<MallSearchGoodsVO>>> search(@RequestParam(required = false) @ApiParam(value = "搜索关键字") String keyword,
                                                              @RequestParam(required = false) @ApiParam(value = "分类id") Long goodsCategoryId,
                                                              @RequestParam(required = false) @ApiParam(value = "orderBy") String orderBy,
                                                              @RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                                                              @TokenToMallUser MallUser loginMallUser) {
        
        logger.info("goods search api,keyword={},goodsCategoryId={},orderBy={},pageNumber={},userId={}", keyword, goodsCategoryId, orderBy, pageNumber, loginMallUser.getUserId());

        Map params = new HashMap(8);
        //两个搜索参数都为空，直接返回异常
        if (goodsCategoryId == null && !StringUtils.hasText(keyword)) {
           MallException.fail("非法的搜索参数");
        }
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("goodsCategoryId", goodsCategoryId);
        params.put("page", pageNumber);
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //对keyword做过滤 去掉空格
        if (StringUtils.hasText(keyword)) {
            params.put("keyword", keyword);
        }
        if (StringUtils.hasText(orderBy)) {
            params.put("orderBy", orderBy);
        }
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.searchMallGoods(pageUtil));
    }



}
