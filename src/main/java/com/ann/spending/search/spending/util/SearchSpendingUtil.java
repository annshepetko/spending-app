package com.ann.spending.search.spending.util;

import java.util.Arrays;
import java.util.List;

public class SearchSpendingUtil {

    public static List<String> getKeyWordsFromPrompt(String s) {
        return Arrays.asList(s.toLowerCase().trim().split("\\s"));
    }
}
