package com.allison.viewpagermasterdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CJH on 1/29/2016.
 */
public class FragmentB extends BaseFragment {

    /**
     *   Create a fragment.
     * @return BaseFragment
     */
    public static FragmentB newInstance() {
        return new FragmentB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_b, container, false);
        return root;
    }
}
