package day03;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


class MyThread implements Callable<String>
{
    @Override
    public String call() throws Exception
    {
        System.out.println(Thread.currentThread().getName()+"\t------come in call");
        return "java0615";
    }
}

/**
 * 第三种获得线程的方法
 *
 * get方法一般放在最后一行
 */
public class CallableDemo
{
    public static void main(String[] args) throws Exception
    {
        //FutureTask<String> futureTask = new FutureTask<>(new MyThread());
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            System.out.println(Thread.currentThread().getName()+"\t------come in call");
            return "java0615";
        });

        new Thread(futureTask, "AAA").start(); //A线程

        String result = futureTask.get(); //跳过时间长的线程，先执行时间短的main线程
        System.out.println(result);

        System.out.println("-----主线程: "+Thread.currentThread().getName()); //main线程
    }
}






























