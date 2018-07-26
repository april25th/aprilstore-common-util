package com.aprilstore.util.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 *
 * redis client
 * @author wd
 */
public class RedisClient {
    protected static Log logger = LogFactory.getLog(RedisClient.class);

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return jedis;
    }
    public void set(String key, String value, int expire) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if(jedis == null){
                return;
            }
            jedis.set(key, value);
            jedis.expire(key,expire);
            
        }
        catch (Exception e){
            logger.error("redis set string error:"+e);
        }
        finally {
            //返还到连接池
                jedis.close();
            }
        }


    public void setList(String key, String value, int expire) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if(jedis == null){
                return;
            }
            jedis.set(key, value);
            jedis.expire(key,expire);
        }
        catch (Exception e){
            logger.error("redis setList error:"+e);
        }
        finally {
        	
            //返还到连接池
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public String get(String key) throws Exception {

        Jedis jedis = null;
        try {
            jedis = getJedis();
            if(jedis == null|| !jedis.exists(key)){
                return null;
            }
            return jedis.get(key);
        }
        catch (Exception e){
            logger.error("redis get error:"+e);
        }
        finally {
            //返还到连接池  
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public void clean(String key)throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if(jedis == null){
                return;
            }
            jedis.del(key);
        }
        catch (Exception e){
            logger.error("redis clean error:"+e);
        }
        finally {
        	
            //返还到连接池
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    /**
     * 设置对象
     * @param key
     * @param obj
     */
    public void setObject(String key ,Object obj){
        Jedis jedis = null;
        try {
            obj = obj == null ? new Object():obj;
            jedis=getJedis();
            if(jedis == null){
                return;
            }
            jedis.set(key.getBytes(), SerializeUtil.serialize(obj));
        }
        catch (Exception e){
            logger.error("redis setObject error:"+e);
        }
        finally {
        	
            //返还到连接池
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    /**
     * 设置对象
     * @param key
     * @param obj
     */
    public void setObject(String key ,Object obj, int expire){
        Jedis jedis = null;
        try {
            obj = obj == null ? new Object():obj;
            jedis=getJedis();
            if(jedis == null){
                return;
            }
            jedis.set(key.getBytes(), SerializeUtil.serialize(obj));
            jedis.expire(key.getBytes(),expire);
        }
        catch (Exception e){
            logger.error("redis setObject error:"+e);
        }
        finally {
        	
            //返还到连接池
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    /**
     * 获取对象
     * @param key
     * @return Object
     */
    public Object getObject(String key){
        Jedis jedis = null;
        try {
            jedis=getJedis();
            if(jedis == null || !jedis.exists(key)){
                return null;
            }
            byte[] data = jedis.get(key.getBytes());
            return (Object)SerializeUtil.unserialize(data);
        }
        catch (Exception e){
            logger.error("redis getObject error:"+e);
        }
        finally {
            //返还到连接池
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 设置List集合
     * @param key
     * @param list
     */
    public void setList(String key ,List<?> list){
        Jedis jedis = null;
        try {
            jedis=getJedis();
            if(jedis == null){
                return;
            }
            if(list!=null&&list.size()>0){
                jedis.set(key.getBytes(), SerializeUtil.serializeList(list));
            }
        }
        catch (Exception e){
            logger.error("redis setList error:"+e);
        }
        finally {
            //返还到连接池
            if(jedis!=null){
                jedis.close();
            }
        }
    }
    /**
     * 设置List集合
     * @param key
     * @param list
     */
    public void setList(String key ,List<?> list, int expire){
        Jedis jedis = null;
        try {
            jedis=getJedis();
            if(jedis == null){
                return;
            }
            if(list!=null&&list.size()>0){
                jedis.set(key.getBytes(), SerializeUtil.serializeList(list));
                jedis.expire(key,expire);
            }
        }
        catch (Exception e){
            logger.error("redis setList error:"+e);
        }
        finally {
        	
            //返还到连接池
            if(jedis!=null){
                jedis.close();
            }
        }
    }
    /**
     * 获取List集合
     * @param key
     * @return
     */
    public List<?> getList(String key){
        Jedis jedis = null;
        try {
            jedis=getJedis();
            if(jedis == null || !jedis.exists(key)){
                return null;
            }
            byte[] data = jedis.get(key.getBytes());
            return SerializeUtil.unserializeList(data);
        }
        catch (Exception e){
            logger.error("redis setList error:"+e);
        }
        finally {
            //返还到连接池
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }
}  