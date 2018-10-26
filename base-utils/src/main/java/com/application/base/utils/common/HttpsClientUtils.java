package com.application.base.utils.common;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;

import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @desc 去掉https请求中的证书认证,可以不用证书认证就可以直接去访问
 * @author 孤狼
 */
public class HttpsClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final CloseableHttpClient HTTP_CLIENT;
    private static final int TIME_OUT = 5;

    static {
        Registry<ConnectionSocketFactory> registry = null;
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            X509HostnameVerifier allowAllHostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            SSLConnectionSocketFactory sslSf = new SSLConnectionSocketFactory(builder.build(), allowAllHostnameVerifier);
            registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslSf)
                    .build();
        } catch (Exception ignored) {
        }
        //创建httpclient连接池
        PoolingHttpClientConnectionManager conMgr = new PoolingHttpClientConnectionManager(registry);
        //设置连接池线程最大数量
        conMgr.setMaxTotal(200);
        //设置单个路由最大的连接线程数量
        conMgr.setDefaultMaxPerRoute(conMgr.getMaxTotal());

        //创建http request的配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(TIME_OUT * 1000)
                .setConnectionRequestTimeout(TIME_OUT * 1000)
                .setSocketTimeout(TIME_OUT * 1000)
                .build();


        //初始化httpclient客户端
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(conMgr).setDefaultRequestConfig(requestConfig);

        try {
            httpClientBuilder.addInterceptorFirst(new HttpResponseInterceptor() {
                @Override
				public void process(final HttpResponse response, final HttpContext context) throws HttpException, IOException {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        Header ceheader = entity.getContentEncoding();
                        if (ceheader != null) {
                            HeaderElement[] codecs = ceheader.getElements();
                            for (int i = 0; i < codecs.length; i++) {
                                if ("gzip".equalsIgnoreCase(codecs[i].getName())) {
                                    response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                                    return;
                                }
                            }
                        }
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
        httpClientBuilder.setRetryHandler(new HttpRequestRetryHandler() {
            // 自定义的恢复策略
            @Override
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                // 设置恢复策略，在发生异常时候将自动重试2次
                int count = 2 ;
                if (executionCount >= count) {
                    return false;
                }

                // 如果服务器丢掉了连接，那么就重试
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }

                // 不要重试SSL握手异常
                if (exception instanceof SSLHandshakeException) {
                    return false;
                }

                // 如果请求被认为是幂等的，那么就重试
                HttpRequest request = (HttpRequest) context.getAttribute(HttpCoreContext.HTTP_REQUEST);
                boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
                if (!idempotent) {
                    return true;
                }
                return false;
            }
        });

        HTTP_CLIENT = httpClientBuilder.build();
    }

    /**
     * get 方式获取内容.
     * 
     * @param url
     * @param params
     * @param header
     * @param charsetName
     * @return
     */
    public static String getContentGet(String url,
                                       Map<String, String> params,
                                       Map<String, String> header,
                                       String charsetName) {

        RequestBuilder rb = RequestBuilder.get();
        buildeBaseRequest(url, params, header, rb);
        HttpUriRequest request = rb.build();

        return getUrlContent(request);

    }
    
	/**
	 * post 方式获取内容.
	 * 
	 * @param url
	 * @param params
	 * @param header
	 * @param charsetName
	 * @param postBody
	 * @param contentType
	 * @return
	 */
    public static String getContentPost(String url,
                                        Map<String, String> params,
                                        Map<String, String> header,
                                        String charsetName, String postBody, String contentType) {

        StringEntity entity = new StringEntity(postBody, ContentType.create(contentType, charsetName));
        entity.setChunked(true);

        return getContentPost(url, params, header, entity);
    }

    public static String getContentPost(String url,
                                        Map<String, String> params,
                                        Map<String, String> header,
                                        StringEntity entity) {

        RequestBuilder rb = RequestBuilder.post();
        buildeBaseRequest(url, params, header, rb);
        rb.setEntity(entity);
        HttpUriRequest request = rb.build();

        return getUrlContent(request);
    }


    private static void buildeBaseRequest(String url,
                                          Map<String, String> params,
                                          Map<String, String> header,
                                          RequestBuilder rb) {
        rb.setUri(url);

        if (header != null) {
            for (Entry<String, String> entry : header.entrySet()) {
                rb.addHeader(entry.getKey(), entry.getValue());
            }
        }

        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                rb.addParameter(entry.getKey(), entry.getValue());
            }
        }
    }


    public static String getUrlContent(HttpUriRequest httpUriRequest) {

        CloseableHttpResponse response = null;

        try {
            LOGGER.info(httpUriRequest.toString());
            response = HTTP_CLIENT.execute(httpUriRequest);

            HttpEntity entity = response.getEntity();
            String responseContent = EntityUtils.toString(entity);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                return responseContent;
            } else {
                if (httpUriRequest.getMethod() == HttpPost.METHOD_NAME) {
                    HttpPost httppost = (HttpPost) httpUriRequest;
                    Object[] logArray = {EntityUtils.toString(httppost.getEntity()), httppost.getURI().toString(),
                            Integer.toString(statusCode), responseContent};
                    LOGGER.error("post {} to {} return status {} return content {}" + logArray);
                } else if (httpUriRequest.getMethod() == HttpGet.METHOD_NAME) {
                    Object[] logArray = {httpUriRequest.getURI().toString(),
                            Integer.toString(statusCode), responseContent};
                    LOGGER.error("get {}  return status {} return content {}" + logArray);
                }
                return "";
            }
        } catch (IllegalStateException e) {
        } catch (IOException e) {
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return "";
    }

    /**
     * HTTPS POST 请求，返回String的数据包
     *
     * @param url
     * @param requestData
     * @return
     * @throws Exception
     */

    public static String httpsPostRequest(String url, String requestData) {
        try {
            return httpsPostRequest(url, requestData.getBytes(CHARACTER_ENCODING));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        @Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
		public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
		public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static String httpsPostRequest(String url, byte[] requestData) throws Exception {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
        HttpsURLConnection httpsConn = null;
        StringBuffer sBuffer = new StringBuffer(300);

        try {
            httpsConn = (HttpsURLConnection) new URL(url).openConnection();
            httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            httpsConn.setSSLSocketFactory(sc.getSocketFactory());
            // 设置的超时时间大于45秒
            httpsConn.setConnectTimeout(45000);
            httpsConn.setDoInput(true);
            httpsConn.setDoOutput(true);

            BufferedOutputStream hurlBufOus = new BufferedOutputStream(httpsConn.getOutputStream());
            hurlBufOus.write(requestData);
            hurlBufOus.flush();
            hurlBufOus.close();

            httpsConn.connect();
            BufferedReader in = null;
            String inputLine = null;

            in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), CHARACTER_ENCODING));
            while ((inputLine = in.readLine()) != null) {
                sBuffer.append(inputLine);
            }

            in.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpsConn != null) {
                httpsConn.disconnect();
            }
        }

        return sBuffer.toString();
    }

    public CloseableHttpClient createHttpsClient() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }

        try {
            sslContext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }
}
