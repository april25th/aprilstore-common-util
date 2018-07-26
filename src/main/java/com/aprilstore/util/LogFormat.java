package com.aprilstore.util;

import java.util.Map;
import java.util.Set;

/**
 * 日志格式化输出类，将日志格式化输出，便于系统分析和查询,terst
 * 
 * @author wd
 * 
 */
public class LogFormat {
	/**
	 * 普通信息格式化，格式:[方法名称]-ID:日志信息
	 * 
	 * @param user
	 * @param methodName
	 * @param info
	 * @return
	 */
	public static String formatMsg(String methodName,String id, 
			Object info) {
		StringBuffer buffer = getLogBuffer(methodName);

		buffer.append(id);
		buffer.append(" : ");
		

		buffer.append(info==null?null:info.toString());

		return buffer.toString();
	}
    
	public static String formatMsg(String methodName,String id, 
			String formatStr,Object... args) {
		StringBuffer buffer = getLogBuffer(methodName);

		buffer.append(id);
		buffer.append(" : ");
		
		String info = formatStr != null && args != null ? String.format(formatStr, args) : formatStr;

		buffer.append(info==null?null:info.toString());

		return buffer.toString();
	}

	/**
	 * 普通信息格式化，格式:[方法名称]-ID:日志信息
	 * @param methodName
	 * @param id
	 * @param info
	 * @return
	 */
	public static String formatMsg(String methodName,long id, 
			Object info) {
		StringBuffer buffer = getLogBuffer(methodName);

		buffer.append(id);
		buffer.append(" : ");
		
		

		buffer.append(info==null?null:info.toString());

		return buffer.toString();
	}

	/**
	 * 格式化充值交易信息，格式：[方法名称]-合作商代码：属性=值^属性=值^属性=值
	 * @param methodName
	 * @param coperateCode
	 * @param chargeInfo
	 * @return
	 */
	public static String formatChargeInfo(String methodName,
			String coperateCode, Map<String, String> chargeInfo) {

		StringBuffer buffer = getLogBuffer(methodName);

		buffer.append(coperateCode);

		buffer.append(" : ");

		if (chargeInfo != null) {
			Set<String> keySet = chargeInfo.keySet();
			for (String name : keySet) {
				buffer.append(name);
				buffer.append("=");
				buffer.append(chargeInfo.get(name));
				buffer.append("^");
			}
		}

		return buffer.toString();
	}

	/**
	 * 格式化方法的运行时间，特别用于与穗彩或者短信的第三方交互接口的执行时间的控制。 便于在性能问题出现时，鉴定问题边界。
	 * @param methodName
	 * @param costTime
	 * @param info
	 * @return
	 */
	public static String formatTimeCost(String methodName, long costTime,
			String info) {
		StringBuffer buffer = getLogBuffer(methodName);
		buffer.append("cost ");
		buffer.append(costTime);
		buffer.append(" ms ... ");

		if (info != null) {
			buffer.append(info);
		}

		return buffer.toString();
	}

	/**
	 * 获取统一的格式化消息头的buffer
	 * 
	 * @param methodName
	 * @return
	 */
	private static StringBuffer getLogBuffer(String methodName) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("[ ");
		buffer.append(methodName);
		buffer.append(" ]");
		buffer.append(" - ");

		return buffer;
	}
	
	/**
	 * 格式化错误日志，格式[方法名称]-错误信息 [附加信息]
	 * @param methodName
	 * @param e
	 * @param info
	 * @return
	 */
	public static String formatError(String methodName,Exception e,String info){
		 StringBuffer buffer = getLogBuffer(methodName);
		 
		 StackTraceElement[] elements= e.getStackTrace();
		 for(StackTraceElement ste:elements){
			 buffer.append(ste.toString());
			 buffer.append("/r/n");
		 }
		 
		 buffer.append(" [ ");
		 buffer.append(info);
		 buffer.append(" ] ");
		 
		 return buffer.toString();
	}
	
    /**
     * 格式化资金交易日志信息,格式[方法名称]-userId:100079-tradeId:10001[operateType money]-info
     * @param method
     * @param userId
     * @param operateType
     * @param tradeId
     * @param info
     * @return
     */
	public static String formatCaptailMsg(String method,long userId,String operateType,long tradeId,double money,String info){
		StringBuffer buffer = getLogBuffer(method);
		buffer.append(" -userId:");
		buffer.append(userId);
		buffer.append("-tradeId:");
		buffer.append(tradeId);
		buffer.append(" [ ");
		buffer.append(operateType);
		buffer.append("  ");
		buffer.append(money);
		if(info != null) {
			buffer.append(info);
		}
		return buffer.toString();
	}
	
}
