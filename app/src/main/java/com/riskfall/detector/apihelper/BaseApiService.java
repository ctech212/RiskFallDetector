package com.riskfall.detector.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface BaseApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseApi> loginRequest(@Field("username") String username,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("regispasien.php")
    Call<ResponseApi> registerRequest(@Field("no_rekam_medis") String no_medis,
                                      @Field("idperawat") String idperawat,
                                      @Field("nama_pasien") String p_name,
                                      @Field("usia") String p_age,
                                      @Field("jenis_kelamin") String p_gender,
                                      @Field("diagnosis") String p_diagnosis,
                                      @Field("kelas") String p_class,
                                      @Field("kamar") String p_room);

    @FormUrlEncoded
    @POST("parentassesment.php")
    Call<ResponseApi> parentAssesmentRequest(
            @Field("no_rekam_medis") String no_rekam_medis,
            @Field("riwayat_jatuh") String riwayat_jatuh,
            @Field("diagnosa_sekunder") String diagnosa_sekunder,
            @Field("alat_bantu") String alat_bantu,
            @Field("infus_heparin") String infus_heparin,
            @Field("gaya_berjalan") String gaya_berjalan,
            @Field("status_mental") String status_mental);

    @FormUrlEncoded
    @POST("pediatriassesment.php")
    Call<ResponseApi> pediatriAssesmentRequest(
            @Field("no_rekam_medis") String no_rekam_medis,
            @Field("usia") String usia,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("diagnosis") String diagnosis,
            @Field("gangguan_kognitif") String gangguan_kognitif,
            @Field("faktor_lingkungan") String faktor_lingkungan,
            @Field("pembedahan") String pembedahan,
            @Field("medikomentosa") String medikomentosa);


    @FormUrlEncoded
    @POST("hasil.php")
    Call<ResponseApi> resultRequest(
            @Field("no_rekam_medis") String no_rekam_medis,
            @Field("hasil") String hasil,
            @Field("ket") String ket,
            @Field("rekomendasi") String rekomendasi);


    @FormUrlEncoded
    @POST("lansiaassesment.php")
    Call<ResponseApi> lansiaAssesmentRequest(
            @Field("no_rekam_medis") String no_rekam_medis,
            @Field("riwayat_jatuh") String karena_jatuh,
            @Field("status_mental") String pernah_jatuh,
            @Field("penglihatan") String delirium,
            @Field("kebiasaan_berkemih") String disorientasi,
            @Field("transfer") String agitasi,
            @Field("mobilitas") String mobilitas);

    @FormUrlEncoded
    @POST("kesensor.php")
    Call<ResponseApi> kesensorRequest(
            @Field("no_rekam_medis") String no_rekam_medis,
            @Field("kamar") String kamar,
            @Field("kondisi") String kondisi);
}

