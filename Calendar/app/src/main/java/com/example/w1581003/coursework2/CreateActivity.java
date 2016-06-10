package com.example.w1581003.coursework2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.lang.StringBuilder;


public class CreateActivity extends Activity implements OnClickListener {
    private TextView event;
    private TextView description;
    private TextView time;
    private Button save;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newevent);

        event = (TextView)findViewById(R.id.e_title);
        description = (TextView)findViewById(R.id.e_detail);
        time = (TextView)findViewById(R.id.time);
        save = (Button)findViewById(R.id.saveCreate);
        cancel = (Button)findViewById(R.id.cancelCreate);


        save.setOnClickListener(this);
        cancel.setOnClickListener(this);





    }

    @Override
    public void onClick(View view){

        if(view.getId() == R.id.cancelCreate){
            setResult(10);
            finish();

        }

        else if(view.getId() == R.id.saveCreate){

            EditText tTitle = (EditText)findViewById(R.id.e_title);
            EditText tDesc = (EditText)findViewById(R.id.e_detail);
            EditText tTime = (EditText)findViewById(R.id.time);

            //Gets the text values from user inputs and combines them into one string
            //~ mark seperation for later
            String title = tTitle.getText().toString();
            String desc = tDesc.getText().toString();
            String time = tTime.getText().toString();
            String user = title+'~'+desc+'~'+time+'~';

            Intent userInfo = new Intent(this, MainActivity.class);
            userInfo.putExtra("newEvent", user);
            setResult(100, userInfo);
            finish();
        }


    }

}
