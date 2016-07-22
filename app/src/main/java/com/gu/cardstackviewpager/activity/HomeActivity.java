package com.gu.cardstackviewpager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.gu.cardstackviewpager.R;
import com.gu.cardstackviewpager.adapter.ContentFragmentAdapter;
import com.gu.cardstackviewpager.fragment.CardFragment;
import com.gu.library.OrientedViewPager;
import com.gu.library.transformer.VerticalStackTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nate on 2016/7/22.
 */
public class HomeActivity extends AppCompatActivity {

    private OrientedViewPager mOrientedViewPager;

    private ContentFragmentAdapter mContentFragmentAdapter;

    private List<Fragment> mFragments=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mOrientedViewPager= (OrientedViewPager) findViewById(R.id.view_pager);


        mFragments.add(new CardFragment());
        mFragments.add(new CardFragment());
        mFragments.add(new CardFragment());
        mFragments.add(new CardFragment());
        mFragments.add(new CardFragment());


        mContentFragmentAdapter = new
                ContentFragmentAdapter(getSupportFragmentManager(), mFragments);
        mOrientedViewPager.setOrientation(OrientedViewPager.Orientation.VERTICAL);
        mOrientedViewPager.setOffscreenPageLimit(4);
        mOrientedViewPager.setPageTransformer(true, new VerticalStackTransformer(getApplicationContext()));
        mOrientedViewPager.setAdapter(mContentFragmentAdapter);

    }
}
