
package com.example.ggb.service;

import com.example.ggb.entity.MallGoods;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;

public interface MallGoodsService {

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchMallGoods(PageQueryUtil pageUtil);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
   MallGoods getMallGoodsById(Long id);


}
