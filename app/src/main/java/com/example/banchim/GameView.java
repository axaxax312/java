package com.example.banchim;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;

import java.util.Random;
import java.util.logging.Handler;

public class GameView extends View  {
    int birdX, birdY;
    int touchX , touchY;
    int screenWidth ,screenHeight;
    int birdFrame = 0, birdDirectionX =1 , birdDirectionY =1 , birdSpeedX =20, birdSpeedY=20, deadBirdDropSpeed =3;
    int tempX = 0, tempY = 0;
    int points = 0;
    Handler handler;
    Runnable runnable;
    Random random;
    long UPDATE_MILLIS =30;
    Bitmap birdDead ,bg,bullet;
    Bitmap[] bird = new Bitmap[6];
    boolean birdAlive = true;
    Rect rect;
    int dist = 0;
    boolean resetState = true;
    int bulletsRemaining = 20;
    MediaPlayer mpPoints;
    Context context;


    public GameView(Context context) {
        super(context);
        this.context = context;
        bg = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        birdDead = BitmapFactory.decodeResource(getResources(),R.drawable.birddead);
        bullet = BitmapFactory.decodeResource(getResources(),R.drawable.bullet);
        bird[0] = BitmapFactory.decodeResource(getResources(),R.drawable.bird1);
        bird[1] = BitmapFactory.decodeResource(getResources(),R.drawable.bird2);
        bird[2] = BitmapFactory.decodeResource(getResources(),R.drawable.bird3);
        bird[3] = BitmapFactory.decodeResource(getResources(),R.drawable.bird4);
        bird[4] = BitmapFactory.decodeResource(getResources(),R.drawable.bird5);
        bird[5] = BitmapFactory.decodeResource(getResources(),R.drawable.bird6);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        handler = new Handler();
        random = new Random();
        birdX = random.nextInt(screenWidth);
        birdY = random.nextInt(screenHeight);
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        rect = new Rect(0,0,screenWidth , screenHeight);
        mpPoints = MediaPlayer.create(context,R.raw.points);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bg,null,rect, null);
        for(int i = 0; i< bulletsRemaining;i++){
            canvas.drawBitmap(bullet, i*bullet.getWidth(),10,null);
        }
        if(birdAlive){
            if ((resetState)){
                tempX = birdX;
                tempY = birdY;
                resetState = false;
            }

        }
        birdX = birdX + birdSpeedX * birdDirectionX;
        birdY = birdY + birdSpeedY * birdDirectionY;
        dist = 100 + random.nextInt(600);
        if(Math.abs(birdX - tempX) >= dist || Math.abs((birdY - tempY) >= dist)){
            if(birdDirectionX > 0){
                if(Math.random() <0.5)
                    birdDirectionX = -1;

            }else{
                if(Math.random() <0.5)
                    birdDirectionX = 1;
            }
            if(birdDirectionY > 0){
                if(Math.random() <0.5)
                    birdDirectionY = -1;

            }else{
                if(Math.random() <0.5)
                    birdDirectionY = 1;
            }
            resetBirdSpeed();
            resetState = true;
        }
        if(birdX >= screenWidth - bird[0].getWidth()){
            birdDirectionX = -1;
            resetBirdSpeed();
        }
        if(birdX <= 0){
            birdDirectionX = 1;
            resetBirdSpeed();
        }
        if(birdY <= 0){
            birdDirectionY = 1;
            resetBirdSpeed();
        }
        if(birdY >= screenHeight - bird[0].getHeight()){
            birdDirectionY = -1;
            resetBirdSpeed();
        }
        birdFrame++;
        if(birdFrame >5){
            birdFrame = 0;
        }
        canvas.drawBitmap(bird[birdFrame],birdX, birdY,null);
        handler.postDelayed(runnable,UPDATE_MILLIS);
    }

    private void resetBirdSpeed() {
        birdSpeedX = 10 +random.nextInt(20);
        birdSpeedY = 10 + random.nextInt(20);

    }
}
