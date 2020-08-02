package com.winkhanh.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    //views
    Button addButton;
    EditText addItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //match views
        setContentView(R.layout.activity_add);
        addButton = findViewById(R.id.addButton);
        addItem = findViewById(R.id.addItem);
        //clear text
        addItem.setText("");
        //change Title
        getSupportActionBar().setTitle("Add Item");
        //add onClick handle
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //send the item back to MainActivity
                Intent i = new Intent();
                i.putExtra(MainActivity.KEY_ITEM_TEXT,addItem.getText().toString());
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }
}