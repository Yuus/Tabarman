package kg.t_media.tabarman.activity;

import androidx.appcompat.app.AppCompatActivity;
import kg.t_media.tabarman.R;
import kg.t_media.tabarman.utilites.ActivityUtilities;
import kg.t_media.tabarman.utilites.LoginData;
import kg.t_media.tabarman.utilites.LoginResult;
import kg.t_media.tabarman.utilites.PlayerSettingsResult;
import kg.t_media.tabarman.utilites.TabarmanApi;
import kg.t_media.tabarman.utilites.TabarmanClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout layout;

    SharedPreferences sPref;
    final String USERSETTINGS = "usersettings";
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
    String langId;
    String countryId;
    String email;

    Button btnLogin;
    TextView lnkReg;
    EditText txtName, txtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        txtName = (EditText) findViewById(R.id.txtEmail);
        txtPwd = (EditText) findViewById(R.id.txtPwd);

        layout = (LinearLayout) findViewById(R.id.loginLayout);

        lnkReg = (TextView) findViewById(R.id.lnkReg);
        lnkReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = txtName.getText().toString();
        String pwd = txtPwd.getText().toString();
        switch (v.getId()) {
            case R.id.btnLogin:

                LoginData logdata = new LoginData();
                logdata.setLogin(name);
                logdata.setPassword(pwd);

                TabarmanApi api = TabarmanClient.getTabarmanApi();
                final Call<LoginResult> logResult = api.Authorization(logdata);
                logResult.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                        if (response.isSuccessful()) {

                            Integer status = response.body().getStatus();
                            String message = response.body().getMessage();
                            playerId = response.body().getPlayerId();
                            sessionId = response.body().getSessionId();
                            if (status == 0) {
                                Snackbar.make(layout, "Ошибка:" + message, Snackbar.LENGTH_LONG).show();
                            } else if (status == 3) {
                                Snackbar.make(layout, "" + message, Snackbar.LENGTH_LONG).show();
                            } else  {
                                Snackbar.make(layout, "Авторизован:" + playerId, Snackbar.LENGTH_LONG).show();
                                saveSessionId();
                                savePlayerId();
                                TabarmanApi api2 = TabarmanClient.getTabarmanApi();
                                Call <PlayerSettingsResult> playerSettingsResult = api2.playerSettings(playerId, sessionId);
                                playerSettingsResult.enqueue(new Callback<PlayerSettingsResult>() {
                                    @Override
                                    public void onResponse(Call<PlayerSettingsResult> call, Response<PlayerSettingsResult> response2) {
                                        if (response2.isSuccessful()) {
                                            Integer status2 = response2.body().getStatus();
                                            String message2 = response2.body().getMessage();
                                            PlayerSettingsResult.Data playerSettingsData = response2.body().getData();

                                            userName = playerSettingsData.getUsername();
                                            surName = playerSettingsData.getSurname();
                                            langId = playerSettingsData.getLanguageId();
                                            countryId = playerSettingsData.getCountryId();
                                            email = playerSettingsData.getEmail();
                                            saveUserSettings();

                                            ActivityUtilities.getInstance().invokeNewActivity(Login.this, Quest.class, true );

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PlayerSettingsResult> call, Throwable t) {

                                    }
                                });


                            }
                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {

                    }
                });

            case R.id.lnkReg:

                ActivityUtilities.getInstance().invokeNewActivity(Login.this, Registration.class, true );
                break;
            default:
                break;
        }
    }

    void saveSessionId() {
        sPref = getSharedPreferences(USERSETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SESSION_ID, sessionId);
        ed.commit();
    }

    void savePlayerId() {
        sPref = getSharedPreferences(USERSETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(PLAYER_ID, playerId);
        ed.commit();
    }

    void saveUserSettings() {
        sPref = getSharedPreferences(USERSETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(USERNAME, userName);
        ed.putString(SURNAME, surName);
        ed.putString(LANGID, langId);
        ed.putString(COUNTRYID, countryId);
        ed.putString(EMAIL, email);
        ed.commit();
    }

}
