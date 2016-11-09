package wiseowl.com.au.reaction.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cleveroad.audiovisualization.DbmHandler;
import com.cleveroad.audiovisualization.GLAudioVisualizationView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import omrecorder.AudioChunk;
import omrecorder.OmRecorder;
import omrecorder.PullTransport;
import omrecorder.Recorder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wiseowl.com.au.reaction.ApiClient;
import wiseowl.com.au.reaction.AudioRecorder.AndroidAudioRecorder;
import wiseowl.com.au.reaction.AudioRecorder.VisualizerHandler;
import wiseowl.com.au.reaction.CustomUi.ProgressDialogBar;
import wiseowl.com.au.reaction.R;
import wiseowl.com.au.reaction.Util;
import wiseowl.com.au.reaction.db.CandidateModel;
import wiseowl.com.au.reaction.db.GenericPresenter;
import wiseowl.com.au.reaction.model.AudioChannel;
import wiseowl.com.au.reaction.model.AudioSampleRate;
import wiseowl.com.au.reaction.model.AudioSource;
import wiseowl.com.au.reaction.model.PostModel;

import static wiseowl.com.au.reaction.R.id.restart;

public class AudioRecorderActivity extends AppCompatActivity implements PullTransport.OnAudioChunkPulledListener, MediaPlayer.OnCompletionListener {
    private String[] mStringQ;
    private String filePath;
    private AudioSource source;
    private AudioChannel channel;
    private AudioSampleRate sampleRate;
    private int color;
    private String name;
    private boolean autoStart;
    private boolean keepDisplayOn;
    private int pos = 0;

    CandidateModel model;

    private MediaPlayer player;
    private Recorder recorder;
    private VisualizerHandler visualizerHandler;

    private MenuItem saveMenuItem;
    private boolean isRecording;
    private GLAudioVisualizationView visualizerView;

    @BindView(R.id.overView)
    RelativeLayout overView;
    @BindView(R.id.content)
    RelativeLayout contentLayout;
    @BindView(R.id.tvQuestion)
    TextView tvQuestion;
    @BindView(R.id.status)
    TextView statusView;
    @BindView(R.id.tutorial)
    TextView tutorView;
    @BindView(restart)
    ImageButton restartView;
    @BindView(R.id.record)
    ImageButton recordView;
    @BindView(R.id.play)
    ImageButton playView;

    //Pop up layouts
    @BindView(R.id.rateLayout)
    RelativeLayout rateView;
    @BindView(R.id.progress)
    ProgressDialogBar dilatingDotsProgressBar;
    @BindView(R.id.mesgText)
    TextView msgTxt;
    @BindView(R.id.polText)
    TextView polTxt;
    @BindView(R.id.magText)
    TextView magTxt;
    @BindView(R.id.ratingText)
    LinearLayout ratingLayout;
    @BindView(R.id.uploading)
    RelativeLayout uploading;
    @BindView(R.id.doneRatingBtn)
    Button doneBtn;

    @OnClick(R.id.doneRatingBtn)
    void doneClick() {
        if (pos < (mStringQ.length - 1)) {
            fadeOutAndHideImage(overView, true, false); //fadeout rating animation
        } else {
            fadeOutAndHideImage(overView, true, true); //fadeout rating animation
        }

    }

    @OnClick(R.id.poor)
    void poor() {
        model.setRating("poor");
    }

    @OnClick(R.id.okay)
    void okay() {
        model.setRating("okay");
    }

    @OnClick(R.id.great)
    void great() {
        model.setRating("great");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aar_activity_audio_recorder);
        ButterKnife.bind(this);

        mStringQ = getResources().getStringArray(R.array.questions);//gets the string array from xml
        if (savedInstanceState != null) {
//            filePath = savedInstanceState.getString(AndroidAudioRecorder.EXTRA_FILE_PATH);
            source = (AudioSource) savedInstanceState.getSerializable(AndroidAudioRecorder.EXTRA_SOURCE);
            channel = (AudioChannel) savedInstanceState.getSerializable(AndroidAudioRecorder.EXTRA_CHANNEL);
            sampleRate = (AudioSampleRate) savedInstanceState.getSerializable(AndroidAudioRecorder.EXTRA_SAMPLE_RATE);
            color = savedInstanceState.getInt(AndroidAudioRecorder.EXTRA_COLOR);
            name = savedInstanceState.getString(AndroidAudioRecorder.EXTRA_NAME);
            autoStart = savedInstanceState.getBoolean(AndroidAudioRecorder.EXTRA_AUTO_START);
            keepDisplayOn = savedInstanceState.getBoolean(AndroidAudioRecorder.EXTRA_KEEP_DISPLAY_ON);
        } else {
//            filePath = getIntent().getStringExtra(AndroidAudioRecorder.EXTRA_FILE_PATH);
            source = (AudioSource) getIntent().getSerializableExtra(AndroidAudioRecorder.EXTRA_SOURCE);
            channel = (AudioChannel) getIntent().getSerializableExtra(AndroidAudioRecorder.EXTRA_CHANNEL);
            sampleRate = (AudioSampleRate) getIntent().getSerializableExtra(AndroidAudioRecorder.EXTRA_SAMPLE_RATE);
            color = getIntent().getIntExtra(AndroidAudioRecorder.EXTRA_COLOR, Color.BLACK);
            name = getIntent().getStringExtra(AndroidAudioRecorder.EXTRA_NAME);
            autoStart = getIntent().getBooleanExtra(AndroidAudioRecorder.EXTRA_AUTO_START, false);
            keepDisplayOn = getIntent().getBooleanExtra(AndroidAudioRecorder.EXTRA_KEEP_DISPLAY_ON, false);
        }


        filePath = Environment.getExternalStorageDirectory() + "/" + name + "Q" + pos + ".wav"; //set file path
        if (keepDisplayOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setBackgroundDrawable(
                    new ColorDrawable(Util.getDarkerColor(color)));
            getSupportActionBar().setHomeAsUpIndicator(
                    ContextCompat.getDrawable(this, R.drawable.aar_ic_clear));
        }

        visualizerView = new GLAudioVisualizationView.Builder(this)
                .setLayersCount(1)
                .setWavesCount(6)
                .setWavesHeight(R.dimen.aar_wave_height)
                .setWavesFooterHeight(R.dimen.aar_footer_height)
                .setBubblesPerLayer(20)
                .setBubblesSize(R.dimen.aar_bubble_size)
                .setBubblesRandomizeSize(true)
                .setBackgroundColor(Util.getDarkerColor(color))
                .setLayerColors(new int[]{color})
                .build();

        tvQuestion.setText(String.format(mStringQ[pos], name));

        contentLayout.setBackgroundColor(Util.getDarkerColor(color));
        contentLayout.addView(visualizerView, 0);
        restartView.setVisibility(View.INVISIBLE);
        playView.setVisibility(View.INVISIBLE);

        if (Util.isBrightColor(color)) {
            ContextCompat.getDrawable(this, R.drawable.aar_ic_clear)
                    .setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            ContextCompat.getDrawable(this, R.drawable.aar_ic_check)
                    .setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            statusView.setTextColor(Color.BLACK);
            tvQuestion.setTextColor(Color.BLACK);
            restartView.setColorFilter(Color.BLACK);
            recordView.setColorFilter(Color.BLACK);
            playView.setColorFilter(Color.BLACK);
        }

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (autoStart && !isRecording) {
            toggleRecording(null);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            visualizerView.onResume();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        restartRecording(null);
        try {
            visualizerView.onPause();
        } catch (Exception e) {
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        restartRecording(null);
        setResult(RESULT_CANCELED);
        try {
            visualizerView.release();
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(AndroidAudioRecorder.EXTRA_FILE_PATH, filePath);
        outState.putInt(AndroidAudioRecorder.EXTRA_COLOR, color);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aar_audio_recorder, menu);
        saveMenuItem = menu.findItem(R.id.action_save);
        saveMenuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.aar_ic_check));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();
        } else if (i == R.id.action_save) {
            if (pos < (mStringQ.length - 1)) {
                stopRecording();    //stop the recording
                setResult(RESULT_OK);

                //find old model;
                // model = new GenericPresenter().getSingle(CandidateModel.class,"filePath", filePath);
                model = new GenericPresenter().add(CandidateModel.newBuilder()
                        .name(name)
                        .filePath(filePath)
                        .build()); //save new path to Realm db
                getRating(Util.getFileFromPath(filePath, getApplicationContext()));
                fadeOutAndHideImage(overView, false, false); //fade in rating animation
                Log.i("ash", "path = " + model.getFilePath());

                nextQuestion();
            } else {
                //Complete stop of recording
                fadeOutAndHideImage(overView, false, false);


            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void nextQuestion() {

        pos++;              //increment question
        restart();          //restart recording

        tvQuestion.setText(String.format(mStringQ[pos], name)); //update new question
        filePath = Environment.getExternalStorageDirectory() + "/" + name + "Q" + pos + ".wav"; //set new file path

//        new GenericPresenter().addAsync(CandidateModel.newBuilder()
//                .name(name)
//                .filePath(filePath)
////                .magnitude(20)
////                .polarity(100)
////                .sentiment("I love this")
//                .build()); //save new path to Realm db
    }

    @Override
    public void onAudioChunkPulled(AudioChunk audioChunk) {
        float amplitude = isRecording ? (float) audioChunk.maxAmplitude() : 0f;
        visualizerHandler.onDataReceived(amplitude);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopPlaying();
    }

    public void toggleRecording(View v) {
        stopPlaying();
        Util.wait(100, new Runnable() {
            @Override
            public void run() {
                if (isRecording) {
                    pauseRecording();
                } else {
                    resumeRecording();
                }
            }
        });
    }

    public void togglePlaying(View v) {
        pauseRecording();
        Util.wait(100, new Runnable() {
            @Override
            public void run() {
                if (isPlaying()) {
                    stopPlaying();
                } else {
                    startPlaying();
                }
            }
        });
    }

    public void restartRecording(View v) {
        restart();

    }

    private void restart() {
        if (isRecording) {
            stopRecording();
        } else if (isPlaying()) {
            stopPlaying();
        } else {
            visualizerHandler = new VisualizerHandler();
            visualizerView.linkTo(visualizerHandler);
            visualizerView.release();
            if (visualizerHandler != null) {
                visualizerHandler.stop();
            }
        }
        saveMenuItem.setVisible(false);
        statusView.setVisibility(View.INVISIBLE);
        restartView.setVisibility(View.INVISIBLE);
        playView.setVisibility(View.INVISIBLE);
        recordView.setImageResource(R.drawable.aar_ic_rec);
    }

    private void resumeRecording() {
        isRecording = true;
        saveMenuItem.setVisible(false);
        statusView.setText(R.string.aar_recording);
        statusView.setVisibility(View.VISIBLE);
        restartView.setVisibility(View.INVISIBLE);
        playView.setVisibility(View.INVISIBLE);
        recordView.setImageResource(R.drawable.aar_ic_pause);
        playView.setImageResource(R.drawable.aar_ic_play);

        visualizerHandler = new VisualizerHandler();
        visualizerView.linkTo(visualizerHandler);

        if (recorder == null && source != null) {
            recorder = OmRecorder.wav(
                    new PullTransport.Default(Util.getMic(source, channel, sampleRate), AudioRecorderActivity.this),
                    new File(filePath));
        }
        if (recorder != null) {
            recorder.resumeRecording();
        }
    }

    private void fadeOutAndHideImage(final RelativeLayout view, final boolean fadeout, final boolean finish) {
        Animation fade;
        if (fadeout) {
            fade = new AlphaAnimation(1, 0);
        } else {
            fade = new AlphaAnimation(0, 1);
        }
        fade.setInterpolator(new AccelerateInterpolator());
        fade.setDuration(500);
        fade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (fadeout) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }

                if (finish) {
                    stopRecording();
                    setResult(RESULT_OK);
                    finish();
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                if (view.getVisibility() == View.GONE) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        });

        view.startAnimation(fade);
    }

    private void pauseRecording() {
        isRecording = false;
        if (!isFinishing()) {
            saveMenuItem.setVisible(true);
        }
        statusView.setText(R.string.aar_paused);
        statusView.setVisibility(View.VISIBLE);
        restartView.setVisibility(View.VISIBLE);
        playView.setVisibility(View.VISIBLE);
        recordView.setImageResource(R.drawable.aar_ic_rec);
        playView.setImageResource(R.drawable.aar_ic_play);

        visualizerView.release();
        if (visualizerHandler != null) {
            visualizerHandler.stop();
        }

        if (recorder != null) {
            recorder.pauseRecording();
        }
        tutorView.setText(getResources().getString(R.string.tutorPlay));
    }

    private void stopRecording() {
        visualizerView.release();
        if (visualizerHandler != null) {
            visualizerHandler.stop();
        }

        if (recorder != null) {
            recorder.stopRecording();
            recorder = null;
        }
    }

    private void startPlaying() {
        try {
            stopRecording();
            player = new MediaPlayer();
            player.setDataSource(filePath);
            player.prepare();
            player.start();

            visualizerView.linkTo(DbmHandler.Factory.newVisualizerHandler(this, player));
            visualizerView.post(new Runnable() {
                @Override
                public void run() {
                    player.setOnCompletionListener(AudioRecorderActivity.this);
                }
            });

            statusView.setText(R.string.aar_playing);
            statusView.setVisibility(View.VISIBLE);
            playView.setImageResource(R.drawable.aar_ic_stop);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopPlaying() {
        statusView.setText("");

        statusView.setVisibility(View.INVISIBLE);
        playView.setImageResource(R.drawable.aar_ic_play);

        visualizerView.release();
        if (visualizerHandler != null) {
            visualizerHandler.stop();
        }

        if (player != null) {
            try {
                player.stop();
                player.reset();
            } catch (Exception e) {
            }
        }
    }

    private boolean isPlaying() {
        try {
            return player != null && player.isPlaying() && !isRecording;
        } catch (Exception e) {
            return false;
        }
    }

    public void stopClick(View v){

    }

    private void uploadingVisablityShow(boolean show) {
        if(show) {
            ScaleAnimation fade_in =  new ScaleAnimation(1f, 1f, 1f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            fade_in.setDuration(700);     // animation duration in milliseconds
            fade_in.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.
            rateView.startAnimation(fade_in);


            dilatingDotsProgressBar.showNow();
            uploading.setVisibility(View.VISIBLE);
            ratingLayout.setVisibility(View.GONE);
            polTxt.setVisibility(View.GONE);
            magTxt.setVisibility(View.GONE);
            doneBtn.setVisibility(View.GONE);
        }else{
            ScaleAnimation fade_in =  new ScaleAnimation(1f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            fade_in.setDuration(700);     // animation duration in milliseconds
            fade_in.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.
            rateView.startAnimation(fade_in);


            dilatingDotsProgressBar.hideNow();
            uploading.setVisibility(View.GONE);
            ratingLayout.setVisibility(View.VISIBLE);
            polTxt.setVisibility(View.VISIBLE);
            magTxt.setVisibility(View.VISIBLE);
            doneBtn.setVisibility(View.VISIBLE);
        }


    }

    private void getRating(File file) {
        uploadingVisablityShow(true);

        //Get the call from the ApiClient
        Call<PostModel> call = ApiClient.uploadFile(file, this);

        //Get the response from the server
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (response.isSuccessful()) {
                    // tasks available
                    uploadingVisablityShow(false);

                    //set Text to overlay View
                    msgTxt.setText(response.body().getMessage());
                    polTxt.setText(String.format(getResources().getString(R.string.polarity), response.body().getPolarity()));
                    magTxt.setText(String.format(getResources().getString(R.string.magnitude), response.body().getMagnitude()));

                    //set Model to the Realm db
                    if (model != null) {
                        model.setMagnitude(response.body().getMagnitude());
                        model.setPolarity(response.body().getPolarity());
                        model.setSentiment(response.body().getMessage());
                        Log.i("ash", "setting model = " + model.getPolarity());
                    } else {
                        Log.i("ash", "failed to save to  model");

                    }

                } else {
                    uploading.setVisibility(View.GONE);
                    dilatingDotsProgressBar.hideNow();
                    magTxt.setText("Woops! Something went wrong");
                    msgTxt.setText("-");
                    polTxt.setText("-");
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                uploading.setVisibility(View.GONE);
                dilatingDotsProgressBar.hideNow();
                magTxt.setText("Woops! Something went wrong");
                msgTxt.setText("-");
                polTxt.setText("-");
            }
        });
    }

}
