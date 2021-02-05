package com.example.test.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @program: test1
 * @description:
 * @author: Zqm
 * @create: 2021-02-05 09:20
 **/
@Data
@AllArgsConstructor
public class Result {
    private boolean flag;
    private Integer code;
    private String message;
    private Map<String,String> map;

}
