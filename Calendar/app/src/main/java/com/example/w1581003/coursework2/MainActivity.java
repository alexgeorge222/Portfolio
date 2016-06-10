package com.example.w1581003.coursework2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.CalendarView;
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


public class MainActivity extends Activity implements OnClickListener {

    private AlertDialog nDialog;
    private static String[] COLUMNS = {_ID, TIME, TITLE, DATE};
    private static String ORDER_BY = TIME + " DESC";
    private AlertDialog mDialog;
    private String uInfo;
    private CalendarView uCal;
    private int calYear = 0;
    private int calMonth = 0;
    private int calDay = 0;


    private Button butCreate;
    private Button butEdit;
    private Button butDel;
    private Button butSearch;
    private Button butMove;

    private EData events;
    static final int CREATE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeCal();

        events = new EData(this);
        butCreate = (Button) findViewById(R.id.createApp);
        butEdit = (Button) findViewById(R.id.viewEd);
        butDel = (Button) findViewById(R.id.delApp);
        butSearch = (Button) findViewById(R.id.searchApp);
        butMove = (Button) findViewById(R.id.moveApp);

        butCreate.setOnClickListener(this);
        butEdit.setOnClickListener(this);
        butDel.setOnClickListener(this);
        butSearch.setOnClickListener(this);
        butMove.setOnClickListener(this);


    }

    public void makeCal() {
        uCal = (CalendarView) findViewById(R.id.calendar);

        uCal.setOnDateChangeListener(new OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();
                calYear = year;
                calMonth = month + 1;
                calDay = day;
            }
        });
    }

    @Override
    public void onClick(View view) {

        //Launches CreateActivity
        if (view.getId() == R.id.createApp) {
            Intent intent = new Intent(this, CreateActivity.class);
            startActivityForResult(intent, CREATE_REQUEST);

        }
        //Gives display of events under a set day
        else if (view.getId() == R.id.viewEd) {
            Cursor cursor = getEvents();
            showEvents(cursor);

        }

        //goes through the delete tree of actions
        else if (view.getId() == R.id.delApp){
            String[] messages = {"Delete All Events", "Choose Events to Delete"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.delQ).setItems(messages, new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    deleteEvents(which);
                }
            });
            nDialog = builder.show();




        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == 100) {

            //return code from CreateActivity
            //The time, description, and title are removes through a for loop, using the ~ as seperators
            String info = data.getStringExtra("newEvent");

            if (requestCode == CREATE_REQUEST && info != null) {

                String title = new String();
                String desc = new String();
                String sTime = new String();
                int titleCheck = 0;
                int desCheck = 0;
                int dateCheck = 0;
                StringBuilder sb0 = new StringBuilder();
                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                for (int i = 0; i < info.length(); i++) {

                    if (info.charAt(i) == '~') {
                        if (titleCheck == 0) {

                            title = sb0.toString();
                            titleCheck = 1;

                        } else if (desCheck == 0) {

                            desc = sb1.toString();
                            desCheck = 1;

                        } else if (dateCheck == 0) {

                            sTime = sb2.toString();
                            dateCheck = 1;

                        }


                    } else {
                        if (titleCheck == 0) {

                            sb0.append(info.charAt(i));

                        } else if (desCheck == 0) {

                            sb1.append(info.charAt(i));

                        } else if (dateCheck == 0) {

                            sb2.append(info.charAt(i));

                        }
                    }
                }


                addEvent(title, desc, sTime, calDay, calMonth, calYear);


            }
        }


    }

    private void addEvent(String title, String desc, String time, int day, int month, int year) {

        //Adds the various values into the table
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        StringBuilder sb = new StringBuilder();
        sb.append(day + month + year);
        String date = sb.toString();
        values.put(TITLE, title);
        values.put(DESC, desc);
        values.put(TIME, time);
        values.put(DATE, date);
        db.insertOrThrow(TABLE_NAME, null, values);

    }

    private Cursor getEvents() {

        //collects the events for display or for deletion
        SQLiteDatabase db = events.getReadableDatabase();

        StringBuilder sb = new StringBuilder();
        sb.append(calDay + calMonth + calYear);
        String sDate = sb.toString();
        String selection = "DATE = ?";
        String[] conditionArgs = {sDate};

        Cursor searcher = db.query(TABLE_NAME, COLUMNS, selection, conditionArgs, null, null, ORDER_BY);
        startManagingCursor(searcher);
        return searcher;

    }

    private void showEvents(Cursor cursor) {

        //displays the events in the same way as the deletion activity
        StringBuilder sb = new StringBuilder("EVENTS:\n");
        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String time = cursor.getString(1);
            String title = cursor.getString(2);

            sb.append(id + ". " + time + "  " +  title + "\n");

        }
        String events = sb.toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(events);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        nDialog = builder.show();
    }
    private void deleteEvents(int choice){
        Cursor cursor = getEvents();
        StringBuilder sb = new StringBuilder();
        sb.append(calDay + calMonth + calYear);
        String sDate = sb.toString();

        //deletes all events that share a user selected day
       if (choice == 0){
            SQLiteDatabase db = events.getReadableDatabase();
            String selection = "DATE = ?";
            String[] conditionArgs = {sDate};
            db.delete(TABLE_NAME,selection, conditionArgs);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You have deleted all events for the day");
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            nDialog = builder.show();

        }
        else{

           //launches the deleteActivity activity, which recieves the date from the main page
           Intent delSwitch = new Intent(this, DeleteActivity.class);
           delSwitch.putExtra("date", sDate);
           this.startActivity(delSwitch);

        }
    }


}
