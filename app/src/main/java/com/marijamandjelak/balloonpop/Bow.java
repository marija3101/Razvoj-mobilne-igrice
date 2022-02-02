package com.marijamandjelak.balloonpop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Bow {
    Context context;
    Bitmap bow;
    int ox, oy;
    Random random;

    public Bow(Context context) {
        this.context = context;
        bow = BitmapFactory.decodeResource(context.getResources(), R.drawable.bow);
        random = new Random();
        ox = random.nextInt(BalloonPop.screenWidth);
        oy = BalloonPop.screenHeight - bow.getHeight();
    }

    public Bitmap getBow(){
        return bow;
    }

    int getBowWidth(){
        return bow.getWidth();
    }
}
