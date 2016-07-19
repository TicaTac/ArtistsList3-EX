package list.artists.com.artistslist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jbt on 18/07/2016.
 */
public class MySqlOpenHelper extends SQLiteOpenHelper {

    public MySqlOpenHelper(Context context) {
        super(context, DBConstants.databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String command="CREATE TABLE  "+DBConstants.tableName+" ( _id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                " "+ DBConstants.artistName +" TEXT," +
                " "+ DBConstants.artistYear+" INTEGER )";

        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

}
