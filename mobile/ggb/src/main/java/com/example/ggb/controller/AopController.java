package com.example.ggb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ggb.config.annotation.PCheck;
import com.example.ggb.controller.param.AddCooksParams;
import com.example.ggb.entity.User;
import com.example.ggb.util.Result;

import io.swagger.v3.oas.annotations.parameters.RequestBody;



@RestController
public class AopController {
    
    @PCheck
    @PostMapping("/add")
    public void addCooks(@RequestBody AddCooksParams addCooksParams){
        System.out.println("nshizhu");
    }

}
