package com.example.smartpillownew.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by a450J on 2018/8/15.
 */

public class ImageFlickerAnimation {

    public void ImageFlickerAnimation(ImageView view) {
        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(750);//闪烁时间间隔
        animation.setInterpolator(new LinearInterpolator());

        animation.setRepeatCount(Animation.INFINITE);

        animation.setRepeatMode(Animation.REVERSE);
        view.setAnimation(animation);

    }
}
