package com.example.cpsc530.entropyrate;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static android.R.attr.start;

public class MainActivity extends AppCompatActivity {

    @Override
    protected  void onStart() {

                TextView myText = (TextView) findViewById(R.id.textView3);
                BufferedReader br2 = null;
                String str = "";
                String print = "";
                String sCurrentLine = "";
                final long NANOSEC_PER_SEC = 1000l*1000*1000;
                try {
                    br2 = new BufferedReader(new FileReader("/proc/sys/kernel/random/entropy_avail"));
                    sCurrentLine = br2.readLine();
                    str = new StringBuilder(String.valueOf(str)).append(sCurrentLine).toString();
                    myText.setText(str);
                    Process su = Runtime.getRuntime().exec("cat /dev/random", null, null);
                    /*while (Integer.parseInt(sCurrentLine) != 0) {
                        //Process su = Runtime.getRuntime().exec("cat /dev/random", null, null);

                        BufferedReader br3 = new BufferedReader(new FileReader("/proc/sys/kernel/random/entropy_avail"));
                        sCurrentLine = br3.readLine();
                        System.out.println(sCurrentLine);

                    }*/
                    //1000 seconds = 1000000000000 nanoseconds
                    long timeItTook;
                    double rate;
                    //16*60*NANOSEC_PER_SEC
                    String header = "time (seconds) entropy (bits) random(bytes/nanoseconds)" ;
                    String seperator = "==============  ==============  ======================\n";
                    //writeToFile(header.getBytes());
                    //writeToFile(seperator.getBytes());
                    long startTime = System.nanoTime();
                    while (((timeItTook = (System.nanoTime()-startTime))< 1000000000000l)) {
                            //Process su = Runtime.getRuntime().exec("cat /dev/random", null, null);
                            timeItTook = (System.nanoTime()-startTime);
                            BufferedReader br3 = new BufferedReader(new FileReader("/proc/sys/kernel/random/entropy_avail"));
                            sCurrentLine = br3.readLine();
                            String slash = "/";
                            rate = (1.0/(double) timeItTook) * 1000000000;
                            print = '\n'+timeItTook+" "+sCurrentLine+" "+rate+'\n';
                            System.out.println(print);
                            //writeToFile(print.getBytes());
                            //System.out.println("Entropy: " + sCurrentLine);
                            //System.out.println("Time: " + timeItTook);
                    }
                    System.out.println("Done");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        super.onStart();
            }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void writeToFile(byte[] toWrite) throws IOException {
        File traceFile = new File((this.getApplicationContext()).getExternalFilesDir(null), "TraceFile.txt");
        if (!traceFile.exists()) {
            try {
                traceFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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


}
