package com.ucmap.dingdinghelper.utils;

import android.os.Build;


public interface Constants {
    String IS_SIGN = "is_sign";
    String ACCOUNT = "account";
    String PASSWORD = "password";


    String DATE = "date";


    int RESET_PASSWORD=0x1100;
    String MORNING_CHECK_IN_TIME = "MORNING_CHECK_IN_TIME";
    String AFTERNOON_CHECK_IN_TIME = "AFTERNOON_CHECK_IN_TIME";

    String ACCOUNT_LIST = "acccount_list";


    String POINT_SERVICES_ORDER = "settings put secure enabled_accessibility_services  com.ucmap.dingdinghelper/com.ucmap.dingdinghelper.services.DingDingHelperAccessibilityService";

    String ENABLE_SERVICE_PUT = "settings put secure accessibility_enabled 1";

    String DISENABLE_SERVICE_PUT = "settings put secure accessibility_enabled 0";


    String IS_NOTITY_TYPE_CHECK_IN = "is_notity_type_check_in";

    boolean IS_NOTITY_TYPE_CHECK_IN_TAG = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH ? true : false;
}
