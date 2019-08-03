package com.tuwq.googleplay95.fragment;

import android.view.View;
import android.widget.TextView;

public class AppFragment extends BaseFragment {
    @Override
    public void loadData() {

    }

    public View getSuccessView() {
        TextView textView = new TextView(getActivity());

        textView.setTextSize(25);
        textView.setText(this.getClass().getSimpleName());
        return textView;
    }
}
