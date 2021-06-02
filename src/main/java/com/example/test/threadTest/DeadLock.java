package com.example.test.threadTest;

/**
 * @program: test
 * @description:
 * @author: Zqm
 * @create: 2021-02-22 13:37
 **/
public class DeadLock extends Thread {
    //构造方法
    DeadLock(int choice){
        this.choice=choice;
    }

    //用static来保证只有一份资源
    static A a = new A();
    static B b = new B();

    int choice;

    public static void main(String[] args) {
        //创建线程
        DeadLock deadLock1 = new DeadLock(0);
        DeadLock deadLock2 = new DeadLock(1);
        deadLock1.start();
        deadLock2.start();

    }
//    @Override
//    public void run() {
//        if (choice == 0){
//            synchronized (a){   //获得a的锁
//                System.out.println(Thread.currentThread().getName()+"if...lockA");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                synchronized (b){  //一秒后获得b的锁
//                    System.out.println(Thread.currentThread().getName()+"if...LockB");
//                }
//            }
//        }else {
//            synchronized (b){  //获得b的锁
//                System.out.println(Thread.currentThread().getName()+"else..LockB");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                synchronized (a){  //一秒后获得a的锁
//                    System.out.println(Thread.currentThread().getName()+"else..LockA");
//                }
//            }
//        }
//    }
    @Override
    public void run() {
        if (choice == 0){
            synchronized (a){   //获得a的锁
                System.out.println(Thread.currentThread().getName()+"if...lockA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //不嵌套在a的同步代码块中
            synchronized (b){  //一秒后获得b的锁
                System.out.println(Thread.currentThread().getName()+"if...LockB");
            }
        }else {
            synchronized (b){  //获得b的锁
                System.out.println(Thread.currentThread().getName()+"else..LockB");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //不嵌套在b的同步代码块中
            synchronized (a){  //一秒后获得a的锁
                System.out.println(Thread.currentThread().getName()+"else..LockA");
            }
        }
    }







}

class A{

}

class B{

}
