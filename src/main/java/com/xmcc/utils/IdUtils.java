package com.xmcc.utils;

import java.util.UUID;

/**
 * @author 邓桥
 * @date 2019-04-17 14:27
 */
public class IdUtils {
        public static String createIdbyUUID() {
            return UUID.randomUUID().toString().replace("-", "");
        }
}
