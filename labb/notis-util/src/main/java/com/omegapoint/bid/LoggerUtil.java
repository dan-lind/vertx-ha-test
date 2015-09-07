package com.omegapoint.bid;

import org.apache.log4j.Logger;

public class LoggerUtil {

    public static Logger getLogger() {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        String className = ste.getClassName();
        return Logger.getLogger(className);
    }
}
