package cn.liuw.platform.common.util;

import cn.liuw.platform.common.base.response.HttpResponseData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    protected static final Log log = LogFactory.getLog("HttpUtil.class");

    private static HttpClient client;

    //连接池最大连接数
    private static final Integer MAX_TOTAL = 1000;

    //单个路由默认最大连接数
    private static final Integer MAX_PER_ROUTE = 100;

    //请求超时时间ms
    private static final Integer REQ_TIMEOUT = 3 * 1000;

    //连接超时时间ms
    private static final Integer CONN_TIMEOUT = 3 * 1000;

    //读取超时时间ms
    private static final Integer SOCK_TIMEOUT = 5 * 1000;

    //HTTP链接管理器线程
    private static HttpClientConnectionMonitorThread thread;

    public static final int cache = 10 * 1024;

    static {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(MAX_TOTAL);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(REQ_TIMEOUT)
                .setConnectTimeout(CONN_TIMEOUT)
                .setSocketTimeout(SOCK_TIMEOUT)
                .build();

        //管理 http连接池
        HttpUtil.thread = new HttpClientConnectionMonitorThread(poolingHttpClientConnectionManager);

        // 设置keepalive策略
        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header 
                /**
                 * 一般服务器端没有设置timeout，连接就会变成长连接。
                 * 一般服务器端tomcat Connector中keepAliveTimeout可以配置连接空闲多久就关闭该连接，它的值默认是和connectionTimeout一样，而在server.xml中connectionTimeout值默认为20s
                 * 当服务器超过20s断开没有通知客户端，客户端这个连接池中的链接就会失效，在访问就会定期报错：org.apache.http.NoHttpResponseException: 172.16.13.8:7012 failed to respond
                 * 因此此处设置为19s超时断掉
                 * */
                long keepAliveDuration = 19000; // 19s超时
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            keepAliveDuration = Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }

//               HttpHost target = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
//               if ("bizapi.jd.com ".equalsIgnoreCase(target.getHostName())) {
//                     return 60 * 1000;
//               }else {
//                    return 300 * 1000;
//               }
                return keepAliveDuration;
            }
        };

        client = HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(myStrategy)
                .setRetryHandler(new HttpRequestRetryHandler() {
                    // 自定义超时重试机制
                    @Override
                    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                        if (executionCount > 1) {
                            // 重试一次
                            log.warn("Maximum tries reached for client http pool ");
                            return false;
                        }
                        if (exception instanceof org.apache.http.NoHttpResponseException
                            //NoHttpResponseException 重试
                            //    || exception instanceof ConnectTimeoutException //连接超时重试
                            //  || exception instanceof SocketTimeoutException    //响应超时不重试，避免造成业务数据不一致
                        ) {
                            log.warn("No response from server on " + executionCount + " call");
                            return true;
                        }
                        return false;
                    }
                })
                .build();
    }

    public HttpUtil() {
    }

    public static HttpResponseData sendXMLForPost(String url, String xmlData) {
        HttpResponseData resp = new HttpResponseData();
        try {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(xmlData, "UTF-8");
            post.setEntity(entity);
            // Set content type of request header
            post.setHeader("Content-Type", "application/xml");
            post.setHeader("cache-control", "no-cache");

            // Execute request and get the response
            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            resp.setCode(response.getStatusLine().getStatusCode());
            InputStreamReader reader = new InputStreamReader(resEntity.getContent(), "UTF-8");
            char[] buff = new char[1024];
            int length = 0;
            StringBuffer sb = new StringBuffer();
            while ((length = reader.read(buff)) != -1) {
                sb.append(new String(buff, 0, length));
            }
            resp.setMessage(sb.toString());
        } catch (Exception e) {
            resp.setValues(500, e.getMessage());
        }
        return resp;
    }

    public static HttpResponseData sendJSONForPost(String url, String json) {
        HttpResponseData resp = new HttpResponseData();
        HttpPost post = null;
        try {
            post = new HttpPost(url);
            StringEntity entity = new StringEntity(json, "utf-8");
            // entity.setContentEncoding("UTF-8");
            //entity.setContentType("application/json");
            post.setEntity(entity);
            //System.out.println(entity.toString());
            // Set content type of request header
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            //post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            // Execute request and get the response
            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            resp.setCode(response.getStatusLine().getStatusCode());
            byte[] buff = readData(resEntity.getContent());
            String content = new String(buff, "utf-8");
            resp.setMessage(content);
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
            log.warn(ex, ex.fillInStackTrace());
            resp.setValues(-1, ex.getMessage());
        } finally {
            if (post != null) {
                post.abort();
            }
        }
        return resp;
    }

    public static HttpResponseData sendForPost(String url, String json) {
        HttpResponseData resp = new HttpResponseData();
        HttpPost post = null;
        try {
            post = new HttpPost(url);
            StringEntity entity = new StringEntity(json, "utf-8");
            post.setEntity(entity);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            resp.setCode(response.getStatusLine().getStatusCode());
            byte[] buff = readData(resEntity.getContent());
            String content = new String(buff, "utf-8");
            resp.setMessage(content);
        } catch (Exception ex) {
            resp.setValues(-1, ex.getMessage());
        } finally {
            if (post != null) {
                post.abort();
            }
        }
        return resp;
    }

    public static HttpResponseData sendForGet(String url) {
        HttpResponseData resp = new HttpResponseData();
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            HttpEntity resEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStreamReader reader = new InputStreamReader(resEntity.getContent(), "UTF-8");
                char[] buff = new char[1024];
                int length = 0;
                StringBuffer sb = new StringBuffer();
                while ((length = reader.read(buff)) != -1) {
                    sb.append(new String(buff, 0, length));
                }
                resp.setCode(200);
                resp.setMessage(sb.toString());
            } else {
                resp.setCode(response.getStatusLine().getStatusCode());
                resp.setMessage("Missing CP DRM parameters or parameter format is incorrect!");
            }
        } catch (Exception e) {
            //resp.setValues(500, e.getMessage());
            resp.setCode(500);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    public static HttpResponseData sendForaAllGet(String url) {
        HttpResponseData resp = new HttpResponseData();
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            HttpEntity resEntity = response.getEntity();
            resp.setCode(response.getStatusLine().getStatusCode());
            InputStreamReader reader = new InputStreamReader(resEntity.getContent(), "UTF-8");
            char[] buff = new char[1024];
            int length = 0;
            StringBuffer sb = new StringBuffer();
            while ((length = reader.read(buff)) != -1) {
                sb.append(new String(buff, 0, length));
            }
            resp.setMessage(sb.toString());
        } catch (Exception e) {
            resp.setValues(500, e.getMessage());
        }
        return resp;
    }

    /**
     * 此方法用来调用irdeto的webservice接口
     *
     * @param url     链接地址
     * @param xmldate xml内容
     * @param headMap 需要插入到http头的数据
     * @return
     */
    public static HttpResponseData sendWebServiceForPost(String url, String xmldate, Map<String, String> headMap) {
        HttpResponseData resp = new HttpResponseData();
        try {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(xmldate);
            post.setEntity(entity);
            // Set content type of request header
            post.setHeader("Content-Type", "text/xml;charset=UTF-8");

            Iterator<String> it = headMap.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                post.setHeader(key, headMap.get(key));
            }

            // Execute request and get the response
            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            resp.setCode(response.getStatusLine().getStatusCode());
            InputStreamReader reader = new InputStreamReader(resEntity.getContent(), "UTF-8");
            char[] buff = new char[1024];
            int length = 0;
            StringBuffer sb = new StringBuffer();
            while ((length = reader.read(buff)) != -1) {
                sb.append(new String(buff, 0, length));
            }
            resp.setMessage(sb.toString());
        } catch (Exception e) {
            resp.setValues(500, e.getMessage());
        }
        return resp;
    }

    public static HttpResponseData httpGet(String url) {
        HttpResponseData resp = new HttpResponseData();
        try {
            URL ur = new URL(url);
            URLConnection conn = ur.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000 * 5);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:23.0) Gecko/20100101 Firefox/23.0");
            conn.getURL().getContent().toString();
            InputStream in = ur.openStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            resp.setCode(200);
            resp.setMessage(sb.toString());
        } catch (Exception e) {
            resp.setValues(500, e.getMessage());
        }
        return resp;
    }

    public static String download(String url, String filepath) {
        InputStream is = null;
        FileOutputStream fileout = null;
        try {
            //HttpClient client = new DefaultHttpClient();  
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);

            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            File file = new File(filepath);
            file.getParentFile().mkdirs();
            fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小 
             */
            byte[] buffer = new byte[cache];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fileout.flush();
                fileout.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return null;
    }

    public static long getSize(String url) throws IOException {
        HttpHead head = new HttpHead(url);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(head);
        Header header = response.getFirstHeader(HttpHeaders.CONTENT_LENGTH);
        if (header == null) return -1L;
        return Long.valueOf(header.getValue());
    }

    public static void main(String[] args) throws IOException {
//		System.out.println(HttpUtil.getSize("http://u-storage.oss-cn-shanghai.aliyuncs.com/测试内容提供商1/img/pukka.jpg"));
//		String rooturl = SafeUtils.getString(PropertyUtil.getProperty().getProperty("Config_ImageRootUrl"));
//		String tmpdatetime = ManageHelper.getDateTimeString();
//		String fileurl = rooturl + tmpdatetime + ".jpg";
//		//sendForGet("https://bb-gen.test.expressplay.com/hms/bb/token?customerAuthenticator=1177,e0e332fca4394d32b5e4fab84ed5ccac&actionTokenType=1&userId=1122&userKey=f0d1dcf415414787bf40aafdb830e530&contentId=cid:marlin%23PATN-LIVE@0000004b&contentKey=f9778d00babdf369bf601a582f29377e&rightsType=BuyToOwn");
//		download("http://u-storage.oss-cn-shanghai.aliyuncs.com/测试内容提供商1/img/pukka.jpg", fileurl);
        for (int i = 0; i < 100; i++) {
            System.out.println(sendForPost("http://localhost:8500/test/post", ""));
        }
    }

    private static byte[] readData(InputStream in) {
        try {
            int bufferSize = 1024 * 8;
            InputStream reader = in;
            byte[] buff = new byte[bufferSize];
            int i = 0;
            List list = new ArrayList(bufferSize);
            int read = reader.read();
            if (read != -1) {
                list.add((byte) read);
            }
            while (read != -1) {
                read = reader.read();
                if (read != -1) {
                    list.add((byte) read);
                }
            }
            byte[] b3 = new byte[list.size()];

            for (int j = 0; j < b3.length; j++) {
                b3[j] = ((Byte) list.get(j)).byteValue();
            }
            reader.close();
            in.close();
            return b3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
