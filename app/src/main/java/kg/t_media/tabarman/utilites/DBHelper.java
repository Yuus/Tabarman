package kg.t_media.tabarman.utilites;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MLOG DB", "--- onCreate database ---");
        // создаем таблицу с полями для AppSettings
        // Таблица languages
        db.execSQL("create table languages ("
                + "id integer primary key,"
                + "name text" + ");");
        // Таблица countries
        db.execSQL("create table countries ("
                + "id integer primary key,"
                + "name text" + ");");
        // Таблица questCategories
        db.execSQL("create table questcats ("
                + "id integer primary key,"
                + "name text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
