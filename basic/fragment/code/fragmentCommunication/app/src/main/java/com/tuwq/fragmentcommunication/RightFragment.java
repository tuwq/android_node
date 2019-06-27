package com.tuwq.fragmentcommunication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RightFragment extends Fragment {
    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, null);
        text = (TextView) view.findViewById(R.id.tv_text);
        return view;
    }

    public void changeText(String s){
        text.setText(s);
    }
}

