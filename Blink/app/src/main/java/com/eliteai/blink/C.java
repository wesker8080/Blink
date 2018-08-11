package com.eliteai.blink;

/**
 * 这里定义一些常量
 * @author MR.ZHANG
 * @create 2018-08-09 14:53
 */
public interface C {
    String APPID = "1234567890abcdefg";
    boolean DEBUG_LOG_ON = true;
    interface Net {
        String DEBUG_URL = "http://47.106.156.161:8080/api";
        String BlinkCmpServer = "tcp://14.18.59.58:18080";
        String BlinkCmpToken = "http://14.18.59.58:8800/token";
    }
}
