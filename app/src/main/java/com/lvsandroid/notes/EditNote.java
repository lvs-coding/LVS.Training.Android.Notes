package com.lvsandroid.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EditNote extends AppCompatActivity {

    Intent intent;
    int noteIdx = -1;
    EditText noteText;
    SharedPreferences sharedPreferences;
    static Set<String> setNotes;
    static ArrayList<String> lstNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        noteIdx = intent.getIntExtra("tappedNote",-1);
        if(noteIdx != -1) {
            noteText = (EditText)findViewById(R.id.editText);
            noteText.setText(MainActivity.lstNotes.get(noteIdx));
        }
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i("aaa", "onTextChanged " + s);
    }

    private void saveNote() {
        EditText noteText = (EditText)findViewById(R.id.editText);
        String note = noteText.getText().toString();

        if(note.trim() != "") {
            MainActivity.lstNotes.add(note);
            MainActivity.notesAdapter.notifyDataSetChanged();
            //intent.putExtra("notes", note);

            lstNotes.add(note);
            setNotes = new HashSet<>();
            setNotes.addAll(lstNotes);
            String packageName = getApplicationContext().getPackageName();
            sharedPreferences = this.getSharedPreferences(packageName, Context.MODE_PRIVATE);
            sharedPreferences.edit().putStringSet("notes",setNotes).apply();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveNote();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
