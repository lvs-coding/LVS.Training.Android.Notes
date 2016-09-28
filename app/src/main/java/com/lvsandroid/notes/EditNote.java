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

import java.util.Map;
import java.util.Set;

public class EditNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void saveNote() {
        String packageName = getApplicationContext().getPackageName();
        EditText noteText = (EditText)findViewById(R.id.editText);
        String note = noteText.getText().toString();
        SharedPreferences spNote = this.getSharedPreferences(packageName, Context.MODE_PRIVATE);
        spNote.edit().putString("note",note).apply();

        Intent i = getIntent();
        i.putExtra("note",note);
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
