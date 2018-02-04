package com.riskfall.detector.apihelper.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 03/12/2017.
 */

public class Perawat implements Comparable<Perawat>{
    public int id;

    @SerializedName("nama")
    public String nama;

    @SerializedName("idperawat")
    public String idperawat;


    @Override
    public int compareTo(@NonNull Perawat o) {
        return nama.compareTo(o.nama);
    }
}



