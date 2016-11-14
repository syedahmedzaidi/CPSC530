package com.example.ahmedzaidi73.randomnumbergenerator;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedWriter;
import android.util.Log;
import java.io.FileWriter;
import android.media.MediaScannerConnection;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    private int i = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    Handler h = new Handler();
    int delay = 1000; //1 seconds
    Runnable runnable;
    @Override
    protected void onStart() {
//start handler as activity become visible

        h.postDelayed(new Runnable() {
            public void run() {
                TextView myText = (TextView) findViewById(R.id.textView2);
                BufferedReader br2 = null;
                try {
                    br2 = new BufferedReader(new FileReader("/proc/sys/kernel/random/entropy_avail"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                SecureRandom secureRandomGenerator = null;
                try {
                    secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                byte[] randomBytes = new byte[17000000];
                secureRandomGenerator.nextBytes(randomBytes);
                FileNotFoundException e;
                IOException e2;
                Throwable th;
                String str = "";
                String sCurrentLine = "";
                BufferedReader br = null;
                while (true) {
                    try {
                        sCurrentLine = br2.readLine();
                        if (sCurrentLine == null) {
                            break;
                        }
                       /* while(Integer.parseInt(sCurrentLine) != 0){
                            Process su = Runtime.getRuntime().exec("cat /dev/random");
                            try {
                                BufferedReader br3 = new BufferedReader(new FileReader("/proc/sys/kernel/random/entropy_avail"));
                                sCurrentLine = br3.readLine();
                                System.out.println(sCurrentLine);
                            } catch (FileNotFoundException el) {
                                el.printStackTrace();
                            }
                        } */ //You need this for - entropy case
                        str = new StringBuilder(String.valueOf(str)).append(sCurrentLine).toString();
                        System.out.println(sCurrentLine);
                        myText.setText("CurrentEntropy: " + sCurrentLine);
                            if (Integer.parseInt(sCurrentLine) > 1000) {
                                System.out.println(sCurrentLine);
                                try {

                                    writeToFile(randomBytes, sCurrentLine);

                                    CharSequence text1 = "File Generated";
                                    int duration1 = Toast.LENGTH_LONG;

                                    Toast toast1 = Toast.makeText(getApplicationContext(), text1, duration1);
                                    toast1.show();

                                } catch (UnsupportedEncodingException el) {
                                    el.printStackTrace();
                                } catch (IOException elr) {

                                }
                            }

                    } catch (FileNotFoundException e3) {
                        e = e3;
                        br = br2;
                    } catch (IOException e4) {
                        e2 = e4;
                        br = br2;
                    } catch (Throwable th2) {
                        th = th2;
                        br = br2;
                    }
                }
                if (br2 != null) {
                    try {
                        br2.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                br = br2;

                runnable=this;

                h.postDelayed(runnable, delay);
            }
        }, delay);

        super.onStart();
    }

    public void writeToFile(byte[] toWrite, String entropy) throws IOException {
        i++;
        File traceFile = new File((this.getApplicationContext()).getExternalFilesDir(null), "TraceFile"+i+".txt");
        if (!traceFile.exists())
            try {
                traceFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        // Adds a line to the trace file
        //BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
        FileOutputStream writer = null;
        try {
            writer = new FileOutputStream(traceFile, true /* append*/);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.write(toWrite);
        writer.flush();
        writer.close();
    }

    @Override
    protected void onPause() {
        h.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void generate(View view) throws NoSuchProviderException, NoSuchAlgorithmException, IOException {

            }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}