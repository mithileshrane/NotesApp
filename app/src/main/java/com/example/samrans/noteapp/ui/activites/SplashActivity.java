package com.example.samrans.noteapp.ui.activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.samrans.noteapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.tv_welcome)
    TextView tvWelcome;
    @BindView(R.id.btngetStarted)
    Button btngetStarted;
    Context mContext;
    private Typeface font;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mContext = this;

        font = Typeface.createFromAsset(mContext.getAssets(), "fonts/opensas_semi_bold.ttf");
        tvWelcome.setTypeface(font);
        btngetStarted.setTypeface(font);
    }


    @OnClick(R.id.btngetStarted)
    public void onViewClicked() {
        startActivity(new Intent(SplashActivity.this,DashActivity.class));
        finish();
    }
}
