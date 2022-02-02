package com.marijamandjelak.balloonpop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Balloons3 {
    Context context;
    Bitmap balloon;
    int ex, ey;
    int bVelocity;
    Random random;


    public Balloons3(Context context) {
        this.context = context;
        balloon = BitmapFactory.decodeResource(context.getResources(), R.drawable.b3);
        random = new Random();
        ex = 1200 + random.nextInt(2300);
        ey = 0;
        bVelocity = 14 + random.nextInt(17);
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
        ex = 1200 + random.nextInt(2300);
        ey = 0;
        bVelocity = 14 + random.nextInt(17);
    }
}

