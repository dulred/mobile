
package com.example.ggb.service;

import com.example.ggb.controller.vo.MallIndexCategoryVO;

import java.util.List;

public interface MallCategoryService {


    /**
     * 返回分类数据(首页调用)
     *
     * @return
     */
    List<MallIndexCategoryVO> getCategoriesForIndex();




}
