package com.mkfree;

import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

/**
 * Created by liyi on 2017/8/27.
 * redis spring简单例子
 */
public class TestRedis {
    public static void main(String[] args) throws InterruptedException{
        ApplicationContext app=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        //这里已经配置好，属于一个redis的服务接口
        RedisService redisService=(RedisService)app.getBean("redisService");
        String ping=redisService.ping();
        System.out.println(ping);
        //首先看下redis服务里是否有数据
        long dbSizeStart=redisService.dbsize();
        System.out.println(dbSizeStart);
        redisService.set("username1","dda");//默认存活时间30分钟
        String username1=redisService.get("username1");
        System.out.println(username1);
        redisService.set("username2","ddb",1);//设置存活1秒
        String username2=redisService.get("username2");
        System.out.println(username2);
        Thread.sleep(2000);//睡眠
        String liveUsername2=redisService.get(username2);
        System.out.println(liveUsername2);//输出null
        boolean exist=redisService.exists("username1");
        System.out.println(exist);
        Set<String> keys=redisService.keys("*");//查看所有的keys
        System.out.println(keys);//只有username1 2已清空
        redisService.set("username3","ddc");
        String username3=redisService.get("username3");
        System.out.println(username3);
        redisService.del("username3");
        String username3_3=redisService.get("username3");
        System.out.println(username3_3);//null则已删除数据
        long dbSizeEnd=redisService.dbsize();
        System.out.println(dbSizeEnd);
        //redisService.flushDB();//清空redis所有数据
    }
}









