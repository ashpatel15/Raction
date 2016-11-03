package wiseowl.com.au.reaction.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wiseowl.com.au.reaction.AudioRecorder.AndroidAudioRecorder;
import wiseowl.com.au.reaction.R;
import wiseowl.com.au.reaction.Util;
import wiseowl.com.au.reaction.model.AudioChannel;
import wiseowl.com.au.reaction.model.AudioSampleRate;
import wiseowl.com.au.reaction.model.AudioSource;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSION_REQUEST_CODE = 1122;
    String filePath;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.topView)
    RelativeLayout topView;
    @BindView(R.id.nameEditText)
    EditText nameEditTxt;
//    @BindView(R.id.question_text)
//    TextView tvQuestion;

    @OnClick(R.id.next)
    void next() {

        if(nameEditTxt.getText() != null){
            String name = nameEditTxt.getText().toString();
            startRecoding(name);
        }else{
            startRecoding("No Name");
        }

//         /filePath = Environment.getExternalStorageDirectory() + "/" + name + "Q" + 0 + ".wav"; //set file path
//        Log.i("ash", filePath);

        Log.i("Anish", "has permissions");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.requestPermission(this, Manifest.permission.RECORD_AUDIO);
        Util.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Util.requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        Util.requestPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        topView.setBackgroundColor(Util.getDarkerColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)));

//        tvQuestion.setText(mStringQ[0]); //sets the string item
//        pos = 0;//sets first pos of questions

//        Log.i("ash",  String.format(mStringQ[0], "anish") );
//        if(model!= null){Log.i("anish", "sentiment - " +model.getSentiment());}


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Audio recorded successfully!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Audio was not recorded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startRecoding(String name) {

        int color = ContextCompat.getColor(this, R.color.colorPrimaryDark);
        int requestCode = 0;
        AndroidAudioRecorder.with(this)
                // Required
//                .setFilePath(filePath)
                .setColor(color)
                .setRequestCode(requestCode)

                // Optional
                .setSource(AudioSource.MIC)
                .setName(name)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
//                .setAutoStart(true)
                .setKeepDisplayOn(true)

                // Start recording
                .record();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
