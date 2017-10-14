package com.example.samrans.noteapp.ui.activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.samrans.noteapp.R;
import com.example.samrans.noteapp.utils.SessionManager;

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
    SessionManager sessionManager;

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
        sessionManager=new SessionManager(this);

        font = Typeface.createFromAsset(mContext.getAssets(), "fonts/opensas_semi_bold.ttf");
        tvWelcome.setTypeface(font);
        btngetStarted.setTypeface(font);
        if (!sessionManager.getStatus())
            btngetStarted.setVisibility(View.VISIBLE);
        else
            btngetStarted.setVisibility(View.GONE);
    }


    @OnClick(R.id.btngetStarted)
    public void onViewClicked() {
        startActivity(new Intent(SplashActivity.this,DashActivity.class));
        sessionManager.setStatus(true);
        finish();
    }
}
