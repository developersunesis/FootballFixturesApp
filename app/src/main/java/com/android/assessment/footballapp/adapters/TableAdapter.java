package com.android.assessment.footballapp.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.assessment.footballapp.R;


public class TableAdapter extends BaseAdapter {

    private Activity context;
    private String[] clubs = {
            "Manchester United", "Barcelona", "Real Madrid", "Atletico-Mardrid", "Arsenal", "Chelsea",
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


    public TableAdapter(Activity context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return clubs.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = context.getLayoutInflater().inflate(R.layout.table_item, null);
        TextView nos = rootView.findViewById(R.id.nos);
        TextView club_name= rootView.findViewById(R.id.club_name);
        ImageView club_logo = rootView.findViewById(R.id.club_logo);

        club_logo.setImageDrawable(context.getResources().getDrawable(svgs[i]));
        nos.setText(String.valueOf(i+1));
        club_name.setText(clubs[i]);
        return rootView;
    }
}
