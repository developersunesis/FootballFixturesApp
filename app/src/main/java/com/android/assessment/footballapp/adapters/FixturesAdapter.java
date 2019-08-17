package com.android.assessment.footballapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.assessment.footballapp.R;
import com.android.assessment.footballapp.models.FixtureModel;

import java.text.MessageFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FixturesAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<FixtureModel> data;

    public FixturesAdapter(Activity context, ArrayList<FixtureModel> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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
        View rowView = context.getLayoutInflater().inflate(R.layout.today_fixture_item, null);
        FixtureModel model = data.get(i);

        TextView status = rowView.findViewById(R.id.status);
        TextView utcTime = rowView.findViewById(R.id.utc_time);
        TextView matchDay = rowView.findViewById(R.id.match_day);
        TextView club1 = rowView.findViewById(R.id.club1);
        TextView club2 = rowView.findViewById(R.id.club2);
        TextView extra_time = rowView.findViewById(R.id.extra_time);
        TextView homeTeamScore = rowView.findViewById(R.id.homeTeamScore);
        TextView awayTeamScore = rowView.findViewById(R.id.awayTeamScore);

        status.setText(model.getStatus());
        utcTime.setText(formatUTCTime(model.getUtcTime()));
        matchDay.setText(MessageFormat.format("MD : {0}", model.getMatchDay()));
        club1.setText(model.getHomeTeamName());
        club2.setText(model.getAwayTeamName());

        homeTeamScore.setText(String.valueOf(model.getHomeTeamScore()));
        awayTeamScore.setText(String.valueOf(model.getAwayTeamScore()));
        return rowView;
    }

    private String formatUTCTime(String utcTime){
        String result = "19:00";
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        try {
            result = format.format(parser.parse(utcTime.replace("T", "").replace("Z","")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
