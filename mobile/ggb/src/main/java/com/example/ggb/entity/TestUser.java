package com.example.ggb.entity;


import lombok.Data;

@Data
public class TestUser {
    private int test_id;
    private String test_name;
    private String password_Sha256;
}
