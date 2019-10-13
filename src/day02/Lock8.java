package day02;

import java.util.concurrent.TimeUnit;

class Phone    // Phone.java -> Phone.class -> load...JVM里面形成模板Class<Phone>
{
    public static synchronized void sendEmail() throws Exception
    {
        TimeUnit.SECONDS.sleep(4);
        System.out.println("-----sendEmail");
    }

    public synchronized void sendSMS() throws Exception
    {
        System.out.println("-----sendSMS");
    }

    public void sayHello()
    {
        System.out.println("-----sayHello");
    }
}

/**
 * 题目：多线程8锁
 *      1.标准访问，请问先打印邮件还是短信？  ：先email后SMS
 *      2.邮件里面新增暂停4秒钟的方法，请问先打印邮件还是短信？  ：先email后SMS
 *      3.新增普通的sayHello方法，请问打印邮件还是sayHello？  ：先sayHello后email
 *      4.有2部手机，请问先打印邮件还是短信？  : 先SMS后email
 *      5.2个静态同步方法，同一部手机，请问先打印邮件还是短信？  : 先email后SMS
 *      6.2个静态同步方法，2部手机，请问先打印邮件还是短信？  : 先email后SMS
 *      7.1个静态同步方法，1个普通同步方法，1部手机，请问先打印邮件还是短信？  : 先SMS后email
 *      8.1个静态同步方法，1个普通同步方法，2部手机，请问先打印邮件还是短信？  : 先SMS后email
 */

public class Lock8
{
    public static void main(String[] args) throws Exception
    {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"A").start(); // A:email

        Thread.sleep(100);

        new Thread(() -> {
            try
            {
                //phone.sendSMS();
                phone2.sendSMS();
                //phone.sayHello();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        },"B").start(); // B:SMS



    }


}
