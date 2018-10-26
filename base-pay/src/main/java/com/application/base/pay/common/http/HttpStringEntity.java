package com.application.base.pay.common.http;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

/**
 * @desc 请求实体
 * @author 孤狼
 */
public class HttpStringEntity extends StringEntity {

    public HttpStringEntity(Map<String, Object> request, ContentType contentType) throws UnsupportedCharsetException {
        super(UriVariables.getMapToParameters(request), contentType);
    }

    public HttpStringEntity(Map<String, Object> request, String charset) throws UnsupportedCharsetException {
        super(UriVariables.getMapToParameters(request), charset);
    }

    public HttpStringEntity(Map<String, Object> request, Charset charset) {
        super(UriVariables.getMapToParameters(request), charset);
    }

    public HttpStringEntity(Map<String, Object> request) throws UnsupportedEncodingException {
        super(UriVariables.getMapToParameters(request));
    }

    public HttpStringEntity(String string, ContentType contentType) throws UnsupportedCharsetException {
        super(string, contentType);
    }

    public HttpStringEntity(String string, String charset) throws UnsupportedCharsetException {
        super(string, charset);
    }

    public HttpStringEntity(String string, Charset charset) {
        super(string, charset);
    }

    public HttpStringEntity(String string) throws UnsupportedEncodingException {
        super(string);
    }


}
