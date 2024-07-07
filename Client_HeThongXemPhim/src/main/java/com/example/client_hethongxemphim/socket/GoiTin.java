package com.example.client_hethongxemphim.socket;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

public class GoiTin<T> implements  Serializable {
    @Expose(serialize = true, deserialize = true)
    public ArrayList<T> listT = new ArrayList<>();
    public YeuCau yeuCau;

    public GoiTin() {
    }

    public GoiTin(ArrayList<T> listT, YeuCau yeuCau) {
        this.listT = listT;
        this.yeuCau = yeuCau;
    }

    @Override
    public String toString() {
        return "GoiTin{" +
                "listT=" + listT +
                ", yeuCau=" + yeuCau +
                '}';
    }

    public YeuCau getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(YeuCau yeuCau) {
        this.yeuCau = yeuCau;
    }

    public ArrayList<T> getListT() {
        return listT;
    }

    public void setListT(ArrayList<T> listT) {
        this.listT = listT;
    }
}
