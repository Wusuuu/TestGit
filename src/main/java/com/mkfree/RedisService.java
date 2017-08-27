package com.mkfree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by liyi on 2017/8/27.
 * 封装redis缓存服务器服务接口
 */
public class RedisService {
    private RedisService(){}
    //操作redis客户端
    private static Jedis jedis;
    @Autowired
    @Qualifier("jedisConnectionFactory")
    private JedisConnectionFactory jedisConnectionFactory;

    /**
     * 获取一个jedis客户端
     * @return
     */
    private Jedis getJedis(){
        if (jedis == null){
            return jedisConnectionFactory.getShardInfo().createResource();
        }
        return jedis;
    }

    /**
     * 通过key删除（字节）
     * @param key
     */
    public void del(byte[] key){
        this.getJedis().del(key);
    }
    public void del(String key){
        this.getJedis().del(key);
    }
    public void set(byte[] key,byte[] value,int liveTime){
        this.set(key,value);
        this.getJedis().expire(key,liveTime);
    }
    public void set(String key,String value,int liveTime){
        this.set(key,value);
        this.getJedis().expire(key,liveTime);
    }
    public void set(String key,String value){
        this.getJedis().set(key, value);
    }
    public void set(byte[] key,byte[] value){
        this.getJedis().set(key,value);
    }
    public String get(String key){
        String value=this.getJedis().get(key);
        return value;
    }
    public byte[] get(byte[] key){
        return this.getJedis().get(key);
    }
    public Set<String> keys(String pattern){
        return this.getJedis().keys(pattern);
    }
    public boolean exists(String key){
        return this.getJedis().exists(key);
    }
    public String flushDB(){
        return this.getJedis().flushDB();
    }
    public long dbsize(){
        return this.getJedis().dbSize();
    }
    public String ping(){
        return this.getJedis().ping();
    }

}













