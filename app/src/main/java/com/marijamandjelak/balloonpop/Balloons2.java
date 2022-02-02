package com.marijamandjelak.balloonpop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Balloons2 {
    Context context;
    Bitmap balloon;
    int ex, ey;
    int bVelocity;
    Random random;


    public Balloons2(Context context) {
        this.context = context;
        balloon = BitmapFactory.decodeResource(context.getResources(), R.drawable.b2);
        random = new Random();
        ex = 1100 + random.nextInt(2200);
        ey = 0;
        bVelocity = 13 + random.nextInt(16);
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
        ex = 1100 + random.nextInt(2200);
        ey = 0;
        bVelocity = 13 + random.nextInt(16);
    }
}
