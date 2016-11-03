package wiseowl.com.au.reaction;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioFormat;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import wiseowl.com.au.reaction.model.AudioChannel;
import wiseowl.com.au.reaction.model.AudioSampleRate;
import wiseowl.com.au.reaction.model.AudioSource;


public class Util {

    private static final Handler HANDLER = new Handler();

    private Util() {
    }

    public static void wait(int millis, Runnable callback){
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
        if(android.R.color.transparent == color) {
            return true;
        }
        int [] rgb = {Color.red(color), Color.green(color), Color.blue(color)};
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


    public static void requestPermission(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
        }
    }

}