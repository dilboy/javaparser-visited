package org.javaparser.support.matcher;

/**
 * @author liang
 * @date 2022/8/14
 */
public class PoiIdNameMatcher {
    public static boolean ifMatch(String key){
        String s = key.toLowerCase();
        if (s.contains("poiid")||s.contains("shopid")) {
            return true;
        }
        return false;
    }
}
