package com.riskfall.detector.apihelper;

import com.riskfall.detector.apihelper.model.Perawat;
import com.riskfall.detector.apihelper.model.pasien;

import java.util.List;

/**
 * Created by asus on 27/11/2017.
 */

public class ResponseApi {
    public boolean success;
    public String data;
    public Perawat perawat;
    public List<pasien> pasiens;
}
