package com.example.ggb.service;

import com.example.ggb.entity.MallUser;
import com.example.ggb.entity.TestUser;

public interface MallUserService {
   MallUser selectByPrimaryKey (Long id);

   MallUser selectByLoginName(String loginName);
}
