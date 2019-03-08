package kg.t_media.tabarman.activity;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kg.t_media.tabarman.R;
import kg.t_media.tabarman.utilites.ActivityUtilities;
import kg.t_media.tabarman.utilites.AppSettings;
import kg.t_media.tabarman.utilites.DBHelper;
import kg.t_media.tabarman.utilites.TabarmanApi;
import kg.t_media.tabarman.utilites.TabarmanClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    private Animation animation;
    private ProgressBar progressBar;
    private ConstraintLayout layout;

    private static final int SPLASH_DURATION = 2500;

    DBHelper dbHelper;

    SharedPreferences sPref;
    final String SESSION_ID = "SESSION_ID";
    final String PLAYER_ID = "PLAYER_ID";
    final String USERNAME = "USERNAME";
    final String SURNAME = "SURNAME";
    final String LANGID = "LANGID";
    final String COUNTRYID = "COUNTRYID";
    final String EMAIL = "EMAIL";

    String playerId;
    String sessionId;
    String userName;
    String surName;
    String fullName;
    String langId;
    String countryId;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        layout = (ConstraintLayout) findViewById(R.id.splashLayout);
        imageView = (ImageView) findViewById(R.id.ivSplashIcon);
        animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        // Делаем запрос на получение AppSettings
        TabarmanApi api = TabarmanClient.getTabarmanApi();
        Call<AppSettings> appSettingsRes = api.appSettings();
        appSettingsRes.enqueue(new Callback<AppSettings>() {
            @Override
            public void onResponse(Call<AppSettings> call, Response<AppSettings> response) {
                if (response.isSuccessful()) {
                    // создаем объект для данных
                    ContentValues cv = new ContentValues();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ArrayList<AppSettings.Language> languages = response.body().getLanguages();
                    ArrayList<AppSettings.Country> countrys = response.body().getCountries();
                    ArrayList<AppSettings.QuestCategory> questcats = response.body().getQuestCategories();

                    if (!languages.isEmpty()) {
                        AppSettings.Language lang;
                        for(int i=0; i<languages.size();i++){
                            lang = languages.get(i);

                            cv.clear();
                            Log.d("MLOG DB", "--- Insert in languages: ---");
                            cv.put("id", lang.getId());
                            cv.put("name", lang.getName());
                            long rowID = db.insert("languages", null, cv);
                            Log.d("MLOG DB", "row inserted, ID = " + rowID);
                        }
                    }

                    if (!countrys.isEmpty()) {
                        AppSettings.Country con;
                        for(int i=0; i<countrys.size();i++){
                            con = countrys.get(i);

                            cv.clear();
                            Log.d("MLOG DB", "--- Insert in countries: ---");
                            cv.put("id", con.getId());
                            cv.put("name", con.getName());
                            long rowID = db.insert("countries", null, cv);
                            Log.d("MLOG DB", "row inserted, ID = " + rowID);
                        }
                    }

                    if (!questcats.isEmpty()) {
                        AppSettings.QuestCategory qc;
                        for(int i=0; i<questcats.size();i++){
                            qc = questcats.get(i);

                            cv.clear();
                            Log.d("MLOG DB", "--- Insert in questcats: ---");
                            cv.put("id", qc.getId());
                            cv.put("name", qc.getName());
                            long rowID = db.insert("questcats", null, cv);
                            Log.d("MLOG DB", "row inserted, ID = " + rowID);
                        }
                    }



                    String favData = response.body().toString();
                    Log.e("RES","response " + response.body());
                } else {
                    Snackbar.make(layout, "Ошибка", Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<AppSettings> call, Throwable t) {
                Snackbar.make(layout, "Ошибка2", Snackbar.LENGTH_LONG).show();
            }
        });

        getPrefs();





//        if (sessionId.equals("0")) {
            ActivityUtilities.getInstance().invokeNewActivity(SplashActivity.this, Login.class, true);
//        } else {
//            ActivityUtilities.getInstance().invokeNewActivity(SplashActivity.this, Quest.class, true);
//        }
    }

//    class DBHelper extends SQLiteOpenHelper {
//        public DBHelper(Context context) {
//            // конструктор суперкласса
//            super(context, "myDB", null, 1);
//        }
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            Log.d("MLOG DB", "--- onCreate database ---");
//            // создаем  таблицу с полями для AppSettings
//            // Таблица languages
//            db.execSQL("create table languages ("
//                    + "id integer primary key,"
//                    + "name text" + ");");
//            // Таблица countries
//            db.execSQL("create table countries ("
//                    + "id integer primary key,"
//                    + "name text" + ");");
//            // Таблица questCategories
//            db.execSQL("create table questcats ("
//                    + "id integer primary key,"
//                    + "name text" + ");");
//        }
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//        }
//    }

    private void initFunctionality() {
       layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                imageView.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        }, SPLASH_DURATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFunctionality();

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
