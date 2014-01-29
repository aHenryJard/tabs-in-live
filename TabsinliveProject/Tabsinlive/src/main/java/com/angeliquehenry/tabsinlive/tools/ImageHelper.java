package com.angeliquehenry.tabsinlive.tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Usefull tools to manage bitmaps.
 */
public class ImageHelper {

    /**
     * Get a bitmap from an uri (uri from Select image intent for example)
     * @param uri
     * @param context
     * @return
     */
    public static Bitmap getBitmapFromURI(Uri uri, Activity context) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        return bitmap;
    }

    public static final int SELECT_PICTURE_INTENT = 1;

    /**
     * Launch image intent to pick the image on device.
     * @param activity
     */
    public static void callImagePickerIntent(Activity activity) {
        // in onCreate or any event where your want the user to
        // select a file
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE_INTENT);
    }

    /**
     * Shortcut to decode tab image from database data.
     * @param image sheet.image
     * @return
     */
    public static Bitmap getBitmapFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }

    public static Bitmap getImageFromSdCard(String filePath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 2;
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        return bm;
    }

    public static Bitmap ScaleBitmap(Bitmap bm, float scalingFactor) {
        int scaleHeight = (int) (bm.getHeight() * scalingFactor);
        int scaleWidth = (int) (bm.getWidth() * scalingFactor);

        return Bitmap.createScaledBitmap(bm, scaleWidth, scaleHeight, true);
    }
}
