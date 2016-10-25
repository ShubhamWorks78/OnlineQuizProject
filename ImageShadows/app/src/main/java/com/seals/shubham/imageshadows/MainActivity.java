package com.seals.shubham.imageshadows;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgBack = (ImageView)findViewById(R.id.imageView1);
        Log.d(TAG,"Creating the bitmap for profile Image");
        Bitmap imgBitmap = GraphicsHelper.getBitmapFromResource(getResources(),R.drawable.homeimage,imgBack.getLayoutParams().width,imgBack.getLayoutParams().height);

        ImageView img = (ImageView)findViewById(R.id.imageView2);
        Log.d(TAG,"Applying shadow effects in background");
        imgBack.setImageBitmap(GraphicsHelper.getShadow(imgBack.getLayoutParams().width,imgBack.getLayoutParams().height,30,img.getLayoutParams().width));
        Log.d(TAG,"Applying created bitmap to image");
        img.setImageBitmap(GraphicsHelper.getRoundedCornerBitmap(imgBitmap,30));
    }

}
