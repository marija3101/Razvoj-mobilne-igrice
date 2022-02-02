package com.marijamandjelak.balloonpop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Balloons4 {
    Context context;
    Bitmap balloon;
    int ex, ey;
    int bVelocity;
    Random random;


    public Balloons4(Context context) {
        this.context = context;
        balloon = BitmapFactory.decodeResource(context.getResources(), R.drawable.b4);
        random = new Random();
        ex = 1300 + random.nextInt(2400);
        ey = 0;
        bVelocity = 15 + random.nextInt(18);
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
        ex = 1300 + random.nextInt(2400);
        ey = 0;
        bVelocity = 15 + random.nextInt(18);
    }
}


