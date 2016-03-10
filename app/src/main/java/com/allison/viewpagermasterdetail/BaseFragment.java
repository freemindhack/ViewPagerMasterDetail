package com.allison.viewpagermasterdetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by CJH on 1/29/2016.
 */
public class BaseFragment extends Fragment {

    public Bridge mBridget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mBridget = (MainActivity) getActivity();
    }
}
