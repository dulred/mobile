package com.example.ggb.repository;

import com.example.ggb.entity.TestUser;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserMapper {
    TestUser selectByPrimaryKey(int id);
}
