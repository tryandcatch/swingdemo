/**
 * Created by Huangxiutao on 2017/7/10.
 */
public class StringUtil {
    /**
     * 判断string是否为空（null或者是空字符串）
     * @param s
     * @return
     */
    public static boolean idEmpty(String s){
        if(null!=s&&!"".equals(s)) return false;
        return true;
    }
}
