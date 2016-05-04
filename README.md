# BitCoinPlatformAndroid
本项目是一个比特币交易平台客户端,服务器采用的BTCC的API


代码缺少一个类,`SecurityConfig`位于 ` cn.scujcc.bug.bitcoinplatformandroid.util`


    public class SecurityConfig {
    
    
        public final static String CNY_ACCESS_KEY = "中国站APIKEY";
        public final static String CNY_SECRET_KEY = "中国站SECRET_KEY";
        public final static String CNY_URL = "wss://real.okcoin.cn:10440/websocket/okcoinapi";
    
        public final static String USD_ACCESS_KEY = "国际站APIKEY";
        public final static String USD_SECRET_KEY = "国际站SECRET_KEY";
        public final static String USD_URL = "wss://real.okcoin.com:10440/websocket/okcoinapi";
        public final static String USD_REST_URL = "https://www.okcoin.com";
    
    }



##编译时请在`gradle.properties`设置

org.gradle.jvmargs=-Xmx2048M

