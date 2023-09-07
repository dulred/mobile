
package com.example.ggb.service;

import com.example.ggb.entity.MallUserAddress;

public interface MallUserAddressService {


    /**
     * 获取收货地址详情
     *
     * @param addressId
     * @return
     */
    MallUserAddress getMallUserAddressById(Long addressId);



}
