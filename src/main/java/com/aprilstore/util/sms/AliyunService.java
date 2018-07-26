package com.aprilstore.util.sms;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.BitSet;
import java.util.Map;

/**
 * Created by wd on 2017/10/13.
 */
public class AliyunService {
    private static final Logger logger = LoggerFactory.getLogger(AliyunService.class);


    public static void main(String[] args) throws Exception {
        String accessKeyId = "testId";
        String accessSecret = "testSecret";
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
        Map<String, String> paras = new java.util.HashMap<String, String>();
        // 1. 系统参数
        paras.put("SignatureMethod", "HMAC-SHA1");
        paras.put("SignatureNonce", java.util.UUID.randomUUID().toString());
        paras.put("AccessKeyId", accessKeyId);
        paras.put("SignatureVersion", "1.0");
        paras.put("Timestamp", df.format(new java.util.Date()));
        paras.put("Format", "XML");
        // 2. 业务API参数
        paras.put("Action", "SendSms");
        paras.put("Version", "2017-05-25");
        paras.put("RegionId", "cn-hangzhou");
        paras.put("PhoneNumbers", "15300000001");
        paras.put("SignName", "阿里云短信测试专用");
        paras.put("TemplateParam", "{\"customer\":\"test\"}");
        paras.put("TemplateCode", "SMS_71390007");
        paras.put("OutId", "123");
        // 3. 去除签名关键字Key
        if (paras.containsKey("Signature")) {
            paras.remove("Signature");
        }
        // 4. 参数KEY排序
        java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<String, String>();
        sortParas.putAll(paras);
        // 5. 构造待签名的字符串
        java.util.Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(paras.get(key)));
        }
        String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append("GET").append("&");
        stringToSign.append(specialUrlEncode("/")).append("&");
        stringToSign.append(specialUrlEncode(sortedQueryString));
        String sign = sign(accessSecret + "&", stringToSign.toString());
        // 6. 签名最后也要做特殊URL编码
        String signature = specialUrlEncode(sign);
        System.out.println(paras.get("SignatureNonce"));
        System.out.println("\r\n=========\r\n");
        System.out.println(paras.get("Timestamp"));
        System.out.println("\r\n=========\r\n");
        System.out.println(sortedQueryString);
        System.out.println("\r\n=========\r\n");
        System.out.println(stringToSign.toString());
        System.out.println("\r\n=========\r\n");
        System.out.println(sign);
        System.out.println("\r\n=========\r\n");
        System.out.println(signature);
        System.out.println("\r\n=========\r\n");
        // 最终打印出合法GET请求的URL
        System.out.println("http://dysmsapi.aliyuncs.com/?Signature=" + signature + sortQueryStringTmp);

        AliyunServiceRequest request = new AliyunServiceRequest();

        request.getParam().put("RegionId", "cn-hangzhou");
        request.getParam().put("PhoneNumbers", "15300000001");
        request.getParam().put("SignName", "阿里云短信测试专用");
        request.getParam().put("TemplateParam", "{\"customer\":\"test\"}");
        request.getParam().put("TemplateCode", "SMS_71390007");
        request.getParam().put("OutId", "123");

        AliyunServiceConfig config = new AliyunServiceConfig();
        config.setApiUrl("http://dysmsapi.aliyuncs.com");
        config.setAccessKeyId("key");
        config.setAccessSecret("secret");
        config.setAction("SendSms");
        config.setVersion("2017-05-25");

        System.out.println(generateAPISignature(config, request));
    }

    public static String invokeService(AliyunServiceConfig config, AliyunServiceRequest request) {
        String result = null;
        try {
            String apiUrl = generateAPISignature(config, request);
            result = httpGet(apiUrl);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return result;
    }

    public static String generateAPISignature(AliyunServiceConfig config, AliyunServiceRequest request) throws Exception {
        request.initalParam();
        return generateAPISignature(config.getApiUrl(), config.getAccessKeyId(), config.getAccessSecret(), config.getAction(), config.getVersion(), request.getParam());
    }

    public static String generateAPISignature(String apiUrl, String accessKeyId, String accessSecret, String action, String version, Map<String, String> param) throws Exception {

        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new java.util.SimpleTimeZone(8, "GMT"));// 这里一定要设置GMT时区
        Map<String, String> paras = new java.util.HashMap<String, String>();
        // 1. 系统参数
        paras.put("SignatureMethod", "HMAC-SHA1");
        paras.put("SignatureNonce", java.util.UUID.randomUUID().toString());
        paras.put("AccessKeyId", accessKeyId);
        paras.put("SignatureVersion", "1.0");
        paras.put("Timestamp", df.format(new java.util.Date()));
        paras.put("Format", "JSON");
        // 2. 业务API参数
        paras.put("Action", action);
        paras.put("Version", version);
        paras.putAll(param);

        // 3. 去除签名关键字Key
        if (paras.containsKey("Signature")) {
            paras.remove("Signature");
        }
        // 4. 参数KEY排序
        java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<String, String>();
        sortParas.putAll(paras);
        // 5. 构造待签名的字符串
        java.util.Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            if (!StringUtils.isEmpty(paras.get(key))) {
                sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(paras.get(key)));
            }
        }
        String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append("GET").append("&");
        stringToSign.append(specialUrlEncode("/")).append("&");
        stringToSign.append(specialUrlEncode(sortedQueryString));
        String sign = sign(accessSecret + "&", stringToSign.toString());
        // 6. 签名最后也要做特殊URL编码
        String signature = specialUrlEncode(sign);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiUrl);
        stringBuilder.append("/?Signature=");
        stringBuilder.append(signature);
        stringBuilder.append(sortQueryStringTmp);
        return stringBuilder.toString();
    }


    public static String specialUrlEncode(String value) throws Exception {
        return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
    }

    public static String sign(String accessSecret, String stringToSign) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(signData);
    }

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static String httpGet(String url) {
        //get请求返回结果
        String result = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    result = EntityUtils.toString(entity);
                    //System.out.println("Response content: " + EntityUtils.toString(entity));
                }
                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

}
