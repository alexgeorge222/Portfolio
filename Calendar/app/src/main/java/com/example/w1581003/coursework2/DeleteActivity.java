package com.example.w1581003.coursework2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;
import android.view.View;
import android.view.View.OnClickListener;

import java.lang.StringBuilder;

import android.content.ContentValues;

import static android.provider.BaseColumns._ID;
import static com.example.w1581003.coursework2.Events.TABLE_NAME;
import static com.example.w1581003.coursework2.Events.TIME;
import static com.example.w1581003.coursework2.Events.TITLE;
import static com.example.w1581003.coursework2.Events.DESC;
import static com.example.w1581003.coursework2.Events.DATE;

import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.database.Cursor;
import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

public class DeleteActivity extends Activity implements OnClickListener {

    private AlertDialog nDialog;
    private EData events;
    private Button confirm;
    private TextView listDisplay;
    private TextView uInput;
    private static String[] COLUMNS = {_ID, TIME, TITLE, DATE};
    private static String ORDER_BY = TIME + " DESC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleteevent);

        events = new EData(this);
        confirm =(Button)findViewById(R.id.delOk);
        listDisplay = (TextView)findViewById(R.id.itemList);
        uInput = (TextView)findViewById(R.id.uNum);
        confirm.setOnClickListener(this);
        SQLiteDatabase db = events.getReadableDatabase();
        Bundle key = getIntent().getExtras();
        String sDate = key.getString("date");
        String selection = "DATE = ?";
        String[] conditionArgs = {sDate};

        Cursor searcher = db.query(TABLE_NAME, COLUMNS, selection, conditionArgs, null, null, ORDER_BY);

        //Displays results of table from the provided day
        StringBuilder sb = new StringBuilder("EVENTS:\n");
        while (searcher.moveToNext()) {

            int id = searcher.getInt(0);
            String time = searcher.getString(1);
            String title = searcher.getString(2);

            sb.append(id + ". " + time + "  " + title + "\n");

        }
        String event = sb.toString();
        listDisplay.setText(event);


    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.delOk){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete this event?");
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    EditText uNumber = (EditText)findViewById(R.id.uNum);
                    String choice = uNumber.getText().toString();
                    deleteTarget(choice);
                }
            });
            nDialog = builder.show();
        }

    }

    private void deleteTarget (String choice){

        //deletes single ID based on provided choice
        SQLiteDatabase db = events.getReadableDatabase();
        String selection = "_ID = ?";
        String[] conditionArgs = {choice};
        db.delete(TABLE_NAME,selection, conditionArgs);
        finish();

    }


}
