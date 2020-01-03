package com.water.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    public void saveDrawable(Drawable drawable, String filePath, Bitmap.CompressFormat format) {
        Bitmap bitmap = drawableToBitmap(drawable);
        saveBitmap(bitmap, filePath, format);
    }

    private void saveBitmap(Bitmap bitmap, String filePath, Bitmap.CompressFormat format) {
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        } else {
            f.getParentFile().mkdirs();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
            bitmap.compress(format, 90, out);
            out.flush();
        } catch (Exception e) {
            Logger.w(TAG, "bitmapToFile error", e);
        } finally {
            // TODO Auto-generated catch block
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Logger.w(TAG, "bitmapToFile: close cursor", e);
                }
            }
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        drawable.setVisible(true, false);

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();


        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;

        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
