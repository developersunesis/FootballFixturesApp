package com.android.assessment.footballapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.assessment.footballapp.models.TeamModel;
import com.android.assessment.footballapp.workers.ApiWorker;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.assessment.footballapp.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.android.assessment.footballapp.workers.Constants.NAME;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     PlayerListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class PlayerListDialogFragment extends BottomSheetDialogFragment {


    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    private ApiWorker apiWorker = retrofit.create(ApiWorker.class);

    ArrayList<TeamModel> teamModels = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_list_dialog, container, false);

        rootView.findViewById(R.id.closeDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return rootView;
    }

    private void populatePlayers(final RecyclerView recyclerView){
        apiWorker.obtainTeam(66).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        try {
                            JSONObject object = new JSONObject(response.body());
                            JSONArray squad = object.getJSONArray("squad");

                            for(int i = 0; i <squad.length(); i++){
                                JSONObject obj = squad.getJSONObject(i);
                                TeamModel teamModel = new TeamModel();
                                teamModel.setName(obj.getString(NAME));
                                teamModel.setPosition(obj.getString("position"));
                                teamModels.add(teamModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recyclerView.setAdapter(new PlayerAdapter(teamModels));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Unable to obtain players! \nPlease ensure you have an active internet and retry!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = view.findViewById(R.id.players);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        populatePlayers(recyclerView);

    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text,text1, text2;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_player_list_dialog_item, parent, false));
            text = itemView.findViewById(R.id.text);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);

        }

    }

    private class PlayerAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final int mItemCount;
        private final ArrayList<TeamModel> teamModels;

        PlayerAdapter(ArrayList<TeamModel> teamModels) {
            mItemCount = teamModels.size();
            this.teamModels = teamModels;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TeamModel model = teamModels.get(position);
            holder.text.setText(String.valueOf(position+1));
            holder.text1.setText(model.getName());
            holder.text2.setText(model.getPosition());
        }

        @Override
        public int getItemCount() {
            return mItemCount;
        }

    }

}
