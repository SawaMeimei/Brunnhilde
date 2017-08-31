package io.github.sawameimei.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huangmeng on 2017/8/6.
 */

public class DecoderImpl implements Decoder {

    @Override
    public Bitmap decoder(InputStream imageStream, ImageView imageView) {
        if (imageStream == null || imageView == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(imageStream, null, options);
        options.inSampleSize = calculateInSampleSize(options, imageView.getWidth(), imageView.getHeight());
        options.inJustDecodeBounds = false;
        //Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        try {
            imageStream = resetImageStream(imageStream);
            Bitmap result = BitmapFactory.decodeStream(imageStream, null, options);
            imageStream.close();
            return result;
        } catch (IOException e) {
            Log.e("error", "error", e);
        }
        /*try {
            byte[] data = readInputStream(imageStream);
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        } catch (IOException e) {
        }*/
        return null;
    }

    private InputStream resetImageStream(InputStream imageStream) {
       //if(imageStream.)
        return null;
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
