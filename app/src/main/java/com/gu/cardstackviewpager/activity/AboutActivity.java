package com.gu.cardstackviewpager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gu.cardstackviewpager.R;

/**
 * Created by Nate on 2016/7/22.
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置切换动画
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        findViewById(R.id.back_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.my_blog_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.URL_KEY, getString(R.string.my_blog));
                Intent intent = new Intent(AboutActivity.this, WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        findViewById(R.id.my_git_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.URL_KEY, getString(R.string.my_github));
                Intent intent = new Intent(AboutActivity.this, WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


    @Override
    public void finish() {
        super.finish();
        //设置切换动画
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
