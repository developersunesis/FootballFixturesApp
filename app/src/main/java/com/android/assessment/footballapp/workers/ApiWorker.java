/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.android.assessment.footballapp.workers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiWorker {

    /**
     * This method is to obtain all fixtures between two dates
     * @return = return a JSONString
     */
    @Headers("X-Auth-Token: "+Constants.API_KEY)
    @GET("v2/matches")
    Call<String> obtainTodaysFixtures(@Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo);

    /**
     * This method is to obtain all competitions
     * @return = return a JSONString
     */
    @Headers("X-Auth-Token: "+Constants.API_KEY)
    @GET("v2/competitions")
    Call<String> obtainCompetitions();

    /**
     * This method is to obtain detail about a team
     * @return = return a JSONString
     */
    @Headers("X-Auth-Token: "+Constants.API_KEY)
    @GET("v2/teams/{id}")
    Call<String> obtainTeam(@Path("id") int id);
}
