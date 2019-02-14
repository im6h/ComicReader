package com.vu.Common;

import com.vu.Models.Chapter;
import com.vu.Models.Comic;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static List<Comic> comicList = new ArrayList<>();
    public static Comic comicSelected;
    public static List<Chapter> chapterList;
    public static Chapter chapterSelected;
    public static int chapterIndex=-1;

    public static String formatString(String name) {
        StringBuilder subName = new StringBuilder();
        if (name.length() >15){
            subName.append(name.substring(0,16)).append(".....");
        }
        return subName.toString();
    }
}
