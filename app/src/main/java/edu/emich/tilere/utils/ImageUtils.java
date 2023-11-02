package edu.emich.tilere.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utilities related to image handling
 */
public class ImageUtils {
    /**
     * Retrieves a bitmap from an Android.Net.Uri
     * @param context Application context
     * @param uri Uri of the image
     * @return Bitmap of the image
     * @throws IOException Failed to open, etc.
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri) throws IOException {
        ContentResolver resolver = context.getContentResolver();
        try (InputStream inStream = resolver.openInputStream(uri)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            options.inMutable = true;

            return BitmapFactory.decodeStream(inStream, null, options);
        }
    }
}
