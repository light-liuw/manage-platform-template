package cn.liuw.platform.common.util;

/**
 * @author liuw
 * @date 2020-09-01
 * 日期工具类
 */
public class CodeUtil {

    /**
     * 生成用户redis缓存token
     *
     * @return
     */
    public static String getUserToken(String username) {
        // md5 加密32位大写
        return MD5Util.md5Encrypt32Upper(username);
        
    }
}
