package com.aprilstore.util.Cache;

import java.util.List;

/**
 * Created by wd on 2018/7/23.
 */
public interface CacheOption {
    public void set(String key, String value, int expire) throws Exception;
    public void setList(String key, String value, int expire) throws Exception;
    public String get(String key) throws Exception;
    public void clean(String key)throws Exception;
    public void setObject(String key, Object obj);
    public void setObject(String key, Object obj, int expire);
    public Object getObject(String key);
    public void setList(String key, List<?> list);
    public void setList(String key, List<?> list, int expire);
    public List<?> getList(String key);
}
