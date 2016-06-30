package com.apps.rmarathe.mytodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayList<Task> completeTodoItems;
    ArrayAdapter<Task> toDoAdapter;
    ListView lvItems;
    EditText edAddText;

    private final int REQUEST_CODE_EDIT = 20;
    private final int REQUEST_CODE_ADD = 30;

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
                completeTodoItems.remove(position);
                toDoAdapter.notifyDataSetChanged();
                serialWriteItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra("edit_task",completeTodoItems.get(position));
                i.putExtra("item_position", position);
                startActivityForResult(i, REQUEST_CODE_EDIT);
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
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD) {
            if (data.hasExtra("new_task")) {
                Task task = (Task) data.getSerializableExtra("new_task");
                completeTodoItems.add(task);
                toDoAdapter.notifyDataSetChanged();
                serialWriteItems();
            }
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT) {
            if (data.hasExtra("edited_task") && data.hasExtra("edited_position")) {
                Task task = (Task) data.getSerializableExtra("edited_task");
                int index = data.getIntExtra("edited_position",0);
                completeTodoItems.remove(index);
                completeTodoItems.add(index,task);
                toDoAdapter.notifyDataSetChanged();
                serialWriteItems();
            }
        }

    }

    private void checkIfFileExists(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"taskstodo.txt");
        if(!file.exists())
        {
           try{
               file.createNewFile();
           }catch(IOException e){}
        }
    }

    public void populateArray(){

        serialReadItems();
        //ArrayAdapter with Custom Object
        toDoAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_2, android.R.id.text1, completeTodoItems){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
               View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(completeTodoItems.get(position).getTaskName());
                text2.setText(completeTodoItems.get(position).getTaskPriority());
                text2.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

                text1.setTextColor(completeTodoItems.get(position).getColorByStatus());
                text2.setTextColor(completeTodoItems.get(position).getColorByStatus());

               /* if(completeTodoItems.get(position).isTaskStatusDone(completeTodoItems.get(position).getTaskStatus())){
                    text1.setPaintFlags(text1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }*/
                return view;
            }
        };
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
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

    // Creates an object by reading it from a file
    public void serialReadItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir,"taskstodo.txt");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            completeTodoItems = new ArrayList<Task>();
            completeTodoItems = (ArrayList<Task>)ois.readObject();

            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void serialWriteItems()
    {
        File filesDir = getFilesDir();
        File file = new File(filesDir,"taskstodo.txt");
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(completeTodoItems);
            oos.close();
        }catch(IOException e){
            e.printStackTrace();
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
        Intent i = new Intent(MainActivity.this, AddActivity.class);
        startActivityForResult(i, REQUEST_CODE_ADD);

    }
}
