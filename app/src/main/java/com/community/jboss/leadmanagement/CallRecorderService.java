package com.community.jboss.leadmanagement;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.community.jboss.leadmanagement.main.contacts.editcontact.EditContactActivity;

import java.io.File;
import java.io.IOException;

public class CallRecorderService extends Service {

    private final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private final String AUDIO_RECORDER_FOLDER = "Lead Management Recordings";
    private String CHANNEL_ID = "LEAD_MANAGEMENT_ID";
    private int ID = 54321;
    private MediaRecorder recorder = null;

    AudioManager audioManager;


    @Nullable
    @Override
    public Binder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            audioManager.setSpeakerphoneOn(true);
        }

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilename());

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startRecording();
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (null != recorder) {
                recorder.stop();
                recorder.reset();
                recorder.release();

                recorder = null;
            }
        } catch (IllegalStateException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + AUDIO_RECORDER_FILE_EXT_MP4);
    }

    public void startRecording() {  // This functions updates the notification to show that Call recording is in process
        Context mContext = getApplicationContext();
        Intent contentIntent = new Intent(getApplicationContext(), EditContactActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(mContext, 0, contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_phone)
                .setContentTitle("Recording in Progress")
                .setTicker("Lead Management")
                .setContentIntent(contentPendingIntent)
                .setChannelId(CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); // Necessary to allow support for Android 8.0 and Higher
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (mNotificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        "This is a notification channel for displaying recording option.",
                        NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
            }

            mNotificationManager.notify(ID, notification.build());

        }
    }
}
