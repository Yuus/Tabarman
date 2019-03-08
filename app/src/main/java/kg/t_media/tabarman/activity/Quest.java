package kg.t_media.tabarman.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kg.t_media.tabarman.R;
import kg.t_media.tabarman.adapters.CategoryAdapter;
import kg.t_media.tabarman.constants.AppConstants;
import kg.t_media.tabarman.listeners.ListItemClickListener;
import kg.t_media.tabarman.models.CategoryModel;
import kg.t_media.tabarman.utilites.ActivityUtilities;
import kg.t_media.tabarman.utilites.Apputilities;
import kg.t_media.tabarman.utilites.Datum;
import kg.t_media.tabarman.utilites.LoginResult;
import kg.t_media.tabarman.utilites.PlayerSettingsResult;
import kg.t_media.tabarman.utilites.QResult;
import kg.t_media.tabarman.utilites.QuestResult;
import kg.t_media.tabarman.utilites.TabarmanApi;
import kg.t_media.tabarman.utilites.TabarmanClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class Quest extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sPref;
    final String SESSION_ID = "SESSION_ID";
    final String PLAYER_ID = "PLAYER_ID";
    final String USERNAME = "USERNAME";
    final String SURNAME = "SURNAME";
    final String LANGID = "LANGID";
    final String COUNTRYID = "COUNTRYID";
    final String EMAIL = "EMAIL";

    private Activity activity;
    private Context context;

    String playerId;
    String sessionId;
    String userName;
    String surName;
    String fullName;
    String langId;
    String countryId;
    String email;

    private ArrayList<CategoryModel> categoryList;
//    private CategoryAdapter adapter = null;
    CategoryAdapter adapter = null;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.rvContent);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false));

        categoryList = new ArrayList<>();

        adapter = new CategoryAdapter(context, activity, categoryList);
        recyclerView.setAdapter(adapter);

        activity = Quest.this;
        context = getApplicationContext();

         FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TabarmanApi api = TabarmanClient.getTabarmanApi();
                final Call<QuestResult> questResult = api.questList(playerId, sessionId, 0);
                questResult.enqueue(new Callback<QuestResult>() {
                    @Override
                    public void onResponse(Call<QuestResult> call, Response<QuestResult> response) {
                        if (response.isSuccessful()) {

                            String status = response.body().getStatus();
                            String message = response.body().getMessage();
                            List<QuestResult.DataQ> questData = response.body().getDataq();

                            QuestResult.DataQ quest;
                            String categoryId;
                            String categoryName;
                            for (int i = 0; i < questData.size(); i++) {
                                quest = questData.get(i);
                                categoryId = quest.getQuestId();
                                categoryName = quest.getQuestName();
                               // if ( quest.getCountryId() == countryId) {
                                    categoryList.add(new CategoryModel(categoryId, categoryName));
                               // }
                            }
                        } else {
                        }
                    }
                    @Override
                    public void onFailure(Call<QuestResult> call, Throwable t) {
                    }
                });

                adapter.notifyDataSetChanged();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        View headerView = navigationView.getHeaderView(0);
        TextView navHeadName = (TextView) headerView.findViewById(R.id.navHeadName);
        navHeadName.setText(fullName);

        getPrefs();
        loadQuests();

        initListener();



    }



    @Override
    protected void onResume() {
        super.onResume();

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

    private void initListener() {

        // recycler list item click listener
        adapter.setItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

                CategoryModel model = categoryList.get(position);
                ActivityUtilities.getInstance().invokeCommonQuizActivity(activity, QuizPromptActivity.class, model.getCategoryId(), true);
            }
        });
    }

    private void loadQuests() {

        TabarmanApi api = TabarmanClient.getTabarmanApi();
        final Call<QuestResult> questResult = api.questList(playerId, sessionId, 0);

        questResult.enqueue(new Callback<QuestResult>() {
            @Override
            public void onResponse(Call<QuestResult> call, Response<QuestResult> response) {
                if (response.isSuccessful()) {

                    String status = response.body().getStatus();
                    String message = response.body().getMessage();
                    List<QuestResult.DataQ> questData = response.body().getDataq();

                    QuestResult.DataQ quest;
                    String categoryId;
                    String categoryName;
                    for (int i = 0; i < questData.size(); i++) {
                        quest = questData.get(i);
                        categoryId = quest.getQuestId();
                        categoryName = quest.getQuestName();
                        if ( quest.getCountryId() == countryId) {
                            categoryList.add(new CategoryModel(categoryId, categoryName));
                        }
                    }


                } else {

                }
            }
            @Override
            public void onFailure(Call<QuestResult> call, Throwable t) {

            }
        });

//        if (categoryList.isEmpty()) {
//            loadQuests();
//        }
        adapter.notifyDataSetChanged();
//        if (categoryList.size() == 0 ) {
//            ActivityUtilities.getInstance().invokeNewActivity(Quest.this, Login.class, true );
//        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_http) {
            Apputilities.httpLink(activity);
        } else if (id == R.id.nav_facebook) {
            Apputilities.facebookLink(activity);
        } else if (id == R.id.nav_youtube) {
            Apputilities.youtubeLink(activity);
        } else if (id == R.id.nav_share) {
            Apputilities.rateThisApp(activity);
        } else if (id == R.id.nav_about) {
            ActivityUtilities.getInstance().invokeNewActivity(activity, AboutDevActivity.class, false);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void getPrefs() {
        sPref = getSharedPreferences("usersettings", MODE_PRIVATE);
        sessionId = sPref.getString(SESSION_ID, "0");
        playerId = sPref.getString(PLAYER_ID, "0");
        userName = sPref.getString(USERNAME, "null");
        surName = sPref.getString(SURNAME, "null");
        langId = sPref.getString(LANGID, "0");
        countryId = sPref.getString(COUNTRYID, "0");
        email = sPref.getString(EMAIL, "");
        fullName = userName + " " + surName;
    }
}
