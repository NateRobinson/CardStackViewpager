package com.gu.cardstackviewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gu.cardstackviewpager.R;


/**
 * Created by Nate on 2016/7/22.
 */
public class CardFragment extends Fragment {

    private static final String INDEX_KEY = "index_key";

    private int[] colors = new int[]{};

    public static CardFragment newInstance(int index) {
        CardFragment fragment = new CardFragment();
        Bundle bdl = new Bundle();
        bdl.putInt(INDEX_KEY, index);
        fragment.setArguments(bdl);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card, container, false);
        TextView cardNumTv = (TextView) v.findViewById(R.id.card_num_tv);
        Bundle bundle = getArguments();
        if (bundle != null) {
            cardNumTv.setText(bundle.getInt(INDEX_KEY, 0)+"");
        }
        return v;
    }
}