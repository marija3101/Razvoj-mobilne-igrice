package com.marijamandjelak.balloonpop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class BalloonPop extends View {
    Context context;
    Bitmap background;
    Handler handler;
    long UPDATE_MILLIS = 30;
    static int screenWidth, screenHeight;
    int points = 0;
    int life = 30;
    Paint scorePaint;
    Paint scorePaint1;
    int TEXT_SIZE = 80;
    boolean paused = false;
    Bow bow;
    Balloons1 balloon;
    Random random;
    ArrayList<Arrow>  arrow;
    ArrayList<Balloons1> balloons1;
    ArrayList<Balloons2> balloons2;
    ArrayList<Balloons3> balloons3;
    ArrayList<Balloons4> balloons4;
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    public BalloonPop(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        arrow = new ArrayList<>();
        balloons1 = new ArrayList<>();
        balloons2 = new ArrayList<>();
        balloons3 = new ArrayList<>();
        balloons4 = new ArrayList<>();
        for(int i=0; i<2; i++) {
            Balloons1 b1 = new Balloons1(context);
            balloons1.add(b1);
            Balloons2 b2 = new Balloons2(context);
            balloons2.add(b2);
            Balloons3 b3 = new Balloons3(context);
            balloons3.add(b3);
            Balloons4 b4 = new Balloons4(context);
            balloons4.add(b4);
        }
        bow = new Bow(context);
        balloon = new Balloons1(context);
        handler = new Handler();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);

        scorePaint = new Paint();
        scorePaint.setColor(Color.BLUE);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);

        scorePaint1 = new Paint();
        scorePaint1.setColor(Color.BLUE);
        scorePaint1.setTextSize(TEXT_SIZE);
        scorePaint1.setTextAlign(Paint.Align.RIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Dodati pozadinu, poene i živote na Canvas-u
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Pt: " + points, 50, TEXT_SIZE, scorePaint);

        canvas.drawText("Time: " + life, 2100, TEXT_SIZE, scorePaint1);
        //Kad život postane 0, zaustaviti igricu i pokrenuti GameOver Activity sa poenima
        if(life == 0){
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }

        //Ako balon udari u desni zid, preokrenuti brzinu balona
        if(balloon.ex + balloon.getBalloonWidth() >= screenWidth){
            balloon.bVelocity *= -1;
        }
        //Ako balon udari u levi zid, ponovo preokrenuti brzinu balona
        if(balloon.ex <=0){
            balloon.bVelocity *= -1;
        }



        // Dodati balone
        for(int i=0; i<balloons1.size(); i++) {
            canvas.drawBitmap(balloons1.get(i).getBalloon(), balloons1.get(i).ex, balloons1.get(i).ey, null);
            balloons1.get(i).ex-=balloons1.get(i).bVelocity;
            if(balloons1.get(i).ex < -balloons1.get(i).getBalloonWidth()) {
                balloons1.get(i).resetPosition();
                life--;
            }

            canvas.drawBitmap(balloons2.get(i).getBalloon(), balloons2.get(i).ex, balloons2.get(i).ey, null);
            balloons2.get(i).ex-=balloons2.get(i).bVelocity;
            if(balloons2.get(i).ex < -balloons2.get(i).getBalloonWidth()) {
                balloons2.get(i).resetPosition();
                life--;
            }

            canvas.drawBitmap(balloons3.get(i).getBalloon(), balloons3.get(i).ex, balloons3.get(i).ey, null);
            balloons3.get(i).ex-=balloons3.get(i).bVelocity;
            if(balloons3.get(i).ex < -balloons3.get(i).getBalloonWidth()) {
                balloons3.get(i).resetPosition();
                life--;
            }

            canvas.drawBitmap(balloons4.get(i).getBalloon(), balloons4.get(i).ex, balloons4.get(i).ey, null);
            balloons4.get(i).ex-=balloons4.get(i).bVelocity;
            if(balloons4.get(i).ex < -balloons4.get(i).getBalloonWidth()) {
                balloons4.get(i).resetPosition();
            }


        }
        //Dodati luk između levog i desnog ugla ekrana
        if(bow.ox > screenWidth - bow.getBowWidth()){
            bow.ox = screenWidth - bow.getBowWidth();
        }else if(bow.ox < 0){
            bow.ox = 0;
        }
        //Dodati luk
        canvas.drawBitmap(bow.getBow(), bow.ox, bow.oy, null);

        //Dodati strelu koja ide ka balonima. Ako se sudare strela i neki od balona
        //povećati poene, skloniti strelu i omogućiti da nestanu, a
        //ako strela promaši balon i ode do vrha ekrana samo skloniti strelu
        for(int j=0; j<balloons1.size(); j++) {
        for(int i=0; i < arrow.size(); i++){
            arrow.get(i).shy -= 15;
            canvas.drawBitmap(arrow.get(i).getArrow(), arrow.get(i).shx, arrow.get(i).shy, null);

                if ((arrow.get(i).shx >= balloons1.get(j).ex)
                        && arrow.get(i).shx <= balloons1.get(j).ex + balloons1.get(j).getBalloonWidth()
                        && arrow.get(i).shy <= balloons1.get(j).getBalloonWidth()
                        && arrow.get(i).shy >= balloons1.get(j).ey)

                {
                    points++;
                    arrow.remove(i);
                    balloons1.get(j).resetPosition();
                }
                else if((arrow.get(i).shx >= balloons2.get(j).ex)
                        && arrow.get(i).shx <= balloons2.get(j).ex + balloons2.get(j).getBalloonWidth()
                        && arrow.get(i).shy <= balloons2.get(j).getBalloonWidth()
                        && arrow.get(i).shy >= balloons2.get(j).ey){
                    points++;
                    arrow.remove(i);
                    balloons2.get(j).resetPosition();

                }
                else if((arrow.get(i).shx >= balloons3.get(j).ex)
                        && arrow.get(i).shx <= balloons3.get(j).ex + balloons3.get(j).getBalloonWidth()
                        && arrow.get(i).shy <= balloons3.get(j).getBalloonWidth()
                        && arrow.get(i).shy >= balloons3.get(j).ey){
                    points++;
                    arrow.remove(i);
                    balloons3.get(j).resetPosition();

                }
                else if((arrow.get(i).shx >= balloons4.get(j).ex)
                        && arrow.get(i).shx <= balloons4.get(j).ex + balloons4.get(j).getBalloonWidth()
                    && arrow.get(i).shy <= balloons4.get(j).getBalloonWidth()
                    && arrow.get(i).shy >= balloons4.get(j).ey){
                points++;
                    arrow.remove(i);
                balloons4.get(j).resetPosition();

            }
                else if (arrow.get(i).shy <= 0) {
                    arrow.remove(i);
                }
            }
        }


        //Ako nije pauzirano, pozvaćemo postDelayed() metodu nad handler objektom koja će
        //pokrenuti metodu Run unutar Runnable-a da se izvršava posle 30 milisekundi, to je vrednost
        //unutar UPDATE_MILLIS-a.
        if(!paused)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        //Kada je event.getAction() jednak MotionEvent.ACTION_UP, ako je veličina liste arrow
        //manja od nule, kreirati novu strelu. Ovime mi ograničavamo da se može ispaliti samo
        //jedna strela u trenutku na ekranu.
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(arrow.size() < 1){
                Arrow ourShot = new Arrow(context, bow.ox + bow.getBowWidth() / 2, bow.oy);
                arrow.add(ourShot);
            }
        }
        // Kad je event.getAction() jednak MotionEvent.ACTION_DOWN, kontroliši luk
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            bow.ox = touchX;
        }
        // Kada je event.getAction() jednak MotionEvent.ACTION_MOVE, kontroliši luk
        // sa pokretanjem miša
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            bow.ox = touchX;
        }
        //Vraćanje true je onTouchEvent() koji govori Android sistemu da
        //smo već iskontrolisali touch event i da nije potrebna dalja kontrola
        return true;
    }
}
