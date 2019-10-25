package day02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 1.故障现象
 * 		java.util.ConcurrentModificationException 异常
 * 2.导致原因
 *
 * 3.解决方法
 * 		3.1 Vector()
 * 		3.2 List<String> list = Collections.synchronizedList();
 * 		3.3 List<String> list = new CopyOnWriteArrayList();  --->  读写分离，写时复制
 * 4.优化建议
 *
 */

public class NotSafeDemo
{

    public static void main(String[] args)
    {
        //Map<String, String> map = new HashMap<>();
        //Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 6));
                System.out.println(map);
            },String.valueOf(i)).start();
        }

    }


    public static void setNotSafe()
    {
        //Set<String> set = new HashSet<>();
        //Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 6));
                System.out.println(set);
            },String.valueOf(i)).start();
        }

    }

    public void listNotSafe()
    {
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();

				/*list.add("a");
				list.add("b");
				list.add("c");
				System.out.println(list);

				java.util.ConcurrentModificationException
				*/

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 6));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }

}
