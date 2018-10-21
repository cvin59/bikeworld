package com.team17.bikeworld.crawl.dict;

import java.util.ArrayList;
import java.util.List;

public class CateDictObj {
    private static List<CateObj> dict;

    static {
        dict = new ArrayList<CateObj>();

        dict.add(new CateObj("absorber", new String[]{"shock absorbers"}, new String[]{"shock", "absorber", "fork"}));
        dict.add(new CateObj("exhaust", new String[]{""}, new String[]{""}));
        dict.add(new CateObj("mirror", new String[]{"side mirror", "mirrors"}, new String[]{""}));
        dict.add(new CateObj("tyre", new String[]{"tire"}, new String[]{""}));
        dict.add(new CateObj("brake", new String[]{"brakes"}, new String[]{""}));
        dict.add(new CateObj("cam shaft", new String[]{""}, new String[]{""}));

    }

    public static CateObj checkCate(String cateName) {
        for (CateObj cateObj : dict) {
            for (String str : cateObj.getAcceptWords()) {
                if (cateName.equals(str)) {
                    return cateObj;
                }
            }
        }
        return null;
    }

    public static boolean checkName(CateObj cateObj, String itemName) {
        for (String str : cateObj.getNameContain()) {
            if (itemName.contains(str)) {
                return true;
            }
        }
        return false;
    }
}
