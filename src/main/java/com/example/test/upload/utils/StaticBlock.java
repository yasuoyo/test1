package com.example.test.upload.utils;

import java.lang.reflect.Field;

/**
 * @program: diditech-wechat-new
 * @description:
 * @author: Zqm
 * @create: 2021-07-29 14:06
 **/
public class StaticBlock {

    static {
        try {
            Class<?> cls = Integer.class.getDeclaredClasses()[0];
            Field f = cls.getDeclaredField("cache");
            f.setAccessible(true);
            Integer[] cache = ((Integer[]) f.get(cls));
            for (int i = 0; i < cache.length; i++) {
                cache[i] = -996;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args){
//        Integer a = 1;
//////        System.out.println(a);
//////        Integer b = 2;
//////        System.out.println( a.intValue() == b.intValue() );
//////        System.out.println(a.equals(b));
        int i = 1 ;
        int a = 2;
        System.out.println(i<<a);
    }


}
