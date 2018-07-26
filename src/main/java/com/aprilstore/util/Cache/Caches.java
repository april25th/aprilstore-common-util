package com.aprilstore.util.Cache;

import com.aprilstore.util.LogFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Caches {
	
	public static CacheHelper from(int expiredSeconds,int maxSize){
		
		CacheHelper helper = new CacheHelper();
		helper.expiredTime(expiredSeconds * 1000).maxSize(maxSize);
		return helper;
	}
	
	public static class CacheHelper {
		// 缓存刷新时间 ，默认2分钟
		protected long expiredTime = 2 * 60 * 1000;
		// 最大的缓存数量
		protected int maxSize = 1000;

		protected Map<String, CacheBean> cacheMap = new ConcurrentHashMap<String, CacheBean>();

		protected static Log log = LogFactory.getLog(CacheHelper.class);
		
		public CacheHelper(){}
		
		public CacheHelper expiredTime(int expiredTime){
			
			this.expiredTime = expiredTime;
			return this;
		}
		
		public CacheHelper maxSize(int maxSize){
			
			this.maxSize = maxSize;
			return this;
		}
		
		public <T> T computeIfAbsent(String key,
	            Function<? super String, ? extends T> mappingFunction) {
	        Objects.requireNonNull(mappingFunction);
	        T v;
	        if ((v = get(key)) == null) {
	            T newValue;
	            if ((newValue = mappingFunction.apply(key)) != null) {
	                put(key, newValue);
	                return newValue;
	            }
	        }

	        return v;
	    }
		
		public <T> T get(String key){
			
			CacheBean bean = cacheMap.get(key);
			
			if(bean == null || bean.isExpire()){
				
				return null;
			}
			
			return bean.getObj();
		}
		
		public boolean containsKey(String key){
			
			boolean contains = cacheMap.containsKey(key);
			
			if(!contains){
				
				return false;
			}
			
			CacheBean bean = cacheMap.get(key);
			
			if(bean == null || bean.isExpire()){
				
				return false;
			}
			
			return true;
		}
		
		public <T> T remove(String key){
			
			CacheBean bean = cacheMap.remove(key);
			
			if(bean == null || bean.isExpire()){
				
				return null;
			}
			
			return bean.getObj();
		}
		
		public <T> T put(String key,Object value){
			
			CacheBean cacheBean = new CacheBean(value, expiredTime);
			
			CacheBean oldBean = put(key, cacheBean);
			
			if(oldBean == null || oldBean.isExpire()){
				
				return null;
			}
			
			return oldBean.getObj();
		}

		/**
		 * 
		 * function: 清空缓存中过期的对象
		 * 
		 * @since JDK 1.6
		 */
		protected void clearCache() {

			// 如果超过了最大缓存数量
			if (cacheMap != null && cacheMap.size() > maxSize) {

				removeExpiredData();
			}
		}

		/**
		 * 移除过期的缓存对象 function:
		 * 
		 * @since JDK 1.6
		 */
		protected synchronized void removeExpiredData() {

			if (cacheMap.size() <= maxSize) {

				return;
			}

			Set<String> keySet = cacheMap.keySet();

			for (String key : keySet) {

				if (key != null) {

					CacheBean cacheBean = cacheMap.get(key);

					if (cacheBean.isExpire()) {

						log.info(LogFormat.formatMsg("BasicCacheMode.removeExpiredData", key, "data is expired,remove it."));
						cacheMap.remove(key);
					}
				}
			}
			// 如果数量还超过最大值，则清理时间最长的对象
			if (cacheMap.size() > maxSize) {

				int removeNum = cacheMap.size() - maxSize;

				log.warn(LogFormat.formatMsg("BasicCacheMode.removeExpiredData", "",
						"after remove expired cache bean,size >= maxSize," + "must remove longest cached obj.maxSize = " + maxSize
								+ ",cacheSize = " + cacheMap.size()));

				String removeKey = null;

				while (removeNum-- > 0) {

					long expiredTime = 0;

					keySet = cacheMap.keySet();

					for (String key : keySet) {

						CacheBean bean = cacheMap.get(key);

						if (bean != null) {

							if (expiredTime == 0) {

								expiredTime = bean.getExpireTime();
								removeKey = key;
							}
							// 获取最早过期的对象
							if (bean.getExpireTime() < expiredTime) {

								expiredTime = bean.getExpireTime();
								removeKey = key;
							}
						}
					}

					if (removeKey != null) {

						log.warn(LogFormat.formatMsg("BasicCacheMode.removeExpiredData", "",
								"after remove expired cache bean,size >= maxSize," + "remove obj key is " + removeKey));
						cacheMap.remove(removeKey);
					}

				}
			}
		}

		protected CacheBean getUnExpiredBean(String key) {

			CacheBean cacheBean = cacheMap.get(key);

			if (cacheBean != null && cacheBean.isExpire()) {

				log.info(LogFormat.formatMsg("BasicCacheMode.getUnExpiredBean", key, "data is expired,remove it."));
				cacheMap.remove(key);
				cacheBean = null;
			}

			return cacheBean;
		}

		protected CacheBean put(String key, CacheBean cacheBean) {

			clearCache();

			return cacheMap.put(key, cacheBean);
		}
	}
	
	public static class CacheBean{
		
		private Object obj;
		// 过期时间
		private long expireTime;

		/**
		 * 
		 * Creates a new instance of CacheData.
		 * 
		 * @param obj
		 *            缓存的对象
		 * @param expire
		 *            设置超时时间，ms
		 */
		public CacheBean(Object obj, long expire) {
			super();
			this.obj = obj;
			this.expireTime = System.currentTimeMillis() + expire;
		}

		/**
		 * 
		 * function: 该缓存对象是否已经过期
		 * 
		 * @return
		 * @since JDK 1.6
		 */
		public boolean isExpire() {

			return expireTime < System.currentTimeMillis();

		}

		public long getExpireTime() {

			return expireTime;

		}

		@SuppressWarnings("unchecked")
		public <T>T getObj() {
			
			return (T) obj;
		}
	}

}
