package com.apps.rmarathe.mytodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

public class EditActivity extends AppCompatActivity {

    EditText edEditedItem;
    int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edEditedItem = (EditText) findViewById(R.id.edEditedItem);
        String itemText = getIntent().getStringExtra("item_text");
        itemPosition = getIntent().getIntExtra("item_position",0);
        edEditedItem.setText(itemText);

    }

    public void OnClickSave(View view) {

        edEditedItem = (EditText) findViewById(R.id.edEditedItem);
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("updated_text", edEditedItem.getText().toString());
        data.putExtra("item_pos", itemPosition); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
