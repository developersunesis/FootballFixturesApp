package com.android.assessment.footballapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.assessment.footballapp.R;
import com.android.assessment.footballapp.adapters.TableAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class TableFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static TableFragment newInstance(int index) {
        TableFragment fragment = new TableFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_table, container, false);
        ListView listView = root.findViewById(R.id.listView);
        listView.setAdapter(new TableAdapter(getActivity()));
        return root;
    }
}