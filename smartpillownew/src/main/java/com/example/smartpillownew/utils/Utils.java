package com.example.smartpillownew.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by a450J on 2018/8/22.
 */

public class Utils {

    /**判断是否为正确的手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**返回整数部分数据
     * @param data
     * @return
     */
    public static int RetInteger(String data){
        String[] datas = data.split("\\.");
        return Integer.valueOf(datas[0]);
    }

    /**返回0~a偶数的数目
     * @param a
     * @return
     */
    public static int EvenNumber(int a){
        a++;
        if (a%2==0){
            return a/2;
        }else {
            return (a+1)/2;
        }
    }
}
