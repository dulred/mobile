
package com.example.ggb.service.impl;


import com.example.ggb.common.MallException;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.entity.MallUserAddress;
import com.example.ggb.repository.MallUserAddressMapper;
import com.example.ggb.service.MallUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MallUserAddressServiceImpl implements MallUserAddressService {

    @Autowired
    MallUserAddressMapper userAddressMapper;
    @Override
    public MallUserAddress getMallUserAddressById(Long addressId) {
        MallUserAddress mallUserAddress = userAddressMapper.selectByPrimaryKey(addressId);
        if (mallUserAddress == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return mallUserAddress;
    }

}
