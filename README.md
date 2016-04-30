# BitCoinPlatformAndroid
本项目是一个比特币交易平台客户端,服务器采用的BTCC的API


代码缺少一个类,`SecurityConfig`位于 ` cn.scujcc.bug.bitcoinplatformandroid.util`


    public class SecurityConfig {
         public final static String ACCESS_KEY = "BTCC的ACCESS_KEY";
         public final static String SECRET_KEY = "BTCC的SECRET_KEY";
    }


##编译时请在`gradle.properties`设置

org.gradle.jvmargs=-Xmx2048M

