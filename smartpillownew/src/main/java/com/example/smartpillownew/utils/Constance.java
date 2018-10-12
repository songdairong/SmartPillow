package com.example.smartpillownew.utils;

/**
 * Created by a450J on 2018/8/8.
 */

public class Constance {

    public static final String CHANNEALID = "warn_notification";

    public static final String WARNSELECTED_KEY = "warnSelected_key";

    public static final String RANGESET_KEY = "range_set_key";

    public static final String LOGININFO_KEY = "login_info_key";

    public static final String LOGIN = "com.example.a450j.login.activity";

    public static final String EXIT = "com.example.a450j.exit.activity";

    public static final String NOTIFY_SETTINGS_CHANGE = "com.example.a450j.notify.settings.change";

    private static final String BASE_URL = "http://111.231.223.127/sleep-monitoring/";

    //实时数据
    public static final String BASE_URL_REAL_TIME = BASE_URL+"realtime-data.php?";

    //每周数据
    public static final String BASE_URL_WEEK_DATA = BASE_URL+"week-data.php?";

    //获取参考参数设置
    public static final String BASE_URL_GET_RANGE = BASE_URL+"range_recommend.php?";

    //设置参数
    public static final String BASE_URL_SEND_RANGE = BASE_URL+"set-preference.php?";

    //所有的异常记录
    public static final String BASE_URL_REPORT =BASE_URL+"usual-info.php?";

    //最新的异常数据
    public static final String BASE_URL_ABNORMAL =BASE_URL+"usual-info-n.php?";

    //历史记录
    public static final String BASE_URL_HISTORY =BASE_URL+"history.php";

    //个人用户注册
    public static final String REGISTER_URL = BASE_URL+"register.php?";

    //添加床位信息
    public static final String ADD_BED_INFO_URL = BASE_URL+"add-bedinfo.php?";

    //登陆
    public static final String LOGIN_URL = BASE_URL+"login.php?";

    //三分钟的异常数据
    public static final String ABNORMAL_URL = BASE_URL+"abnormal-record.php?";
}
