package com.riskfall.detector.apihelper;

public class UtilsApi {

    public static final String BASE_URL_API = "http://muntako.esy.es/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}