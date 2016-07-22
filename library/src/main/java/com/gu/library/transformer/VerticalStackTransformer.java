package com.gu.library.transformer;

import android.content.Context;
import android.view.View;

import com.gu.library.utils.ScreenUtils;


/**
 * Created by Nate on 2016/7/22.
 */
public class VerticalStackTransformer extends VerticalBaseTransformer {

    private Context context;

    public VerticalStackTransformer(Context context) {
        this.context = context;
    }

    @Override
    protected void onTransform(View page, float position) {
        if (position <= -1) {
            page.setAlpha(0.0f);
        } else if (position <= 0.0f) {
            page.setAlpha(1.0f);
            page.setTranslationY(0f);
        } else if (position <= 1.0f) {
            float scale1 = (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, 30 * 2 + 30 * 2 * position)) / (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, 30 * 2));
            page.setAlpha(1.0f);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale1);
            page.setScaleY(scale1);
            float shiftY1 = (ScreenUtils.getScreenHeight(context) - ScreenUtils.dp2px(context, 92 + 32)) * 0.5f - (ScreenUtils.getScreenHeight(context) - ScreenUtils.dp2px(context, 92 + 32)) * scale1 * 0.5f + ScreenUtils.dp2px(context, 30) * position;
            page.setTranslationY(-page.getHeight() * position + shiftY1);
        } else if (position <= 2.0f) {
            float scale2 = (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, 30 * 2 + 30 * 2 * position)) / (float) (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, 30 * 2));
            page.setAlpha(1.0f);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale2);
            page.setScaleY(scale2);
            float shiftY2 = (ScreenUtils.getScreenHeight(context) - ScreenUtils.dp2px(context, 92 + 32)) * 0.5f - (ScreenUtils.getScreenHeight(context) - ScreenUtils.dp2px(context, 92 + 32)) * scale2 * 0.5f + ScreenUtils.dp2px(context, 30) * position;
            page.setTranslationY(-page.getHeight() * position + shiftY2);
        }
    }
}
