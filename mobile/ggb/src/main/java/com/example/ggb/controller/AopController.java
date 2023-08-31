package com.example.ggb.controller;


import com.example.ggb.config.annotation.PCheck;
import com.example.ggb.controller.param.AddCooksParams;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AopController {
    
    @PCheck
    @PostMapping("/add")
    public void addCooks(@RequestBody AddCooksParams addCooksParams){
        System.out.println("nshizhu");
    }

}
