package wiseowl.com.au.reaction;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioFormat;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import wiseowl.com.au.reaction.model.AudioChannel;
import wiseowl.com.au.reaction.model.AudioSampleRate;
import wiseowl.com.au.reaction.model.AudioSource;


public class Util {

    private static final Handler HANDLER = new Handler();

    private Util() {
    }

    public static void wait(int millis, Runnable callback) {
        HANDLER.postDelayed(callback, millis);
    }

    public static omrecorder.AudioSource getMic(AudioSource source,
                                                AudioChannel channel,
                                                AudioSampleRate sampleRate) {
        return new omrecorder.AudioSource.Smart(
                source.getSource(),
                AudioFormat.ENCODING_PCM_16BIT,
                channel.getChannel(),
                sampleRate.getSampleRate());
    }

    public static boolean isBrightColor(int color) {
        if (android.R.color.transparent == color) {
            return true;
        }
        int[] rgb = {Color.red(color), Color.green(color), Color.blue(color)};
        int brightness = (int) Math.sqrt(
                rgb[0] * rgb[0] * 0.241 +
                        rgb[1] * rgb[1] * 0.691 +
                        rgb[2] * rgb[2] * 0.068);
        return brightness >= 200;
    }

    public static int getDarkerColor(int color) {
        float factor = 0.8f;
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(a,
                Math.max((int) (r * factor), 0),
                Math.max((int) (g * factor), 0),
                Math.max((int) (b * factor), 0));
    }


    public static void requestPermission(Activity activity, String[] permission) {
        for(String perm : permission){
            if (ContextCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, permission, 4);
            }
        }
    }

    public static File getFileFromPath(String path, Context context) {
        try {
            //File to be overwriten
            File file = new File(context.getExternalCacheDir() + File.separator + "audio.wav");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            //recorded file
            File fileInput = new File(path);
            FileInputStream fileInputStream = new FileInputStream(fileInput);
//            InputStream inputStream =  context.getResources().openRawResource(R.raw.test3);



            byte buf[] = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, len);
            }

            fileOutputStream.close();
            fileInputStream.close();

            String s = "file : " + file.length() + " - " + file.exists();
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

            if (file.exists())
                return file;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}