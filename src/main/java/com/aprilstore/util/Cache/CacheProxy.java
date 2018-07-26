package com.aprilstore.util.Cache;


import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wd on 2018/7/23.
 */
public class CacheProxy implements CacheOption {

    @Resource
    private RedisClient redisClinet;

    private Caches.CacheHelper caches = Caches.from(60*10,1000);

    private boolean isRedis=true;

    @Override
    public void set(String key, String value, int expire) throws Exception {
        if(isRedis){
            redisClinet.set(key,value,expire);
        }
        else {
            caches.put(key,value);
        }
    }

    @Override
    public void setList(String key, String value, int expire) throws Exception {
        if(isRedis){
            redisClinet.setList(key,value,expire);
        }
        else {
            caches.put(key,value);
        }
    }

    @Override
    public String get(String key) throws Exception {
        if(isRedis){
            return redisClinet.get(key);
        }
        else {
            return (String)caches.get(key);
        }
    }

    @Override
    public void clean(String key) throws Exception {
        if(isRedis){
            redisClinet.clean(key);
        }
        else {
            caches.remove(key);
        }
    }

    @Override
    public void setObject(String key, Object obj) {
        if(isRedis){
            redisClinet.setObject(key,obj);
        }
        else {
            caches.put(key,obj);
        }
    }

    @Override
    public void setObject(String key, Object obj, int expire) {
        if(isRedis){
            redisClinet.setObject(key,obj,expire);
        }
        else {
            caches.put(key,obj);
        }
    }

    @Override
    public Object getObject(String key) {
        if(isRedis){
            return redisClinet.getObject(key);
        }
        else {
            return caches.get(key);
        }
    }

    @Override
    public void setList(String key, List<?> list) {
        if(isRedis){
            redisClinet.setList(key,list);
        }
        else {
            caches.put(key,list);
        }
    }

    @Override
    public void setList(String key, List<?> list, int expire) {
        if(isRedis){
            redisClinet.setList(key,list,expire);
        }
        else {
            caches.put(key,list);
        }
    }

    @Override
    public List<?> getList(String key) {
        if(isRedis){
            return redisClinet.getList(key);
        }
        else {
            return (List<?>)caches.get(key);
        }
    }
}
