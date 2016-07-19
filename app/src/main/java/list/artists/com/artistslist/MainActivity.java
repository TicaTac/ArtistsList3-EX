package list.artists.com.artistslist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SimpleCursorAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.new_artist_mi:
                    Toast.makeText(this,"New Artist",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,AddEditActivity.class);
                    startActivity(intent);
                break;

            case R.id.help_mi:
                    Toast.makeText(MainActivity.this,"Help",Toast.LENGTH_SHORT).show();


                   // finish();
                break;

        }



        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button addBtn= (Button) findViewById(R.id.addartistBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this, AddEditActivity.class);
                startActivity(intent);

            }
        });



        refreshList();

    }


    public  void  refreshList()
    {
        ListView lv= (ListView)findViewById(R.id.artistsLV);
        DbCommands commands= new DbCommands(this);
        final Cursor tempTableDataCursor =commands.getDataFromDBAsCursor();
        String[] from={DBConstants.artistName, DBConstants.artistYear };
        int[] to= { R.id.artistNameTV, R.id.artistYearTV };
        adapter= new SimpleCursorAdapter(this, R.layout.artist_item ,tempTableDataCursor, from, to );
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(tempTableDataCursor.moveToPosition(position))
                {
                  //  String name= tempTableDataCursor.getString(tempTableDataCursor.getColumnIndex(DBConstants.artistName));
                  //  int year=  tempTableDataCursor.getInt(tempTableDataCursor.getColumnIndex(DBConstants.artistYear));
                    int dbID= tempTableDataCursor.getInt(tempTableDataCursor.getColumnIndex("_id"));

                 //   Artist tempArtist= new Artist(name,year);
                //    tempArtist.sqlId=dbID;



                    Intent intent= new Intent(MainActivity.this, AddEditActivity.class);
                    intent.putExtra("id", dbID);
                    startActivity(intent);

                }



            }
        });





        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {



                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked

                                int dbID= tempTableDataCursor.getInt(tempTableDataCursor.getColumnIndex("_id"));

                                DbCommands commands1= new DbCommands(MainActivity.this);
                                commands1.deleteArtist(dbID);
                                refreshList();


                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to delete?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();



                return true;
            }
        });



        /*

        set a text change listener  and  update the list
         */

        ((EditText)findViewById(R.id.searchET)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String textToSearch= ((EditText)findViewById(R.id.searchET)).getText().toString();

                ListView lv= (ListView)findViewById(R.id.artistsLV);
                DbCommands commands= new DbCommands(MainActivity.this);
                Cursor tempTableDataCursor =commands.getDataFromDBAsCursor(textToSearch);
                String[] from={DBConstants.artistName, DBConstants.artistYear };
                int[] to= { R.id.artistNameTV, R.id.artistYearTV };
                adapter= new SimpleCursorAdapter(MainActivity.this, R.layout.artist_item ,tempTableDataCursor, from, to );
                lv.setAdapter(adapter);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        Button searchBtn= (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







            }
        });





    }


    @Override
    protected void onResume() {


        refreshList();

        super.onResume();
    }
}
