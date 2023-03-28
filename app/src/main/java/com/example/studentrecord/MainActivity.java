package com.example.studentrecord;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper MyDB;
    EditText editTextID,editName,editEmail,editCC;
    Button buttonAdd,buttonGetData,buttonUpdate,buttonDelete,buttonViewAll;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDB = new DatabaseHelper(this);
        editTextID = findViewById(R.id.editTextID);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editCC = findViewById(R.id.editCC);
        buttonAdd = findViewById(R.id.button5);
        buttonDelete = findViewById(R.id.button2);
        buttonUpdate = findViewById(R.id.button3);
        buttonGetData = findViewById(R.id.button4);
        buttonViewAll = findViewById(R.id.button);

        AddData();
        getData();
        viewAll();
        updateData();
        viewAll();
        deleteData();
    }
    public void  AddData() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = MyDB.insertData(editName.getText().toString(),
                        editEmail.getText().toString(), editCC.getText().toString());
                if(isInserted == true) {
                    Toast.makeText(MainActivity.this, "DATA INSERTED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
        public void getData(){
            buttonGetData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ID = editTextID.getText().toString();
                    if(ID.equals(String.valueOf(""))){
                        editTextID.setError("Enter ID");
                       return;
                    }
                    Cursor cursor = MyDB.getData(ID);
                    String data = null;
                    if(cursor.moveToNext()){
                        data = "ID:"+cursor.getString(0)+"\n"+
                                "Name:"+cursor.getString(1)+"\n"+
                        "Email:"+cursor.getString(2)+"\n"+
                        "Course Count:"+cursor.getString(3)+"\n";
                    }
                    showMessage("DATA:",data);
                }
            });
        }

        public void viewAll(){
            buttonViewAll .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor cursor = MyDB.getAllData();

                    if (cursor.getCount() == 0){
                        showMessage("Error","Nothing is found in DB");
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while (cursor.moveToNext()){
                        buffer.append("ID:"+cursor.getString(0)+"\n");
                      buffer.append("Name:"+cursor.getString(1)+"\n");
                        buffer.append("Email:"+cursor.getString(2)+"\n");
                        buffer.append("CC:"+cursor.getString(3)+"\n\n");
                    }
                    showMessage("All data",buffer.toString());
                }
            });

        }
    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = MyDB.updateData(editTextID.toString(),
                        editName.getText().toString(),
                        editEmail.getText().toString(),
                        editCC.getText().toString());

                if (isUpdate == true){
                    Toast.makeText(MainActivity.this,"Updated successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"OOPSS!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRow = MyDB.deleteData(editTextID.getText().toString());
                if (deleteRow > 0){
                    Toast.makeText(MainActivity.this,"Delete Success",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"OOPSS!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}