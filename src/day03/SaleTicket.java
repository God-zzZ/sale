package day03;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket //资源类
{
    private int number = 30;
	/*public synchronized void sale()
	{
		if (number > 0)
		{
			System.out.println(Thread.currentThread().getName()+"\t卖出第："+(number--)+"\t还剩下："+number);
		}
	}*/

    Lock lock = new ReentrantLock();
    public void sale()
    {
        lock.lock();
        try {
            if (number > 0)
            {
                System.out.println(Thread.currentThread().getName()+"\t卖出第："+(number--)+"\t还剩下："+number);
            }
        } finally {
            lock.unlock();
        }
    }
}


/**
 * 题目：三个售票员   卖出   30张票
 * 目的：如何写出企业级的多线程程序
 *
 *  ** 高内聚，低耦合
 *
 *  高内低耦的前提下，线程        操作        资源类
 *
 */
public class SaleTicket {
    public static void main(String[] args) throws Exception{ // main一切程序入口
        Ticket ticket = new Ticket();

        ExecutorService executorService = new ThreadPoolExecutor(
                3,
                6,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );


        //ExecutorService executorService = Executors.newFixedThreadPool(3);
        //ExecutorService executorService = Executors.newCachedThreadPool();
        //ExecutorService executorService = Executors.newSingleThreadExecutor();


        try {
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> {  // executor或者submit
                    ticket.sale();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}

