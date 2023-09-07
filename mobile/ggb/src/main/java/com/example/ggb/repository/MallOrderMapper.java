
package com.example.ggb.repository;


import com.example.ggb.entity.MallOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface MallOrderMapper {

    int insertSelective(MallOrder record);


}