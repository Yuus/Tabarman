package kg.t_media.tabarman.activity;

import androidx.appcompat.app.AppCompatActivity;
import kg.t_media.tabarman.R;
import kg.t_media.tabarman.utilites.ActivityUtilities;
import kg.t_media.tabarman.utilites.DBHelper;
import kg.t_media.tabarman.utilites.RegistrationData;
import kg.t_media.tabarman.utilites.RegistrationResult;
import kg.t_media.tabarman.utilites.TabarmanApi;
import kg.t_media.tabarman.utilites.TabarmanClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layout;

    ArrayList<String> data = new ArrayList<String>();
    ArrayList<String> dataLang = new ArrayList<String>();
    DBHelper dbHelper;
    final String UCOUNTRY = "u_country";
    final String ULANGUAGE = "u_language";

    Button btnReg;
    EditText txtName, txtEmail, txtPwd;
    TextView lnkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btnReg = (Button) findViewById(R.id.btnReg);
        btnReg.setOnClickListener(this);
        lnkLogin = (TextView) findViewById(R.id.lnkLogin);
        lnkLogin.setOnClickListener(this);
        layout = (LinearLayout) findViewById(R.id.regLayout);

        txtName = (EditText) findViewById(R.id.txtName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPwd = (EditText) findViewById(R.id.txtPwd);


        dbHelper = new DBHelper(this);
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query("countries", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            do {
                data.add(c.getString(nameColIndex));
            } while (c.moveToNext());
        } else
            Log.d("MLOG DB", "0 rows");
        c.close();

        Cursor clang = db.query("languages", null, null, null, null, null, null);
        if (clang.moveToFirst()) {
            int idColIndex = clang.getColumnIndex("id");
            int nameColIndex = clang.getColumnIndex("name");
            do {
                dataLang.add(clang.getString(nameColIndex));
            } while (clang.moveToNext());
        } else
            Log.d("MLOG DB", "0 rows");
        clang.close();

        // Если значение страна и язык определено

        // адаптер для страны
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Title");
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // адаптер для языка
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataLang);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);
        spinner2.setPrompt("Title");
        spinner2.setSelection(2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }// End OnCreate

    @Override
    public void onClick(View v){

        String name = txtName.getText().toString();
        final String email = txtEmail.getText().toString();
        String pwd = txtPwd.getText().toString();


        switch (v.getId()) {
            case R.id.btnReg:

                RegistrationData regdata = new RegistrationData();

                String [] fio;
                fio = name.split(" ", 3);

                regdata.setCountryId(13);
                regdata.setLanguageId(4);
                regdata.setLogin(email);
                regdata.setEmail(email);
                regdata.setPassword(pwd);
                if (fio.length > 0) {
                    regdata.setName(fio[0]);
                }
                if (fio.length > 1) {
                    regdata.setSurname(fio[1]);
                } else {
                    regdata.setSurname(" ");
                }
                if (fio.length > 2) {
                    regdata.setPatronymic(fio[2]);
                } else {
                    regdata.setPatronymic("");
                }




                TabarmanApi api = TabarmanClient.getTabarmanApi();
                Call<RegistrationResult> regResult = api.playersAdd(regdata);
                regResult.enqueue(new Callback<RegistrationResult>() {
                    @Override
                    public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {
                        if (response.isSuccessful()) {

                            Integer status = response.body().getStatus();
                            String message = response.body().getMessage();

                            if (status == 1) {
                                Snackbar.make(layout, "Регистрация успешно завершено. Подтвердите почту " + email , Snackbar.LENGTH_LONG)
                                        .show();
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                ActivityUtilities.getInstance().invokeNewActivity(Registration.this, Login.class, true );
                            }
                            else {
                                Snackbar.make(layout, "Ошибка:" + message, Snackbar.LENGTH_LONG).show();
                            }
                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<RegistrationResult> call, Throwable t) {

                    }
                });


                break;
            case R.id.lnkLogin:

                ActivityUtilities.getInstance().invokeNewActivity(Registration.this, Login.class, true );

            default:
                break;
        }
    }
}
