package com.seals.shubham.imageshadows;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by shubham on 10/26/2016.
 */

public class GraphicsHelper {
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,int roundPx){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(color);

        canvas.drawRoundRect(rectF,roundPx,roundPx,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,rect,rect,paint);

        return output;
    }

    //Adding shadow
    public static Bitmap getShadow(int widthShadowLayout,int heightShadowLayout,int roundPx,int widthImage){
        final Rect rect = new Rect(0,0,widthShadowLayout,heightShadowLayout);

        Bitmap output = Bitmap.createBitmap(rect.width(),rect.height(),Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(output);

        rect.left += widthShadowLayout-widthImage;
        rect.top += widthShadowLayout-widthImage;
        rect.right -= widthShadowLayout-widthImage;
        rect.bottom -= widthShadowLayout-widthImage;
        final RectF rectF = new RectF(rect);
        Paint shadowPaint = new Paint();
        shadowPaint.setColor(Color.BLACK);
        shadowPaint.setShadowLayer((widthShadowLayout-widthImage)/2,5,5,0xFF555555);
        canvas.drawRoundRect(rectF,roundPx,roundPx,shadowPaint);
        return output;
    }

    public static Bitmap getBitmapFromResource(Resources resources, int resourceId,int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources,resourceId,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap image = BitmapFactory.decodeResource(resources,resourceId,options);
        return image;
    }
    private static int calculateInSampleSize(BitmapFactory.Options options,int regWidth,int regHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height>regHeight || width>regWidth){
            final int heightRatio = Math.round((float)height/(float)regHeight);
            final int widthRatio = Math.round((float)width/(float)height);
            inSampleSize = heightRatio<widthRatio?heightRatio:widthRatio;
        }
        return inSampleSize;
    }

}
