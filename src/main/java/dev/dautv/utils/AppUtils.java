package dev.dautv.utils;

import org.apache.commons.lang3.SystemUtils;

public class AppUtils {
    public static String getDriver() {
        if (SystemUtils.IS_OS_LINUX) {
            return "driver_new/chromedriver";
        } else {
            return "driver_new\\chromedriver.exe";
        }
    }
}
