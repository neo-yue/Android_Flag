package com.example.flag;

import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.database.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DBActivity extends AppCompatActivity {
    TextView flagCountry;
    EditText comment;
    Spinner ratSpinner;
    RatingBar ratFlag;
    int flagId;
    Button submit;
    Button delete;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<Integer> ids=new ArrayList<>();
    ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbactivity);
        List<String> flags = Arrays.asList(getResources().getStringArray(R.array.countries_name));
        Bundle extras = getIntent().getExtras();



        flagCountry=findViewById(R.id.flagCountry);
        comment=findViewById(R.id.comment);
        ratFlag=findViewById(R.id.ratFlag);
        ratSpinner=findViewById(R.id.flagSpinner);

        submit=findViewById(R.id.subButton);
        delete=findViewById(R.id.delButton);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, names);






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

        db.open();
        Cursor check=db.getRate(1);
        if (!check.moveToFirst()){

        long createData;
        for (int i=0;i<flags.size();i++){
            createData= db.insertRate(flags.get(i),"",0);

        }
        }
        db.close();




        db.open();
        Cursor c = db.getAllRate();
        if(c.moveToFirst())

       {
           do{
           String name=c.getString(1);
           int id=c.getInt(0);
           names.add(name);
           ids.add(id);
           }while(c.moveToNext());
        }
        ratSpinner.setAdapter(adapter);
        db.close();

        System.out.println(ids);





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                boolean result=db.updateRate(flagId, flags.get(flagId),comment.getText().toString(),ratFlag.getRating());
                db.close();
                if(result){
                    Toast.makeText(getApplicationContext(),"Successful. ",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(),"Failure,All data need to be filled. ",Toast.LENGTH_LONG).show();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                boolean result=db.updateRate(flagId, flags.get(flagId),"",0);
                db.close();
                comment.setText("");
                ratFlag.setRating(0);

            }
        });


        ratSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            flagId=ratSpinner.getSelectedItemPosition()+1;

            db.open();
            Cursor  c= db.getRate(flagId);

        if(c.moveToFirst())

           // DisplayContact(c);
        {
            flagCountry.setText("Please rate the flag of "+c.getString(1));
            ratFlag.setRating(c.getFloat(2));
            comment.setText(c.getString(3));

        }

        else

            Toast.makeText(getApplicationContext(),"No contact found",Toast.LENGTH_LONG).show();


        db.close();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {


        }

    });



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


}//end class
