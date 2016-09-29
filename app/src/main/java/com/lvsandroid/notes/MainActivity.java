package com.lvsandroid.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    ListView notesListView;
    SharedPreferences sharedPreferences;
    public static ArrayAdapter notesAdapter;
    public static ArrayList<String> lstNotes = new ArrayList<>();
    public static Set<String> setNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void getSPData() {
        // SharedPreferences
        String packageName = getApplicationContext().getPackageName();
        sharedPreferences = this.getSharedPreferences(packageName, Context.MODE_PRIVATE);
        setNotes = sharedPreferences.getStringSet("notes",null);
    }

    private void init() {
        getSPData();

        lstNotes.clear();

        if(setNotes != null) {
            lstNotes.clear();
            lstNotes.addAll(setNotes);
        } else {
            lstNotes.add("Example note");
            setNotes = new HashSet<>();
            setNotes.addAll(lstNotes);
            sharedPreferences.edit().putStringSet("notes",setNotes).apply();
        }

        // Intent
        intent = new Intent(getApplicationContext(),EditNote.class);

        // List
        notesListView = (ListView)findViewById(R.id.lstNotes);

        notesAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lstNotes);
        notesListView.setAdapter(notesAdapter);

        // Events
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intent = new Intent(getApplicationContext(),EditNote.class);
                intent.putExtra("tappedNote",position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addNote) {
            editNote();
        }

        return super.onOptionsItemSelected(item);
    }

    private void editNote() {
        startActivity(intent);
    }
}
