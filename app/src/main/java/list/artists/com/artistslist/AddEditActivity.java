package list.artists.com.artistslist;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddEditActivity extends AppCompatActivity {


    boolean isEditMode=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ImageView drumsIV = (ImageView) findViewById(R.id.drumsIV);
        drumsIV.

        final int artistID= getIntent().getIntExtra("id", -1);

        //we got an ID from mainactivity this is edit mode - load data from DB
        if(artistID != -1)
        {
            isEditMode=true;
            EditText artistNameET= (EditText) findViewById(R.id.artistnameET);
            EditText artistYear= (EditText) findViewById(R.id.artistYearET);
            DbCommands commands= new DbCommands(this);
            Cursor resultCursor= commands.getDataFromDBAsCursor(artistID);

            if(resultCursor.moveToNext())
            {
                String name= resultCursor.getString(resultCursor.getColumnIndex(DBConstants.artistName));
                int year=  resultCursor.getInt(resultCursor.getColumnIndex(DBConstants.artistYear));
                artistNameET.setText(name);
                artistYear.setText(""+year);

            }

        }





        ((Button) findViewById(R.id.saveBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText artistNameET = (EditText) findViewById(R.id.artistnameET);
                EditText artistYear = (EditText) findViewById(R.id.artistYearET);
                Artist artist = new Artist(artistNameET.getText().toString(), Integer.parseInt(artistYear.getText().toString()));
                DbCommands commands = new DbCommands(AddEditActivity.this);

                if(isEditMode)
                {
                    artist.sqlId=artistID;
                    commands.updateArtist(artist);
                    Toast.makeText(AddEditActivity.this, "Edited the Artist!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    //not edit mode add new artist....
                    commands.addArtist(artist);

                    Toast.makeText(AddEditActivity.this, "Added a new Artist!", Toast.LENGTH_SHORT).show();

                    finish();
                }

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (v.getId()){
            case R.id.drumsIV :
                    Toast.makeText(this,"Drums",Toast.LENGTH_SHORT).show();
                break;
        }


    }

    @Override

    @Override
    public boolean onContextItemSelected(MenuItem item) {




        return true;
//        return super.onContextItemSelected(item);
    }


}
