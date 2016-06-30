package com.apps.rmarathe.mytodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity implements OnItemSelectedListener {

    EditText edAddText;
    private DatePicker datePicker;
    private Spinner taskPrioritySpinner;
    private Spinner taskStatusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addItemsOnTaskPrioritySpinner();
        addItemsOnTaskStatusSpinner();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // add items into spinner dynamically
    public void addItemsOnTaskPrioritySpinner() {

        taskPrioritySpinner = (Spinner) findViewById(R.id.taskPrioritySpinner);
        List<String> list = new ArrayList<String>();
        list.add("Low");
        list.add("Medium");
        list.add("High");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskPrioritySpinner.setAdapter(dataAdapter);
    }
    public void addItemsOnTaskStatusSpinner() {

        taskStatusSpinner = (Spinner) findViewById(R.id.taskStatusSpinner);
        List<String> list = new ArrayList<String>();
        list.add("Yet To Start");
        list.add("Doing");
        list.add("Done");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskStatusSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void OnSaveAddItem(View view) {

        //create an item object and send it back to the list
        Task task = new Task();

        edAddText = (EditText) findViewById(R.id.edAddText);
        task.setTaskName(edAddText.getText().toString());

        datePicker = (DatePicker) findViewById(R.id.datePicker);

        taskPrioritySpinner = (Spinner) findViewById(R.id.taskPrioritySpinner);
        taskPrioritySpinner.setOnItemSelectedListener(this);
        task.setTaskPriority(taskPrioritySpinner.getSelectedItem().toString());

        taskStatusSpinner = (Spinner) findViewById(R.id.taskStatusSpinner);
        taskStatusSpinner.setOnItemSelectedListener(this);
        task.setTaskStatus(taskStatusSpinner.getSelectedItem().toString());

        Intent data = new Intent();
        data.putExtra("new_task",task);
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish();
    }

}
