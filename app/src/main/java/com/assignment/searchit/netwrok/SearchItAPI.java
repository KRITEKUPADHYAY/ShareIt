package com.assignment.searchit.netwrok;

import com.assignment.searchit.model.SearchImageResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SearchItAPI {

    @GET("api/")
    Call<SearchImageResponse> searchImage(@QueryMap Map<String, String> queryMap);

}
