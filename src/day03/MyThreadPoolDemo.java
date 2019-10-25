package day03;

import java.util.Random;
import java.util.concurrent.*;

public class  MyThreadPoolDemo
{
    public static void main(String[] args)
    {
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                //new ThreadPoolExecutor.AbortPolicy()
                //new ThreadPoolExecutor.CallerRunsPolicy()
                //new ThreadPoolExecutor.DiscardOldestPolicy()
                new ThreadPoolExecutor.DiscardPolicy()
        );

        try {
            for (int i = 1; i <= 10; i++) //模拟20个客户来银行办理业务，提交请求。customer
            {
                executorService.submit(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务"+new Random().nextInt(10));
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }


     /*   //ExecutorService executorService = Executors.newFixedThreadPool(5); //一池五线程
        //ExecutorService executorService = Executors.newSingleThreadExecutor(); //一池1线程
        ExecutorService executorService = Executors.newCachedThreadPool(); //一池N线程

        try {
                for (int i = 1; i <= 20; i++) //模拟20个客户来银行办理业务，提交请求。customer
                {
                    executorService.submit(() -> {
                        System.out.println(Thread.currentThread().getName()+"\t 办理业务"+new Random().nextInt(10));
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                executorService.shutdown();
            }*/

    }
}
