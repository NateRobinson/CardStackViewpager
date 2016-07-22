package com.gu.cardstackviewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gu.cardstackviewpager.R;


/**
 * Created by Nate on 2016/7/22.
 */
public class CardFragment extends Fragment {

    public static CardFragment newInstance(int index) {
        CardFragment fragment = new CardFragment();
        Bundle bdl = new Bundle();
        //bdl.putInt(EXTRA_COLOR, backgroundColor);
        fragment.setArguments(bdl);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card, container, false);
//        Bundle bdl = getArguments();
//        mMainLayout = (FrameLayout) v.findViewById(R.id.main_layout);
//        LayerDrawable bgDrawable = (LayerDrawable) mMainLayout.getBackground();
//        GradientDrawable shape = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.background_shape);
//        shape.setColor(bdl.getInt(EXTRA_COLOR));
        return v;
    }
}