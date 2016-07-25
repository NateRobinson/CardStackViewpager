package com.gu.library.transformer;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.gu.library.utils.ScreenUtils;


/**
 * Created by Nate on 2016/7/22.
 */
public class VerticalStackTransformer extends VerticalBaseTransformer {

    private Context context;
    private int spaceBetweenFirAndSecWith = 10 * 2;//第一张卡片和第二张卡片宽度差  dp单位
    private int spaceBetweenFirAndSecHeight = 10;//第一张卡片和第二张卡片高度差   dp单位

    public VerticalStackTransformer(Context context) {
        this.context = context;
    }

    public VerticalStackTransformer(Context context, int spaceBetweenFirAndSecWith, int spaceBetweenFirAndSecHeight) {
        this.context = context;
        this.spaceBetweenFirAndSecWith = spaceBetweenFirAndSecWith;
        this.spaceBetweenFirAndSecHeight = spaceBetweenFirAndSecHeight;
    }

    @Override
    protected void onTransform(View page, float position) {
        if (position <= 0.0f) {
            page.setAlpha(1.0f);
            Log.e("onTransform", "position <= 0.0f ==>" + position);
            page.setTranslationY(0f);
            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
            page.setClickable(true);
        } else if (position <= 3.0f) {
            Log.e("onTransform", "position <= 3.0f ==>" + position);
            float scale = (float) (page.getWidth() - ScreenUtils.dp2px(context, spaceBetweenFirAndSecWith * position)) / (float) (page.getWidth());
            //控制下面卡片的可见度
            page.setAlpha(1.0f);
            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
            page.setClickable(false);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setTranslationY(-page.getHeight() * position + (page.getHeight() * 0.5f) * (1 - scale) + ScreenUtils.dp2px(context, spaceBetweenFirAndSecHeight) * position);
        }
    }
}
