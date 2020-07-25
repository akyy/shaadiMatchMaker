package com.shaadi.match.maker.featureModules.landing.repo;

import com.shaadi.match.maker.db.tables.MatchingUsersTable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ajay
 */
public interface HomeActivityRestApi {

    @GET("/api/")
    Call<MatchingUsersTable> getAllMatchesDynamic(@Query("results") String count);


}
