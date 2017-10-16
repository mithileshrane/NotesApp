package com.example.samrans.noteapp.ui.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.samrans.noteapp.R;
import com.example.samrans.noteapp.models.Notes;
import com.skydoves.colorpickerview.ColorPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddNoteActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_header)
    EditText edtHeader;
    @BindView(R.id.edt_note_detail)
    EditText edtNoteDetail;
    @BindView(R.id.ll_add_more)
    LinearLayout llAddMore;
    @BindView(R.id.rel_check_type)
    RelativeLayout relCheckType;
    @BindView(R.id.fabAddNote)
    FloatingActionButton fabAddNote;
    @BindView(R.id.rel_text_type)
    RelativeLayout relTextType;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ivbtn_color)
    ImageButton ivbtnColor;
    @BindView(R.id.cardColor)
    CardView cardColor;
    private Intent intent;
    boolean click = false;
    private int colorSelect = 0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Note");
        tvTitle.setText("Add Note");

        intent = getIntent();
        int type = intent.getIntExtra("type", -1);
        Notes notes = (Notes) intent.getSerializableExtra("data");
        switch (type) {
            case 0:
                relCheckType.setVisibility(View.GONE);
                relTextType.setVisibility(View.VISIBLE);
                if (notes != null) {
                    edtHeader.setText(notes.getHeaderNote());
                    edtNoteDetail.setText(notes.getDetails());
                }
                break;
            case 1:
                relCheckType.setVisibility(View.VISIBLE);
                relTextType.setVisibility(View.GONE);
                break;
            default:

        }


    }

    @OnClick({R.id.ll_add_more, R.id.fabAddNote, R.id.ivbtn_color})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_add_more:
                break;
            case R.id.ivbtn_color:
                showColorDialog();
                break;
            case R.id.fabAddNote:
                Intent intent = new Intent();
                intent.putExtra("header", edtHeader.getText().toString());
                intent.putExtra("details", edtNoteDetail.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);

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
//        if (id == R.id.action_color_note) {
//            showColorDialog(item);
//        }
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showColorDialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ColorPickerView colorPickerView = (ColorPickerView) dialoglayout.findViewById(R.id.colorPickerView);
        final TextView tv_hexColor = (TextView) dialoglayout.findViewById(R.id.tv_hexColor);
        final View view_color = dialoglayout.findViewById(R.id.view_color);


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                click = true;
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(dialoglayout);
        alertDialog.show();
        Button button=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setBackgroundColor(Color.LTGRAY);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (click) {
                    ivbtnColor.setBackgroundColor(colorSelect);
                    cardColor.setCardBackgroundColor(colorSelect);
                }
            }
        });
        colorPickerView.setColorListener(new ColorPickerView.ColorListener() {
            @Override
            public void onColorSelected(int color) {
                colorSelect = color;
                tv_hexColor.setText(String.valueOf(color));
                view_color.setBackgroundColor(color);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("header", edtHeader.getText().toString());
        intent.putExtra("details", edtNoteDetail.getText().toString());
        intent.putExtra("color", colorSelect);
        setResult(RESULT_OK, intent);
        finish();
    }


}
