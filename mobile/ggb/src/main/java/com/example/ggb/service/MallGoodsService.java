
package com.example.ggb.service;

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
}
