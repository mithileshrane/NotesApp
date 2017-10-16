package com.example.samrans.noteapp.ui.activites;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.samrans.noteapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity {

    private ProgressBar mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressBar(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setVisibility(View.VISIBLE);
            if (mProgressDialog.isIndeterminate()) {
                mProgressDialog.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }

    }

    public void hideProgressDialog() {
        if (mProgressDialog != null ) {
            mProgressDialog.setVisibility(View.GONE);
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
