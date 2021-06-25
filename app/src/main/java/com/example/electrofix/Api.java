package com.example.electrofix;

import android.widget.Toast;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("registerCus")
    Call<ResponseBody> registerCustomer(
            @Field("user_name") String user_name,
            @Field("area") String area,
            @Field("address") String address,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("registerRep")
    Call<ResponseBody> registerRepairer(
            @Field("user_name") String user_name,
            @Field("area") String area,
            @Field("address") String address,
            @Field("reg_no") String reg_no,
            @Field("cnic") String cnic,
            @Field("base_fare") String base_fare,
            @Field("skill") String skill,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("loginCus")
    Call<List<Customer>> loginCus(
            @Field("phone") String phone,
            @Field("pwd") String pwd
    );

    @FormUrlEncoded
    @POST("loginRep")
    Call<List<Repairer>> loginRep(
            @Field("phone") String phone,
            @Field("pwd") String pwd
    );

    @FormUrlEncoded
    @POST("getFilteredRepairers")
    Call<List<RepairerList>> getFilteredRepairers(
            @Field("area_name") String area_name,
            @Field("skill_name") String skill_name,
            @Field("fare_name") String fare_name,
            @Field("sort_name") String sort_name
    );

    @FormUrlEncoded
    @POST("getRequestsForRep")
    Call<List<RequestList>> getRequestsForRep(
            @Field("rep_id") int rep_id

    );

    @FormUrlEncoded
    @POST("updateCustomer")
    Call<ResponseBody> updateCustomer(
            @Field("id") int id,
            @Field("name") String name,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("sendRequest")
    Call<ResponseBody> sendRequest(
            @Field("c_id") int c_id,
            @Field("r_id") int r_id
    );

    @FormUrlEncoded
    @POST("getRequestsForCus")
    Call<List<RequestList>> getRequestsForCus(
            @Field("cus_id") int cus_id

    );

    @FormUrlEncoded
    @POST("updateRepairer")
    Call<ResponseBody> updateRepairer(
            @Field("id") int id,
            @Field("name") String name,
            @Field("password") String password

    );


    @FormUrlEncoded
    @POST("acceptRequest")
    Call<ResponseBody> acceptRequest(
            @Field("req_id") int req_id
    );


    @FormUrlEncoded
    @POST("completeRequest")
    Call<ResponseBody> completeRequest(
            @Field("req_id") int req_id,
            @Field("amount") String amount
    );

    @FormUrlEncoded
    @POST("rateRepairer")
    Call<ResponseBody> rateRepairer(
            @Field("rep_id") int rep_id,
            @Field("rate") String rate
    );


    @FormUrlEncoded
    @POST("cancelRequest")
    Call<ResponseBody> cancelRequest(
            @Field("req_id") int req_id
    );

    @FormUrlEncoded
    @POST("repairerTotalPaid")
    Call<ResponseBody> repairerTotalPaid(
            @Field("rep_id") int rep_id
    );

    @FormUrlEncoded
    @POST("updateRequest")
    Call<ResponseBody> updateRequest(
            @Field("req_id") int req_id
    );


    @FormUrlEncoded
    @POST("getCusDashBoardData")
    Call<List<DashboardData>> getCusDashBoardData(
            @Field("cus_id") int cus_id
    );

    @FormUrlEncoded
    @POST("getRepDashBoardData")
    Call<List<DashboardData>> getRepDashBoardData(
            @Field("rep_id") int rep_id
    );


    @GET("/lo")
    Call<ResponseBody> getPosts();

}
