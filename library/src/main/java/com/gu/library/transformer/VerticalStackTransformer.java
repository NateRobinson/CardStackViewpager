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
        if (position <= -1f) {
            Log.e("onTransform","position <= -1f ==>"+position);
            page.setTranslationY(-page.getHeight());
        } else if (position <= 0.0f) {
            Log.e("onTransform","position <= 0.0f ==>"+position);
            page.setTranslationY(0f);
            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
            page.setClickable(true);
        } else if (position <= 3.0f) {
            Log.e("onTransform","position <= 3.0f ==>"+position);
            float scale1 = (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, spaceBetweenFirAndSecWith * (1 + position))) / (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, spaceBetweenFirAndSecWith));
            //控制下面卡片的可见度
            page.setAlpha(1.0f);
            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
            page.setClickable(false);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale1);
            page.setScaleY(scale1);
            float shiftY1 = (page.getHeight() * 0.5f) * (1 - scale1) + ScreenUtils.dp2px(context, spaceBetweenFirAndSecHeight) * position;
            page.setTranslationY(-page.getHeight() * position + shiftY1);
        }
    }
}
