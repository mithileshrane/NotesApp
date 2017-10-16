package com.example.samrans.noteapp.ui.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samrans.noteapp.R;
import com.example.samrans.noteapp.adapter.NotesAdapter;
import com.example.samrans.noteapp.models.Login;
import com.example.samrans.noteapp.models.Notes;
import com.example.samrans.noteapp.utils.ClickListen;
import com.example.samrans.noteapp.utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ClickListen {

    private static final int REQUEST_ADD_NOTE = 100;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rel_progress)
    RelativeLayout relProgress;
    @BindView(R.id.progress_view)
    ProgressBar progressView;
    @BindView(R.id.iv_no_data)
    ImageView ivNoData;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.no_data_found)
    RelativeLayout noDataFound;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.recylerViewNotes)
    RecyclerView recylerViewNotes;
    private SessionManager sessionManager;
    Context mContext;
    ArrayList<Notes> notesArrayList;
    NotesAdapter notesAdapter;
    private String[] mTestArray;
    ClickListen clickListen;
    boolean isSwitchView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mContext = this;
        sessionManager = new SessionManager(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        CircleImageView headerImage = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        TextView headerName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_person_name);
        TextView headerEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_email_id);
        Login login = sessionManager.getUser();
        if (login != null) {
            Picasso.with(mContext).load(login.getImageurl()).
                    error(R.drawable.ic_account_circle_white_48dp).
                    placeholder(R.drawable.ic_account_circle_white_48dp).
                    into(headerImage);
            headerEmail.setText(login.getEmailid());
            headerName.setText(login.getName());
        }
//        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DashActivity.this, ProfileActivity.class));
//            }
//        });
        initiz();

    }


    @Override
    protected void onStart() {
        super.onStart();
        clickListen = this;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
        if (id == R.id.action_view_change) {
            switchView();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {

        mTestArray = getResources().getStringArray(R.array.testArray);

        final ArrayAdapter<String> spinner_countries = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item,
                mTestArray);

        new AlertDialog.Builder(mContext)
                .setTitle("Select Category")
                .setAdapter(spinner_countries, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DashActivity.this, AddNoteActivity.class);
                        intent.putExtra("type", which);
                        startActivityForResult(intent, REQUEST_ADD_NOTE);
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_NOTE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    getData(data);
                }
            } else if (resultCode == RESULT_CANCELED) {
                if (data != null) {
                    getData(data);
                }
            }
        }
    }

    private void getData(Intent data) {
        Notes notes = new Notes();
        if (data.getStringExtra("details") != null) {
            if (!TextUtils.isEmpty(data.getStringExtra("details"))) {
                notes.setDetails(data.getStringExtra("details"));
                notes.setOneField(true);
            }
        } else {
            notes = null;
        }
        if (data.getStringExtra("header") != null) {
            if (!TextUtils.isEmpty(data.getStringExtra("header")))
                notes.setHeaderNote(data.getStringExtra("header"));
        }

        if (notes != null) {
            if (notes.isOneField()) {
                notesArrayList.add(0, notes);
                notesAdapter.notifyItemInserted(notesArrayList.size() - 1);
                noDataFound.setVisibility(View.GONE);
                relProgress.setVisibility(View.GONE);
                recylerViewNotes.setVisibility(View.VISIBLE);
            }
        }
    }

    public void switchView() {
        if (notesArrayList.size() > 0) {
            recylerViewNotes.setLayoutManager(isSwitchView ? new LinearLayoutManager(this) : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            isSwitchView = !isSwitchView;
            supportInvalidateOptionsMenu();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isSwitchView) {
            menu.getItem(R.id.action_view_change).setIcon(R.drawable.ic_view_headline_white_48dp);
        }
        return super.onPrepareOptionsMenu(menu);

    }

    private void initiz() {
        notesArrayList = new ArrayList<Notes>();
        if (notesArrayList.size() == 0) {
            recylerViewNotes.setVisibility(View.GONE);
            noDataFound.setVisibility(View.VISIBLE);
            relProgress.setVisibility(View.GONE);
        } else {
            recylerViewNotes.setVisibility(View.VISIBLE);
            noDataFound.setVisibility(View.GONE);
            relProgress.setVisibility(View.GONE);

        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recylerViewNotes.setLayoutManager(layoutManager);
        recylerViewNotes.setItemAnimator(new DefaultItemAnimator());
        recylerViewNotes.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        notesAdapter = new NotesAdapter(mContext, notesArrayList, clickListen);
        recylerViewNotes.setAdapter(notesAdapter);

    }

    @Override
    public void click(int position, Notes notes) {
        Toast.makeText(mContext, "Edit Mode", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DashActivity.this, AddNoteActivity.class);
        intent.putExtra("type", notes.getType());
        intent.putExtra("data", notes);
        startActivityForResult(intent, REQUEST_ADD_NOTE);

    }
}
