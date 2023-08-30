package com.example.ggb.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.example.ggb.config.annotation.StrVal;
import com.example.ggb.config.exception.ParamsException;

@Component
public class PcheckUtil {
 
    /***
     * 校验传过来的参数
     * @param: [arg]
     * @return: void
     * @author: kevin
     * @date: 2023/5/10 9:58
     */
    public static void validate(Object arg) throws IllegalAccessException {
        // 获取对象中所有的字段和方法
        Field[] fields = arg.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(StrVal.class)){
                //存在某个注解就进行对应的处理
                StrVal annotation = field.getAnnotation(StrVal.class);
                String regex = annotation.regex();
                int max = annotation.max();
                int min = annotation.min();
                String info = annotation.info();
                boolean ifNull = annotation.ifNull();
                //设置属性可见性
                field.setAccessible(true);
                String value = (String) field.get(arg);
                //先判断空
                //如果可以为空 值也为空就不进行处理
                if (ifNull && StringUtils.isBlank(value)){
                    continue;
                }
                if (!ifNull && StringUtils.isBlank(value)){
                    throw new ParamsException(info+"：不能为空！");
                }
                //如果正则不匹配就直接返回
                if (StringUtils.isNotBlank(regex)){
                    if (!value.matches(regex)){
                        throw new ParamsException(info+"：格式不正确！");
                    }
                }
                //最后判断长度
                if (value.length()<min || value.length()>max){
                    throw new ParamsException(info+"：请将字符长度控制在"+min+"~"+max+"之间");
                }
            }
        }
    }
}