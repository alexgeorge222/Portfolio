package com.example.w1581003.coursework1;

import java.lang.StringBuilder;
import java.util.Random;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
public class GameActivity extends Activity implements OnClickListener {

    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    private int hintActive = 0;
    private int tries = 0;
    private int uAnswer = 0;
    private int right = 0;
    private int lev = 0;
    private int round = 0;
    private int[] aInProg = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private int[] save = {0,0};
    private int aAnswer = 0;
    private Button but1;
    private Button but2;
    private Button but3;
    private Button but4;
    private Button but5;
    private Button but6;
    private Button but7;
    private Button but8;
    private Button but9;
    private Button but0;
    private Button butE;
    private Button butD;
    private Button butM;
    private Button butHint;
    private Button butMenu;
    private TextView question;
    private TextView answer;
    private TextView correct;
    private int min = 0;
    private int digit = 0;
    private int solved = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Random rand = new Random();


        question = (TextView)findViewById(R.id.question);
        answer = (TextView)findViewById(R.id.answer);
        correct = (TextView)findViewById(R.id.correct);

        but1 = (Button)findViewById(R.id.one);
        but2 = (Button)findViewById(R.id.two);
        but3 = (Button)findViewById(R.id.three);
        but4 = (Button)findViewById(R.id.four);
        but5 = (Button)findViewById(R.id.five);
        but6 = (Button)findViewById(R.id.six);
        but7 = (Button)findViewById(R.id.seven);
        but8 = (Button)findViewById(R.id.eight);
        but9 = (Button)findViewById(R.id.nine);
        but0 = (Button)findViewById(R.id.zero);
        butE = (Button)findViewById(R.id.ent);
        butD = (Button)findViewById(R.id.del);
        butM = (Button)findViewById(R.id.minus);
        butMenu = (Button)findViewById(R.id.menu);
        butHint = (Button)findViewById(R.id.hint);
        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
        but4.setOnClickListener(this);
        but5.setOnClickListener(this);
        but6.setOnClickListener(this);
        but7.setOnClickListener(this);
        but8.setOnClickListener(this);
        but9.setOnClickListener(this);
        but0.setOnClickListener(this);
        butE.setOnClickListener(this);
        butD.setOnClickListener(this);
        butM.setOnClickListener(this);
        butMenu.setOnClickListener(this);
        butHint.setOnClickListener(this);

        Bundle diff = getIntent().getExtras();
        lev = diff.getInt("difficulty");

        getMath();
    }

    private void getMath(){

        min = 0;
        digit = 0;
        int intArray[] = {-1,-1,-1,-1,-1,-1};
        int opArray[] = {-1,-1,-1,-1,-1, -1};
        String[] actOps = {"+", "-", "x", "/"};
        Random rand = new Random();
        int cap = 0;
        answer.setText("?");
        if (lev == 0){
            cap = 2;
        }
        else if (lev == 1){

            cap = rand.nextInt((3-2) + 1) + 2;
        }
        else if (lev == 2){

            cap = rand.nextInt((4-2) + 1) + 2;
        }
        else if (lev == 3){

            cap = rand.nextInt((6-4) + 1) + 4;
        }

        for(int i = 0; i < cap; i++){

            intArray[i] = rand.nextInt(1001);

            if(i != (cap-1)) {
                opArray[i] = rand.nextInt(4);
            }
        }

        int sum = 0;
        for(int i = 0; i< cap -1 ; i++){
            if(i == 0){
                if(opArray[i] == 0){

                    sum = sum + intArray[i] + intArray[i + 1];
                }
                else if(opArray[i] == 1){

                    sum = sum + (intArray[i] - intArray[i + 1]);
                }
                else if(opArray[i] == 2){

                    sum = sum + (intArray[i] * intArray[i + 1]);
                }
                else if(opArray[i] == 3){

                    sum = sum + (intArray[i] / intArray[i + 1]);
                }
            }

            else{
                if(opArray[i] == 0){

                sum = sum  + intArray[i + 1];
                }
                 else if(opArray[i] == 1){

                sum = sum  - intArray[i + 1];
                }
                else if(opArray[i] == 2){

                sum = sum  * intArray[i + 1];
                }
                else if(opArray[i] == 3){

                sum = sum  / intArray[i + 1];
                }
            }


            aAnswer = sum;
        }
        int i = 0;
        String uPrint = new String();
        while(opArray[i] != -1){
            uPrint = uPrint + " " + intArray[i] + " " + actOps[opArray[i]] + " ";
            i++;

        }
        uPrint = uPrint + intArray[cap - 1];
        question.setText(uPrint);

    }

    public int getAnswer(){

        return aAnswer;
    }
    @Override
    public void onClick(View view){

        if(view.getId() == R.id.ent){

            if (solved == 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < digit; i++) {
                    sb.append(aInProg[i]);
                }

                String text = sb.toString();

                uAnswer = Integer.parseInt(text);
                if (min == 1) {

                    uAnswer = uAnswer * -1;
                }

                if(hintActive == 0) {
                    winCheck(uAnswer, aAnswer);
                }

                else{

                    hintCheck(uAnswer, aAnswer);
                }
            }
            else{
                if(round < 10) {
                    solved = 0;
                    getMath();
                }

                else{

                    correct.setTextColor(getResources().getColor(R.color.green));
                    String correctomundo = String.valueOf(right) + " " + "Correct";
                    correct.setText(correctomundo);
                    round = 0;
                    right = 0;
                }

            }

        }

        else if (view.getId() == R.id.hint){

            if(hintActive == 0) {
                butHint.setTextColor(getResources().getColor(R.color.green));
                hintActive = 1;
            }
            else{

                butHint.setTextColor(getResources().getColor(R.color.black));
                hintActive = 0;
            }

        }

        else if(view.getId() == R.id.menu) {


            finish();


        }
        else if(view.getId() == R.id.del){

            if(digit > 0) {
                aInProg[digit - 1] = -1;
                digit = digit - 1;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < digit; i++) {
                    sb.append(aInProg[i]);
                }

                String text = sb.toString();
                if (min == 1) {

                    text = "-" + text;
                }
                answer.setText(text);
            }

        }
        else if(view.getId() == R.id.minus){

            if (digit == 0) {
                min = 1;
                answer.setText("-");
            }

        }

        else{

            digit++;
            int uNum = Integer.parseInt(view.getTag().toString());
            aInProg[digit - 1] = uNum;

            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < digit;  i++){
                    sb.append(aInProg[i]);
            }

            String text =  sb.toString();
            if(min == 1){

                text = "-" + text;
            }
            answer.setText(text);



        }
    }

    public void winCheck(int user, int computer){

        if(user == computer){

            correct.setTextColor(getResources().getColor(R.color.green));
            correct.setText("CORRECT");
            right ++;
        }
        else{
            correct.setTextColor(getResources().getColor(R.color.red));
            correct.setText("WRONG");
        }
        round ++;
        solved = 1;



    }

    public void hintCheck(int user, int computer){

        if(user == computer){

            correct.setTextColor(getResources().getColor(R.color.green));
            correct.setText("CORRECT");
            right ++;
            round ++;
            solved = 1;
            tries = 0;
        }
        else{
            correct.setTextColor(getResources().getColor(R.color.red));
            if(tries < 4){
                for(int i = 0; i < digit; i++){

                    aInProg[i] = -1;

                }
                digit = 0;
                if(user < computer){

                    correct.setText("HIGHER");
                }

                else{

                    correct.setText("LOWER");
                }

                tries++;
            }
            else{
                correct.setTextColor(getResources().getColor(R.color.red));
                correct.setText("WRONG");
                tries = 0;
                round ++;
                solved = 1;
            }
        }




    }

    public String saveGame(){

        StringBuilder sb = new StringBuilder();
        sb.append(round);
        sb.append(right);
        return sb.toString();

    }





}
