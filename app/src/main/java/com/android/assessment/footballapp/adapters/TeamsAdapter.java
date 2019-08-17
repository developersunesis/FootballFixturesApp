package com.android.assessment.footballapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.assessment.footballapp.R;
import com.android.assessment.footballapp.fragments.PlayerListDialogFragment;
import com.android.assessment.footballapp.models.TeamModel;


import java.util.ArrayList;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyViewHolder>{

    private FragmentActivity context;


    private String[] clubs = {
            "Man United", "Barcelona", "Real Madrid", "Atletico-Mardrid", "Arsenal", "Chelsea",
            "Juventus", "Liverpool", "Man City", "Paris Saint", "Bayern-Munchen",
            "Barcelona", "Real Madrid", "Atletico-Mardrid", "Arsenal", "Chelsea",
            "Juventus", "Liverpool", "Man City", "Paris Saint", "Bayern-Munchen"
    };

    private int[] svgs= {
            R.drawable.ic_manchester_united_fc, R.drawable.ic_barcelona, R.drawable.ic_real_madrid, R.drawable.ic_atletico_de_madrid, R.drawable.ic_arsenal,
            R.drawable.ic_chelsea, R.drawable.ic_juventus, R.drawable.ic_liverpool, R.drawable.ic_manchester_city,
            R.drawable.ic_paris_saint_germain, R.drawable.ic_bayern_munchen,
            R.drawable.ic_barcelona, R.drawable.ic_real_madrid, R.drawable.ic_atletico_de_madrid, R.drawable.ic_arsenal,
            R.drawable.ic_chelsea, R.drawable.ic_juventus, R.drawable.ic_liverpool, R.drawable.ic_manchester_city,
            R.drawable.ic_paris_saint_germain, R.drawable.ic_bayern_munchen
    };

    private ArrayList<TeamModel> teamArrayList = new ArrayList<>();


    public TeamsAdapter(FragmentActivity context){
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnail;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            thumbnail =  view.findViewById(R.id.thumbnail);
        }
    }

    @NonNull
    @Override
    public TeamsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsAdapter.MyViewHolder holder, int position) {
        holder.title.setText(clubs[holder.getAdapterPosition()]);
        holder.thumbnail.setImageDrawable(context.getResources().getDrawable(svgs[holder.getAdapterPosition()]));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new PlayerListDialogFragment().show(context.getSupportFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return clubs.length;
    }
}
