package com.marijamandjelak.balloonpop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Balloons1 {
    Context context;
    Bitmap balloon;
    int ex, ey;
    int bVelocity;
    Random random;


    public Balloons1(Context context) {
        this.context = context;
        balloon = BitmapFactory.decodeResource(context.getResources(), R.drawable.b1);
        random = new Random();
        ex = 1000 + random.nextInt(2000);
        ey = 0;
        bVelocity = 12 + random.nextInt(15);
        resetPosition();

    }



    public Bitmap getBalloon(){
        return balloon;
    }

    int getBalloonWidth(){
        return balloon.getWidth();
    }

    int getBalloonHeight(){
        return balloon.getHeight();
    }

    public void resetPosition() {
        ex = 1000 + random.nextInt(2000);
        ey = 0;
        bVelocity = 12 + random.nextInt(15);
    }
}
