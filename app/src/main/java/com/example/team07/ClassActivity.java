package com.example.team07;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashSet;

public class ClassActivity extends AppCompatActivity {

    static ArrayList<String> notes_title = new ArrayList<>();
    static ArrayList<String> notes_body = new ArrayList<>();
    static ArrayAdapter arrayAdapter, arrayAdapter2;

    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        ListView listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        HashSet<String> set2 = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {
            notes_title.add("Example note");
        } else {
            notes_title = new ArrayList(set);
        }

        if (set2 == null) {
            notes_body.add("Example note");
        } else {
            notes_body = new ArrayList(set2);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes_title);
        arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes_body);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemToDelete = i;
                // To delete the data from the App
                new AlertDialog.Builder(ClassActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes_title.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet(ClassActivity.notes_title);
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });

    }
}



    /*
    private void addNote(){

    }

    private void deleteNote(){

    }

    private void editNote(View view){
        Intent intent = new Intent(this, NotesActivity.class);
        String title = "Notes Title";
        intent.putExtra("Notes Title", title);
        startActivity(intent);
    }

    public void sortNotes(){

    }

    public String getClassTitle(){
        return classTitle.getText().toString();
    }

    public List searchClassNotes(){
        // Change <String> to whatever the list objects will be later
        ArrayList<String> searchList = new ArrayList<>();
        return searchList;
    }
     */
