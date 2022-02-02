package com.marijamandjelak.balloonpop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Arrow {
    Bitmap arrow;
    Context context;
    int shx, shy;

    public Arrow(Context context, int shx, int shy) {
        this.context = context;
        arrow = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.arrow);
        this.shx = shx;
        this.shy = shy;
    }
    public Bitmap getArrow(){
        return arrow;
    }
    public int getArrowWidth() {
        return arrow.getWidth();
    }
    public int getArrowHeight() {
        return arrow.getHeight();
    }
}
