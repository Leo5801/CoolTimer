package com.example.cooltimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SeekBar seekBar;
    private TextView textView;
    private boolean isTimerOn;
    private Button button;
    private CountDownTimer countDownTimer;
    private int defaultInterval;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        seekBar.setMax(600);
        isTimerOn = false;
        setIntervalFromSharedPrefernces(sharedPreferences);

        button = findViewById(R.id.button);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                long progressInMillis = i * 1000;
                updateTimer(progressInMillis);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    public void start(View view) {

        if (!isTimerOn) {
            button.setText("Stop");
            seekBar.setEnabled(false);
            isTimerOn = true;

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000,1000) {
                @Override
                public void onTick(long l) {

                    updateTimer(l);

                }

                @Override
                public void onFinish() {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    String melodyName = sharedPreferences.getString("timer_melody","bell");
                    if (melodyName.equals("bell")) {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.bell1);
                        mediaPlayer.start();
                    } else if (melodyName.equals("alarm_siren")) {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.alarm_siren);
                        mediaPlayer.start();
                    }else if (melodyName.equals("bip")) {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.bip);
                        mediaPlayer.start();
                    }





                    if (sharedPreferences.getBoolean("enable_sound",true)){
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.bell1);
                        mediaPlayer.start();

                    }
                    resetTimer();

                }
            };
            countDownTimer.start();
        } else {
            resetTimer();
        }


    }
    private void updateTimer(long l) {
        int minutes = (int) l/1000/60;
        int seconds = (int) l/1000 - (minutes * 60);

        String minutesString = "";
        String secondsString = "";

        if (minutes < 10){
            minutesString = "0" + minutes;
        }else{
            minutesString = String.valueOf(minutes);
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        }else {
            secondsString = String.valueOf(seconds);
        }
        textView.setText(minutesString + ":" + secondsString);
        // ne gap
        // ne gap 2
    }

    private void resetTimer(){
        countDownTimer.cancel();
        button.setText("Start");
        seekBar.setEnabled(true);
        isTimerOn = false;
        setIntervalFromSharedPrefernces(sharedPreferences);
    }
    //github
    //sds
    //dsad

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent openSettings = new Intent(this,SettingsActivity.class);
            startActivity(openSettings);
            return true;
        } else if (id == R.id.action_about) {
            Intent openAbout = new Intent(this,AboutActivity.class);
            startActivity(openAbout);
            return true;
        }

        return super.onOptionsItemSelected(item);
        // yena coment :)
    }

    private void setIntervalFromSharedPrefernces (SharedPreferences sharedPreferences) {
        try {
            defaultInterval = Integer.valueOf(sharedPreferences.getString("default_interval","30)"));

        } catch (NumberFormatException numberFormatException) {
            Toast.makeText(this, "Blaaa Son yoz axr", Toast.LENGTH_LONG).show();
        }catch ( Exception exception) {
            Toast.makeText(this, "Some error happens", Toast.LENGTH_LONG).show();
        }


        long defaultIntervalInMillis = defaultInterval * 1000;
        updateTimer(defaultIntervalInMillis);
        seekBar.setProgress(defaultInterval);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
         if (s.equals("default_interval")){
                setIntervalFromSharedPrefernces(sharedPreferences);
         }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
    // pitdi axr
}