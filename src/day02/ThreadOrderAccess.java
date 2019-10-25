package day02;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource
{
    private int flag = 1; // A:1, B:2, C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition(); //（condition:顺序执行，精准唤醒）
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5()
    {
        lock.lock();
        try {
            //1.判断
            while(flag != 1)
            {
                //A系统就要停
                c1.await();
            }
            //2.干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3.通知
            flag = 2;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10()
    {
        lock.lock();
        try {
            //1.判断
            while(flag != 2)
            {
                //B系统就要停
                c3.await();
            }
            //2.干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3.通知
            flag = 3;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15()
    {
        lock.lock();
        try {
            //1.判断
            while(flag != 3)
            {
                //C系统就要停
                c1.await();
            }
            //2.干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3.通知
            flag = 1;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 多线程之间按顺序调用，实现 A->B->C
 * 三个线程启动，要求如下：
 *      AA打印5次，BB打印10次，CC打印15次
 *      接着
 *      AA打印5次，BB打印10次，CC打印15次
 *      ...来10轮
 *
 *  1. 高聚低耦前提下，线程操作资源类
 *  2. 判断/干活/通知
 *  3. 多线程交互中，防止多线程的虚假唤醒,判断时候用while而不是if
 *  4. 注意判断标志位的更新
 */

public class ThreadOrderAccess
{
    public static void main(String[] args)
    {
        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print5();
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print10();
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print15();
            }
        },"C").start();
    }
}




























