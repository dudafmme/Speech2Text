package br.com.dudafmme.speech2text;

import android.animation.ObjectAnimator;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Duda on 27/06/2016.
 */
public class MainActivity extends AppCompatActivity {

    private final int REQ_SPEECH = 100;
    RelativeLayout rlMain;
    private Toolbar mToolbar;
    private ImageView micImageView;
    private TextView speechTextView;
    private String txtSpeech;
    private File dir;
    private String nameDir;
    private String fileName;
    private String appDir;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlMain = (RelativeLayout) findViewById(R.id.rlMain);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);

        nameDir = getString(R.string.app_name);
        fileName = "NewSpeech.txt";
        appDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + nameDir + "/";
        dir = new File(appDir);
        dir.mkdirs();

        speechTextView = (TextView) findViewById(R.id.speechTextView);
        micImageView = (ImageView) findViewById(R.id.micImageView);
        micImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(micImageView, View.ALPHA, 0.2f, 1.0f).setDuration(1000).start();
                speech();
            }
        });
    }

    private void speech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech));
        try {
            startActivityForResult(intent, REQ_SPEECH);
        } catch (ActivityNotFoundException a) {
            Snackbar.make(rlMain, "Speech is not supported!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txtSpeech = result.get(0);
                    speechTextView.setText(txtSpeech);

                    final File file = new File(appDir, fileName);
                    file.getParentFile().mkdirs();
                    FileOutputStream fos = null;

                    try {
                        fos = new FileOutputStream(file);
                        fos.write(txtSpeech.getBytes());
                        fos.close();
                        Snackbar.make(rlMain, "File successfully stored!", Snackbar.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }
}
