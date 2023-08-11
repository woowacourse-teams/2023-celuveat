package com.celuveat.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtil {

    public static <T> List<T> relocateTargetToFirst(T target, List<T> list) {
        List<T> result = new ArrayList<>(list);
        Collections.swap(result, 0, result.indexOf(target));
        return result;
    }
}
