/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.android.assessment.footballapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.assessment.footballapp.Competition;
import com.android.assessment.footballapp.R;
import com.android.assessment.footballapp.adapters.FixturesAdapter;
import com.android.assessment.footballapp.models.FixtureModel;
import com.android.assessment.footballapp.models.ScrollState;
import com.android.assessment.footballapp.workers.ApiWorker;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.android.assessment.footballapp.workers.Constants.AWAY_TEAM;
import static com.android.assessment.footballapp.workers.Constants.COMPETITIONS;
import static com.android.assessment.footballapp.workers.Constants.COUNT;
import static com.android.assessment.footballapp.workers.Constants.EXTRA_TIME;
import static com.android.assessment.footballapp.workers.Constants.FULL_TIME;
import static com.android.assessment.footballapp.workers.Constants.HALF_TIME;
import static com.android.assessment.footballapp.workers.Constants.HOME_TEAM;
import static com.android.assessment.footballapp.workers.Constants.ID;
import static com.android.assessment.footballapp.workers.Constants.MATCHES;
import static com.android.assessment.footballapp.workers.Constants.MATCH_DAY;
import static com.android.assessment.footballapp.workers.Constants.NAME;
import static com.android.assessment.footballapp.workers.Constants.PENALTY;
import static com.android.assessment.footballapp.workers.Constants.SCORE;
import static com.android.assessment.footballapp.workers.Constants.STATUS;
import static com.android.assessment.footballapp.workers.Constants.TITLE;
import static com.android.assessment.footballapp.workers.Constants.UTC_DATE;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CompetitionsFragment extends Fragment {


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

    private ArrayList<Integer> competitionIds = new ArrayList<>();
    private List<String> competitions = new ArrayList<>();


    public CompetitionsFragment() {
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

        Call<String> fixtureModelCall = apiWorker.obtainCompetitions();
        fixtureModelCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(call.isExecuted()){
                    if(response.isSuccessful()){
                        if(response.body() != null) {
                            populateCompetitions(response.body());
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


        //Retry button to retry api call if recently failed
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry.setEnabled(false);
                empty.setText(R.string.refreshing);

                Call<String>  fixtureModelCall = apiWorker.obtainCompetitions();
                fixtureModelCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(call.isExecuted()){
                            if(response.isSuccessful()){
                                if(response.body() != null) {
                                    populateCompetitions(response.body());
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
                Intent intent = new Intent(getActivity(), Competition.class);
                intent.putExtra(ID, competitionIds.get(i));
                intent.putExtra(TITLE,  competitions.get(i));
                startActivity(intent);
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

    //populate the competition ListView
    private void populateCompetitions(String data){
        if(data != null) {

            try {
                JSONObject object = new JSONObject(data);
                int count = object.getInt(COUNT);
                JSONArray competitionsArray = object.getJSONArray(COMPETITIONS);

                for(int i = 0; i < count; i++){
                    JSONObject obj = competitionsArray.getJSONObject(i);
                    competitions.add(obj.getString(NAME));
                    competitionIds.add(obj.getInt(ID));
                }

                if(competitions.size() < 1) {
                    empty.setText(R.string.empty);
                    retry.setVisibility(View.VISIBLE);
                } else {
                    emptyLinearLayout.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    listView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.competiton_item, competitions));
                }

            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
