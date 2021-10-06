package com.wangd.utils;

import javafx.scene.control.IndexRange;

import java.security.Key;
import java.util.*;

/**
 * @author wangd
 */
public class MapUtils {
    public static <k, v> Map<k, v> subMap(Map<k, v> baseMap, Integer startIndex, Integer step){
        Map<k, v> tempMap = new HashMap<>();
        ArrayList<k> baseList = new ArrayList<>(baseMap.keySet());
        List<k> subList = null;

        if (startIndex + step > baseList.size()){
            subList = baseList.subList(startIndex, baseList.size());
        } else {
            subList = baseList.subList(startIndex, startIndex + step);
        }

        for (k k : subList) {
            tempMap.put(k, baseMap.get(k));
        }
        return tempMap;
    }
}
