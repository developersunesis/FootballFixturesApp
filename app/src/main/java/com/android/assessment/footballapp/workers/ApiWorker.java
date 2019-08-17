package com.android.assessment.footballapp.workers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiWorker {

    /**
     * This method is to obtain all currencies symbol
     * @return = return a JSONString
     */
    @Headers("X-Auth-Token: bdd906ea49a74c8abfc07f5e4d9b5aa7")
    @GET("v2/matches")
    Call<String> obtainTodaysFixtures(@Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo);

    @Headers("X-Auth-Token: bdd906ea49a74c8abfc07f5e4d9b5aa7")
    @GET("v2/competitions")
    Call<String> obtainCompetitions();

    @Headers("X-Auth-Token: bdd906ea49a74c8abfc07f5e4d9b5aa7")
    @GET("v2/teams/{id}")
    Call<String> obtainTeam(@Path("id") int id);
}
