package com.example.curd_operation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editText_Name, editText_Surname, editText_Marks, editText_Id;
    Button button1, button2, button3,button4;   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        editText_Id = (EditText) findViewById(R.id.editText_Id);
        editText_Name = (EditText) findViewById(R.id.editText_Name);
        editText_Surname = (EditText) findViewById(R.id.editText_Surname);
        editText_Marks = (EditText) findViewById(R.id.editText_Marks);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById((R.id.button3));
        button4 = (Button) findViewById(R.id.button4);

        AddData();
        ViewALL();
        UpdateData();
        DeleteData();

    }

    public void AddData() {
       button1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               boolean isInserted = myDB.insertData(editText_Name.getText().toString(), editText_Surname.getText().toString(), editText_Marks.getText().toString() );
               if (isInserted == true)
                   Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
               else
                   Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
               }
           }
       );
    }
    public void ViewALL(){
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDB.getAllData();
                        if(res.getCount() == 0){
                            showMessage("Error","No Data Found");
                            return;
                        }
                        StringBuffer buffer= new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("ID:"+res.getString(0)+"\n");
                            buffer.append("NAME:"+res.getString(1)+"\n");
                            buffer.append("SURNAME:"+res.getString(2)+"\n");
                            buffer.append("MARKS:"+res.getString(3)+"\n\n");
                        }
                        showMessage("Data",buffer.toString() );
                    }
                }
        );
    }

    public void UpdateData(){
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDB.updateData(editText_Id.getText().toString(),editText_Name.getText().toString(),editText_Surname.getText().toString(),editText_Marks.getText().toString());
                        if (isUpdated == true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void DeleteData(){
        button4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDB.DeleteData(editText_Id.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}