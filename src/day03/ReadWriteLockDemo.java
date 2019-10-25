package day03;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache
{
    private volatile Map<String, String> map = new HashMap<>();

    // 这种方法 控制了写，不控制读
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public void put(String key, String value)
    {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 写入开始");
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"\t 写入结束");
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key)
    {
        readWriteLock.readLock().lock();
        try {
            String result = null;
            System.out.println(Thread.currentThread().getName()+"\t 读入开始");
            result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读入结束result: "+result);
        }finally {
            readWriteLock.readLock().unlock();
        }
    }


    /* //原始方法，高并发时不推荐使用，只控制写，读不用控制
    private Lock lock = new ReentrantLock();
    public void put(String key, String value)
    {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 写入开始");
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"\t 写入结束");
        }finally {
            lock.unlock();
        }
    }

    public void get(String key)
    {
        lock.lock();
        try {
            String result = null;
            System.out.println(Thread.currentThread().getName()+"\t 读取开始");
            result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取结束result: "+result);
        } finally {
            lock.unlock();
        }
    }*/
}

/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是
 * 如果有一个线程想去写共享资源来，就不应该再有其它线程可以对该资源进行读或写
 * 小总结：
 *          读-读能共存
 *          读-写不能共存
 *          写-写不能共存
 */

public class ReadWriteLockDemo
{
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(finalI+"", finalI+"");
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(finalI+"");
            },String.valueOf(i)).start();
        }

    }

}
