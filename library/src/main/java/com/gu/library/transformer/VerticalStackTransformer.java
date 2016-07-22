package com.gu.library.transformer;

import android.content.Context;
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
        if (position <= -1) {
            page.setAlpha(0.0f);
        } else if (position <= 0.0f) {
            page.setAlpha(1.0f);
            page.setTranslationY(0f);
        } else if (position <= 1.0f) {
            float scale1 = (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, spaceBetweenFirAndSecWith * (1 + position))) / (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, spaceBetweenFirAndSecWith));
            page.setAlpha(1.0f);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale1);
            page.setScaleY(scale1);
            float shiftY1 = (page.getHeight() * 0.5f) * (1 - scale1) + ScreenUtils.dp2px(context, spaceBetweenFirAndSecHeight) * position;
            page.setTranslationY(-page.getHeight() * position + shiftY1);
        } else if (position <= 2.0f) {
            float scale2 = (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, spaceBetweenFirAndSecWith * (1 + position))) / (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, spaceBetweenFirAndSecWith));
            page.setAlpha(1.0f);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale2);
            page.setScaleY(scale2);
            float shiftY2 = (page.getHeight() * 0.5f) * (1 - scale2) + ScreenUtils.dp2px(context, spaceBetweenFirAndSecHeight) * position;
            page.setTranslationY(-page.getHeight() * position + shiftY2);
        }
    }
}
