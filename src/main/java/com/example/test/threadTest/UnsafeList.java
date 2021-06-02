package com.example.test.threadTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: test
 * @description:
 * @author: Zqm
 * @create: 2021-02-22 11:40
 **/
public class UnsafeList {
    public static void main(String[] args) {

        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 10000 ; i++) {
            //开启线程
            new Thread(()->{
                //增加同步代码块，把list锁住（锁的一般是需要变化的量，即需要增删改的对象）
                synchronized (list){
                    //向集合中添加线程名字
                    list.add(Thread.currentThread().getName());
                }

            }).start();

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //查看集合大小
        System.out.println(list.size());
    }
}
