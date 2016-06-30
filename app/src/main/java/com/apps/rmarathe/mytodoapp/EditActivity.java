package com.apps.rmarathe.mytodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edAddText;
    private DatePicker datePicker;
    private Spinner editTaskPrioritySpinner;
    private Spinner editTaskStatusSpinner;
    int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Task task = (Task) getIntent().getSerializableExtra("edit_task");
        edAddText = (EditText) findViewById(R.id.edAddText);
        edAddText.setText(task.getTaskName());
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        addItemsOnTaskPrioritySpinner();
        addItemsOnTaskStatusSpinner();

        editTaskPrioritySpinner.setSelection(task.getPrioritySelection());
        editTaskPrioritySpinner.setOnItemSelectedListener(this);
        editTaskStatusSpinner = (Spinner) findViewById(R.id.editTaskStatusSpinner);
        editTaskStatusSpinner.setSelection(task.getStatusSelection());

        itemPosition = getIntent().getIntExtra("item_position",0);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void OnClickSave(View view) {

        //create an item object and send it back to the list
        Task task = new Task();

        edAddText = (EditText) findViewById(R.id.edAddText);
        task.setTaskName(edAddText.getText().toString());

        datePicker = (DatePicker) findViewById(R.id.datePicker);

        editTaskPrioritySpinner = (Spinner) findViewById(R.id.editTaskPrioritySpinner);
        editTaskPrioritySpinner.setOnItemSelectedListener(this);
        task.setTaskPriority(editTaskPrioritySpinner.getSelectedItem().toString());

        editTaskStatusSpinner = (Spinner) findViewById(R.id.editTaskStatusSpinner);
        editTaskStatusSpinner.setOnItemSelectedListener(this);
        task.setTaskStatus(editTaskStatusSpinner.getSelectedItem().toString());

        Intent data = new Intent();
        data.putExtra("edited_task",task);
        data.putExtra("edited_position", itemPosition);
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish();
    }

    // add items into spinner dynamically
    public void addItemsOnTaskPrioritySpinner() {

        editTaskPrioritySpinner = (Spinner) findViewById(R.id.editTaskPrioritySpinner);
        List<String> list = new ArrayList<String>();
        list.add("Low");
        list.add("Medium");
        list.add("High");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTaskPrioritySpinner.setAdapter(dataAdapter);

    }
    public void addItemsOnTaskStatusSpinner() {

        editTaskStatusSpinner = (Spinner) findViewById(R.id.editTaskStatusSpinner);
        List<String> list = new ArrayList<String>();
        list.add("Yet To Start");
        list.add("Doing");
        list.add("Done");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTaskStatusSpinner.setAdapter(dataAdapter);
    }

}
