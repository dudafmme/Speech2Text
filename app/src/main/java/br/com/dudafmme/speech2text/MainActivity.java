package br.com.dudafmme.speech2text;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by Duda on 27/06/2016.
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView micImageView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        //mToolbar.setLogo();
        //mToolbar.setNavigationIcon();
        setSupportActionBar(mToolbar);

        micImageView = (ImageView)findViewById(R.id.mic_imv);
        micImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(micImageView, View.ALPHA, 0.2f, 1.0f).setDuration(1000).start();

            }
        });

    }
}
