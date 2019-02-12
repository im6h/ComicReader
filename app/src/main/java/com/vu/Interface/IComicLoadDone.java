package com.vu.Interface;

import com.vu.Models.Comic;

import java.util.List;

public interface IComicLoadDone {
    void onComicLoadDoneListener(List<Comic> comics);
}
