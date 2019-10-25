package day03;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 对 多资源
 */
public class SemaphoreDemo
{
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3); //模拟三个停车位

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                boolean flag = false;
                try {
                    semaphore.acquire(); //获得
                    flag = true;
                    System.out.println(Thread.currentThread().getName()+"\t 抢到车位");
                    try {
                        //暂停3秒线程
                        TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t ---离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (flag) // 此时flag == true
                    {
                        semaphore.release(); //释放
                    }
                }
            }, String.valueOf(i)).start();
        }


    }
}
