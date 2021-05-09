package com.hidoctest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable
{
    @SerializedName("volumeInfo")
    VolumeInfo volumeInfo;

    @SerializedName("saleInfo")
    SalseInfo saleInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public SalseInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SalseInfo saleInfo) {
        this.saleInfo = saleInfo;
    }
}
