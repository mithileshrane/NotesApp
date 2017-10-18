package com.example.samrans.noteapp.ui.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.example.samrans.noteapp.R;
import com.example.samrans.noteapp.models.Notes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.skydoves.colorpickerview.ColorPickerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_date_edit)
    TextView tvDateEdit;
    private Intent intent;
    boolean click = false;
    private int colorSelect = 0;
    private String today;
    Calendar calendar;
    private Typeface font;
    private Context mContext;
    int type;
    Notes notes;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mStorageRef = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
    private DatabaseReference mChildUserRef = mStorageRef.child("user");
//    private DatabaseReference mNotesUserRef = mStorageRef.child("note/user");
//    private DatabaseReference mNoteRef = mStorageRef.child("note");


    private
    DateFormat df;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        mContext = this;
        font = Typeface.createFromAsset(mContext.getAssets(), "fonts/opensansbold.ttf");
        df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm a");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calendar = Calendar.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Note");
        tvTitle.setText("Add Note");
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
//        tvDate.setText(DateFormat.getDateTimeInstance().format(new Date()));
//        tvDateEdit.setText(DateFormat.getDateTimeInstance().format(new Date()));
        tvDate.setText(df.format(Calendar.getInstance().getTime()));
        tvDateEdit.setText(df.format(Calendar.getInstance().getTime()));
        intent = getIntent();
        type = intent.getIntExtra("type", -1);
        notes = (Notes) intent.getSerializableExtra("data");
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

        edtHeader.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtHeader.setTypeface(font);
            }
        });


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
//                Intent intent = new Intent();
//                intent.putExtra("header", edtHeader.getText().toString());
//                intent.putExtra("details", edtNoteDetail.getText().toString());
//                intent.putExtra("color", colorSelect);
//                intent.putExtra("date", df.format(Calendar.getInstance().getTime()));
                saveDatatoFirebase();
//                setResult(RESULT_OK, getIntentData());
//                finish();
                break;
        }
    }

    private void saveDatatoFirebase() {
        Notes notes = new Notes();
        notes.setHeaderNote(TextUtils.isEmpty(edtHeader.getText().toString()) ? null : edtHeader.getText().toString());
        notes.setDetails(TextUtils.isEmpty(edtNoteDetail.getText().toString()) ? null : edtNoteDetail.getText().toString());
        notes.setNoteColor(colorSelect);
        notes.setNoteDate(df.format(Calendar.getInstance().getTime()));
        if (notes.getHeaderNote() != null && notes.getDetails() != null) {
            notes.setOneField(true);
            mStorageRef.push().setValue(notes, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        if (databaseError.getCode() != DatabaseError.PERMISSION_DENIED) {
                            setResult(RESULT_OK, getIntentData());
                            Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(mContext, "Unauthenicated user,please login again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (databaseReference != null) {
                            setResult(RESULT_OK, getIntentData());
                            Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(mContext, "Something wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);

        if (notes != null)
            menu.findItem(R.id.action_check).setVisible(true);
        else
            menu.findItem(R.id.action_check).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_check) {
            if (!item.getTitle().equals("UnCheck")) {
                setSpannableText();
                item.setTitle("UnCheck");
            } else {
                setSpannableText();
                item.setTitle("Check");
            }
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

    private void setSpannableText() {
        edtHeader.setPaintFlags(edtHeader.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
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
        builder.setTitle("Note Color");
        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(dialoglayout);

        alertDialog.show();
        Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
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
                tv_hexColor.setText( String.format("#%06X", (0xFFFFFF & color)));
                view_color.setBackgroundColor(color);
            }
        });
    }

    @Override
    public void onBackPressed() {
        saveDatatoFirebase();
//        setResult(RESULT_OK, getIntentData());
//        finish();
    }

    private Intent getIntentData() {
        Intent intent = new Intent();
        Notes notes = new Notes();
        notes.setHeaderNote(TextUtils.isEmpty(edtHeader.getText().toString()) ? null : edtHeader.getText().toString());
        notes.setDetails(TextUtils.isEmpty(edtNoteDetail.getText().toString()) ? null : edtNoteDetail.getText().toString());
        notes.setNoteColor(colorSelect);
        notes.setNoteDate(df.format(Calendar.getInstance().getTime()));
        intent.putExtra("note", notes);
//
//        intent.putExtra("header", edtHeader.getText().toString());
//        intent.putExtra("details", edtNoteDetail.getText().toString());
//        intent.putExtra("color", colorSelect);
//        intent.putExtra("date", df.format(Calendar.getInstance().getTime()));
        return intent;
    }

    @OnClick(R.id.tv_date_edit)
    public void onViewClicked() {
    }
}
