package day03;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo
{
    public static void main(String[] args) throws Exception{

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++)
        {
            new Thread(() -> {
               /* try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                System.out.println(Thread.currentThread().getName()+"\t 离开教室");
                countDownLatch.countDown(); //等所有的线程都结束了，主线程再执行
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        //countDownLatch.await(2L, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName()+"\t 关门离开"); //主线程(main)

    }
}









































