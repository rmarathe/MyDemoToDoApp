package com.apps.rmarathe.mytodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> toDoAdapter;
    ListView lvItems;
    EditText edAddText;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //rujuta
        checkIfFileExists();
        populateArray();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(toDoAdapter);
        edAddText = (EditText) findViewById(R.id.edAddText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                makeFeedbackToast(R.drawable.ic_menu_delete);
                todoItems.remove(position);
                toDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra("item_text",todoItems.get(position));
                i.putExtra("item_position", position);
                //startActivity(i);
                startActivityForResult(i, REQUEST_CODE);
            }

        });
    }

    private void makeFeedbackToast(int resId){
        Toast toast = new Toast(MainActivity.this);
        ImageView v = new ImageView(MainActivity.this);
        v.setImageResource(resId);
        toast.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(v);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("updated_text") && data.hasExtra("item_pos")) {
                String itemText = data.getStringExtra("updated_text");
                int index = data.getIntExtra("item_pos",0);
                todoItems.remove(index);
                todoItems.add(index,itemText);
                toDoAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }

    private void checkIfFileExists(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        if(!file.exists())
        {
           try{
               file.createNewFile();
           }catch(IOException e){}
        }
    }

    public void populateArray(){
        readItems();
        toDoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems);
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        //check if exists else create
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }catch(IOException e){
                System.out.println(e.getMessage());
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(file,todoItems);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnAddItem(View view) {
        toDoAdapter.add(edAddText.getText().toString());
        edAddText.setText("");
        makeFeedbackToast(R.drawable.ic_menu_add);
        writeItems();
    }
}
