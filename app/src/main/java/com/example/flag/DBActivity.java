package com.example.flag;

import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.os.Bundle;
import android.widget.*;
import android.database.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;


public class DBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbactivity);

        Bundle extras = getIntent().getExtras();

        List<String> flags = Arrays.asList(getResources().getStringArray(R.array.countries_name));

        try{

            String destPath = "/data/data/" + getPackageName() +"/database/MyDB";

            File f = new File(destPath);

            if(!f.exists()){

                CopyDB(getBaseContext().getAssets().open("mydb"),

                        new FileOutputStream(destPath));

            }


        }catch (FileNotFoundException e){

            e.printStackTrace();

        }catch (IOException e){

            e.printStackTrace();

        }


        DBAdapter db = new DBAdapter(this);


//add a contact- CREATE

        db.open();
        long id;
        for (int i=0;i<flags.size();i++){
            id = db.insertRate(flags.get(i),"",10);

        }
        db.close();


//get all contacts - READ

        db.open();

        Cursor c = db.getAllRate();

        if(c.moveToFirst())

        {

            do{

                DisplayContact(c);

            }while(c.moveToNext());

        }

        db.close();


//get a single contact - READ

        db.open();

        c = db.getRate(2);

        if(c.moveToFirst())

            DisplayContact(c);

        else

            Toast.makeText(this,"No contact found",Toast.LENGTH_LONG).show();


        db.close();


//update a contact - UPDATE

        db.open();

        if(db.updateRate(1,"China","the best",10))

            Toast.makeText(this,"Update successful",Toast.LENGTH_LONG).show();

        else

            Toast.makeText(this,"Update failed",Toast.LENGTH_LONG).show();

        db.close();


//delete a contact - DELETE

        db.open();

        if(db.deleteRate(1))

            Toast.makeText(this,"Delete successful",Toast.LENGTH_LONG).show();

        else

            Toast.makeText(this,"Delete failed",Toast.LENGTH_LONG).show();


        db.close();


    }//end method onCreate


    public void CopyDB(InputStream inputStream,OutputStream outputStream)

            throws IOException{

//copy 1k bytes at a time

        byte[] buffer = new byte[1024];

        int length;

        while((length = inputStream.read(buffer)) > 0)

        {

            outputStream.write(buffer,0,length);

        }

        inputStream.close();

        outputStream.close();


    }//end method CopyDB


    public void DisplayContact(Cursor c)

    {

        Toast.makeText(this,

                "id: " + c.getString(0) + "\n" +

                        "Name: " + c.getString(1) + "\n" +
                        "Rate: " + c.getFloat(2) + "\n" +
                        "common: " + c.getString(3),

                Toast.LENGTH_LONG).show();

    }


}//end class
