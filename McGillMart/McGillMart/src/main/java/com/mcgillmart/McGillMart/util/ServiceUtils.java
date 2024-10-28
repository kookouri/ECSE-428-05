package com.mcgillmart.McGillMart.util;

import java.util.ArrayList;
import java.util.List;

public class ServiceUtils {
   public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}