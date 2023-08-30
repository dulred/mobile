package com.example.ggb.controller.param;

import java.io.Serializable;

import com.example.ggb.config.annotation.StrVal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCooksParams implements Serializable {
    private static final long serialVersionUID = 2145635852726787978L;
    @StrVal(info = "菜品名称",max = 26)
    private String name;
    private String src;
    @StrVal(info = "菜品详情",max = 250)
    private String detail;
}