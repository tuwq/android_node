package com.tuwq.fragmentcommunication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class LeftFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, null);
        Button btn_call = (Button) view.findViewById(R.id.btn_call);
        btn_call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello");
                // 通过
                RightFragment right = (RightFragment) getActivity().getFragmentManager().findFragmentByTag("right");
                //RightFragment rightFragment = new RightFragment();
                right.changeText("hello");
            }
        });
        return view;
    }

}
