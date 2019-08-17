package com.android.assessment.footballapp.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.assessment.footballapp.R;
import com.android.assessment.footballapp.adapters.FixturesAdapter;
import com.android.assessment.footballapp.adapters.TeamsAdapter;
import com.android.assessment.footballapp.models.FixtureModel;
import com.android.assessment.footballapp.models.ScrollState;
import com.android.assessment.footballapp.workers.ApiWorker;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
public class TeamsFragment extends Fragment {

    RecyclerView recyclerView;

    public TeamsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);
        recyclerView = rootView.findViewById(R.id.teams);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new TeamsAdapter(getActivity()));
        return rootView;
    }
}
