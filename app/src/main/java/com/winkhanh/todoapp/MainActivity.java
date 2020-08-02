package com.winkhanh.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //constrain definition
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 18;
    public static final int ADD_ITEM_CODE = 9;

    //variables
    List<String> items;

    //views
    RecyclerView itemsContainer;
    ItemsAdapter itemsAdapter;
    FloatingActionButton addFAB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //match views
        setContentView(R.layout.activity_main);
        itemsContainer=findViewById(R.id.itemsContainer);
        addFAB = findViewById(R.id.addFAB);
        //load item when starting
        loadItems();

        //Adapter def
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int pos) {
                items.remove(pos);
                itemsAdapter.notifyItemRemoved(pos);
                saveItems();
            }
        };
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int pos) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(KEY_ITEM_POSITION,pos);
                i.putExtra(KEY_ITEM_TEXT,items.get(pos));

                startActivityForResult(i,EDIT_TEXT_CODE);
            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        itemsContainer.setAdapter(itemsAdapter);
        itemsContainer.setLayoutManager(new LinearLayoutManager(this));

        addFAB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(i,ADD_ITEM_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE){
            String item = data.getStringExtra(KEY_ITEM_TEXT);
            int pos = data.getExtras().getInt(KEY_ITEM_POSITION);
            items.set(pos,item);
            itemsAdapter.notifyItemChanged(pos);
            saveItems();
            Toast.makeText(getApplicationContext(),"Item was editted", Toast.LENGTH_SHORT).show();
        }else if (resultCode == RESULT_OK && requestCode == ADD_ITEM_CODE){
            String item = data.getStringExtra((KEY_ITEM_TEXT));
            items.add(item);
            itemsAdapter.notifyItemInserted((items.size()-1));
            saveItems();
            Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
        } else{
            Log.w("MainActivity","Unknown call to onActivityResult");
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }
    private void loadItems(){
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(),Charset.defaultCharset()));
        }catch (IOException e){
            Log.e("MainActivity","Error reading items",e);
            items = new ArrayList<>();

        }
    }
    private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(),items);
        }catch (IOException e){
            Log.e("MainActivity","Error writing items",e);
        }
    }
}