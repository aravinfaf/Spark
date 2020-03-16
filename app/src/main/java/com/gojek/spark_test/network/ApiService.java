package com.gojek.spark_test.network;

import com.gojek.spark_test.model.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("users?page=1")
    Call<Model> getAll();

}
