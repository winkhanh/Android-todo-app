package com.winkhanh.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    //views
    Button editButton;
    EditText editItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //match views
        setContentView(R.layout.activity_edit);
        editButton=findViewById(R.id.editButton);
        editItem=findViewById(R.id.editItem);
        //change Title
        getSupportActionBar().setTitle("Edit Item");
        //retrieve Extras
        final int pos = getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION);
        String item = getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT);
        editItem.setText(item);

        //add onClick handle
        editButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(MainActivity.KEY_ITEM_TEXT, editItem.getText().toString());
                i.putExtra(MainActivity.KEY_ITEM_POSITION,pos);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}