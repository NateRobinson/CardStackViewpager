package com.gu.cardstackviewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gu.cardstackviewpager.R;


/**
 * Created by Nate on 2016/7/22.
 */
public class CardFragment extends Fragment {
    private static final String INDEX_KEY = "index_key";

    public static CardFragment newInstance(int index) {
        CardFragment fragment = new CardFragment();
        Bundle bdl = new Bundle();
        bdl.putInt(INDEX_KEY, index);
        fragment.setArguments(bdl);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_card, container, false);
        TextView cardNumTv = (TextView) v.findViewById(R.id.card_num_tv);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            cardNumTv.setText(bundle.getInt(INDEX_KEY, 0) + "");
        }
        cardNumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "点击了" + bundle.getInt(INDEX_KEY, 0) + "", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}