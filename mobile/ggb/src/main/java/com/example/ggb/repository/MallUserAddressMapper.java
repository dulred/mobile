
package com.example.ggb.repository;


import com.example.ggb.entity.MallUserAddress;
import org.springframework.stereotype.Repository;

@Repository
public interface MallUserAddressMapper {


    MallUserAddress selectByPrimaryKey(Long addressId);


}