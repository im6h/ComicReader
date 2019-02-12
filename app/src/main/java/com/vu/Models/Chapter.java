package com.vu.Models;

import java.util.List;

public class Chapter
{
    private String Name ;
    private List<String> Links;

    public Chapter() {
    }

    public Chapter(String name, List<String> links) {
        Name = name;
        Links = links;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getLinks() {
        return Links;
    }

    public void setLinks(List<String> links) {
        Links = links;
    }
}