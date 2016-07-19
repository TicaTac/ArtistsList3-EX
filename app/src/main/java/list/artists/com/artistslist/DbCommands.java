package list.artists.com.artistslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by jbt on 18/07/2016.
 */
public class DbCommands {

    Context context;
    MySqlOpenHelper helper;

    public DbCommands(Context c)
    {
        context=c;
        helper= new MySqlOpenHelper(context);
    }


    public  void addArtist(Artist artist)
    {
        ContentValues cv= new ContentValues();
        cv.put(DBConstants.artistName, artist.name);
        cv.put(DBConstants.artistYear, artist.year);
        helper.getWritableDatabase().insert(DBConstants.tableName, null,cv );
    }



    public void updateArtist(Artist artist)
    {
        ContentValues cv= new ContentValues();
        cv.put(DBConstants.artistName, artist.name);
        cv.put(DBConstants.artistYear, artist.year);

        //update artists set name="" , year =1985 where id=8
        //update contacts set name="yossi1" lastname="cohen1" where name="yosef" and lastname="levi"
        //update contacts set name="yossi1" lastname="cohen1" where name="?" and lastname="?"

        helper.getWritableDatabase().update(DBConstants.tableName,cv, "_id=?",  new String []{""+artist.sqlId } );

    }


    public Cursor getDataFromDBAsCursor()
    {
        Cursor tempTableDataCursor=   helper.getReadableDatabase().rawQuery("SELECT * FROM "+DBConstants.tableName, null);

        return  tempTableDataCursor;
    }



    public Cursor getDataFromDBAsCursor(String searchTerm)
    {
        Cursor tempTableDataCursor=   helper.getReadableDatabase().rawQuery("SELECT * FROM "+DBConstants.tableName+" WHERE "+DBConstants.artistName+" LIKE '%"+searchTerm+"%'" , null);

        return  tempTableDataCursor;
    }




    public Cursor getDataFromDBAsCursor(int artistID)
    {
        Cursor tempTableDataCursor=   helper.getReadableDatabase().rawQuery("SELECT * FROM "+DBConstants.tableName+" WHERE _id="+artistID , null);

        return  tempTableDataCursor;
    }


    public void deleteArtist(int dbID) {

        helper.getWritableDatabase().delete(DBConstants.tableName, "_id=?" , new String[]{""+dbID});


    }
}
