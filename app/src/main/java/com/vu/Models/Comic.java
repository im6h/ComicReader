package com.vu.Models;

import java.util.List;

public class Comic {
    private String Name ;
    private String Image ;
    private String Category ;
    private List<Chapter> Chapters ;

    public Comic() {
    }

    public Comic(String name, String image, String category, List<Chapter> chapters) {
        Name = name;
        Image = image;
        Category = category;
        Chapters = chapters;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public List<Chapter> getChapters() {
        return Chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        Chapters = chapters;
    }
}
