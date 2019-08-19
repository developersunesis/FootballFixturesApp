/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.android.assessment.footballapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.assessment.footballapp.R;
import com.android.assessment.footballapp.adapters.FixturesAdapter;
import com.android.assessment.footballapp.adapters.MyViewPagerAdapter;
import com.android.assessment.footballapp.database.AppDatabaseClient;
import com.android.assessment.footballapp.models.FixtureModel;
import com.android.assessment.footballapp.models.ScrollState;
import com.android.assessment.footballapp.workers.ApiWorker;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.android.assessment.footballapp.workers.Constants.AWAY_TEAM;
import static com.android.assessment.footballapp.workers.Constants.EXTRA_TIME;
import static com.android.assessment.footballapp.workers.Constants.FULL_TIME;
import static com.android.assessment.footballapp.workers.Constants.HALF_TIME;
import static com.android.assessment.footballapp.workers.Constants.HOME_TEAM;
import static com.android.assessment.footballapp.workers.Constants.MATCHES;
import static com.android.assessment.footballapp.workers.Constants.MATCH_DAY;
import static com.android.assessment.footballapp.workers.Constants.NAME;
import static com.android.assessment.footballapp.workers.Constants.PENALTY;
import static com.android.assessment.footballapp.workers.Constants.SCORE;
import static com.android.assessment.footballapp.workers.Constants.STATUS;
import static com.android.assessment.footballapp.workers.Constants.UTC_DATE;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FixturesFragment extends Fragment {

    //Assign variables ranging from views, retrofit, apiworker
    private TextView empty;
    private ListView listView;
    private LinearLayout emptyLinearLayout;
    private Button retry;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    private ApiWorker apiWorker = retrofit.create(ApiWorker.class);

    public FixturesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.view_page, container, false);
        empty = rootView.findViewById(R.id.empty);
        listView = rootView.findViewById(R.id.listView);
        emptyLinearLayout = rootView.findViewById(R.id.emptyLayout);
        retry = rootView.findViewById(R.id.retry);

        //Obtains fixtures between 2 dates
        Call<String> fixtureModelCall = apiWorker.obtainTodaysFixtures("2018-08-13", "2018-08-23");
        fixtureModelCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(call.isExecuted()){
                    if(response.isSuccessful()){
                        if(response.body() != null) {
                            populateFixtures(response.body());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                emptyLinearLayout.setVisibility(View.VISIBLE);
                empty.setText(R.string.empty);
                retry.setVisibility(View.VISIBLE);
                retry.setEnabled(true);
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry.setEnabled(false);
                empty.setText(R.string.refreshing);

                Call<String>  fixtureModelCall = apiWorker.obtainTodaysFixtures("2018-08-13", "2018-08-23");
                fixtureModelCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(call.isExecuted()){
                            if(response.isSuccessful()){
                                if(response.body() != null) {
                                    populateFixtures(response.body());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getActivity(), "Unable to connect!\nPlease check that you have an active internet connection", Toast.LENGTH_SHORT).show();
                        emptyLinearLayout.setVisibility(View.VISIBLE);
                        empty.setText(R.string.empty);
                        retry.setVisibility(View.VISIBLE);
                        retry.setEnabled(true);
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "You clicked fixture: "+ i, Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(i == SCROLL_STATE_IDLE){
                    EventBus.getDefault().postSticky(new ScrollState(false));
                } else {
                    EventBus.getDefault().postSticky(new ScrollState(true));
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        return rootView;
    }

    private void populateFixtures(String data){
        if(data != null) {
            JSONObject jsonObject;
            ArrayList<FixtureModel> fixtureModelList = new ArrayList<>();

            try {
                jsonObject = new JSONObject(data);
                JSONArray arrayMatches = jsonObject.getJSONArray(MATCHES);

                for (int i = 0; i < arrayMatches.length(); i++) {
                    FixtureModel model = new FixtureModel();
                    JSONObject object = arrayMatches.getJSONObject(i);

                    model.setStatus(object.getString(STATUS));
                    model.setHomeTeamName(object.getJSONObject(HOME_TEAM).getString(NAME));
                    model.setAwayTeamName(object.getJSONObject(AWAY_TEAM).getString(NAME));

                    String md = object.getString(MATCH_DAY);

                    model.setMatchDay((!md.equals("null")) ? Integer.parseInt(object.getString(MATCH_DAY)) : 0);
                    model.setUtcTime(object.getString(UTC_DATE));
                    model.setHomeTeamScore(obtainCurrentScore(object, HOME_TEAM));
                    model.setAwayTeamScore(obtainCurrentScore(object, AWAY_TEAM));

                    fixtureModelList.add(model);
                }

                if(fixtureModelList.size() < 1) {
                    empty.setText(R.string.empty);
                    retry.setVisibility(View.VISIBLE);
                } else {
                    emptyLinearLayout.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    FixturesAdapter adapter = new FixturesAdapter(getActivity(), fixtureModelList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private int obtainCurrentScore(JSONObject jsonObject, String team) {
        int score = 0;
        try {
            jsonObject = jsonObject.getJSONObject(SCORE);

            if(!jsonObject.getJSONObject(FULL_TIME).isNull(team)){
                score = jsonObject.getJSONObject(FULL_TIME).getInt(team);
            }

            if(!jsonObject.getJSONObject(HALF_TIME).isNull(team)){
                score = (score < jsonObject.getJSONObject(HALF_TIME).getInt(team)) ?
                        jsonObject.getJSONObject(HALF_TIME).getInt(team) : score;
            }

            if(!jsonObject.getJSONObject(EXTRA_TIME).isNull(team)){
                score = (score < jsonObject.getJSONObject(EXTRA_TIME).getInt(team)) ?
                        jsonObject.getJSONObject(EXTRA_TIME).getInt(team) : score;
            }

            if(!jsonObject.getJSONObject(PENALTY).isNull(team)){
                score = (score < jsonObject.getJSONObject(PENALTY).getInt(team)) ?
                        (jsonObject.getJSONObject(PENALTY).getInt(team) + score) : score;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        return score;
    }
}
