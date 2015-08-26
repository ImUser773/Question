package com.example.iamuser773.question;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Quiz extends AppCompatActivity {
    int score;
    String answer;
    Button nextquestion;
    RadioGroup radioGroup;
    RadioButton Radio_A, Radio_B, Radio_C, Radio_D;
    TextView textview;
    DataQuiz mdb;
    SQLiteDatabase db;
    Cursor mCursor;
    String getAnswer;
    int next = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //กำหนดid
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        Radio_A = (RadioButton) findViewById(R.id.a);
        Radio_B = (RadioButton) findViewById(R.id.b);
        Radio_C = (RadioButton) findViewById(R.id.c);
        Radio_D = (RadioButton) findViewById(R.id.d);
        nextquestion = (Button) findViewById(R.id.nextquestion);
        textview = (TextView) findViewById(R.id.text_question);
        mdb = new DataQuiz(this);
        db = mdb.getReadableDatabase();
        mCursor = db.rawQuery("SELECT * FROM " + DataQuiz.TABLE_NAME, null);
        mCursor.moveToFirst();
        checkQuestion();
        nextquestion.setText("ตอบ");
        nextquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.clearCheck();

                answer = mCursor.getString(mCursor.getColumnIndex(DataQuiz.COL_ANSWERS));
                if (answer.equals(getAnswer)) {

                    Snackbar.make(findViewById(android.R.id.content), "ถูกต้องนะครับ บ", Snackbar.LENGTH_LONG).show();


                    if (mCursor.getCount() > next) {
                        next++;
                        mCursor.moveToNext();
                        checkQuestion();
                        score++;
                        TextView score_text = (TextView) findViewById(R.id.text_score);
                        score_text.setText("คะเเนน = " + score);

                        }else {
                        Toast.makeText(getApplicationContext(),"หมดคำศัพท์เเล้ว เพิ่มคำศัพท์ใหม่สิ",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "ว้ายผิด!! ให้ตอบใหม่", Snackbar.LENGTH_LONG).show();
                }

            }


        });

    }

    private void checkQuestion() {

        if (mCursor.getCount() > 0) {
            textview.setText(mCursor.getString(mCursor.getColumnIndex(DataQuiz.COL_QUIZ)));
            Radio_A.setText(mCursor.getString(mCursor.getColumnIndex(DataQuiz.COL_A)));
            Radio_B.setText(mCursor.getString(mCursor.getColumnIndex(DataQuiz.COL_B)));
            Radio_C.setText(mCursor.getString(mCursor.getColumnIndex(DataQuiz.COL_C)));
            Radio_D.setText(mCursor.getString(mCursor.getColumnIndex(DataQuiz.COL_D)));
        } else {
            Snackbar.make(findViewById(android.R.id.content), "ยังไม่มีคำศัพท์", Snackbar.LENGTH_LONG).show();
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == Radio_A.getId()) {
                    getAnswer = Radio_A.getText().toString();


                } else if (checkedId == Radio_B.getId()) {
                    getAnswer = Radio_B.getText().toString();

                    ;

                } else if (checkedId == Radio_C.getId()) {
                    getAnswer = Radio_C.getText().toString();

                } else if (checkedId == Radio_D.getId()) {
                    getAnswer = Radio_D.getText().toString();


                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        //เพิ่มคำศัพท์
        if (id == R.id.add) {
            AlertDialog.Builder add = new AlertDialog.Builder(this);
            add.setTitle("เพิมคำศัพท์");
            LayoutInflater inflate = getLayoutInflater();
            final View view = inflate.inflate(R.layout.add_activity, null);
            add.setView(view);
            add.setNegativeButton("บันทึก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText questions = (EditText) view.findViewById(R.id.editText_question);
                    EditText a = (EditText) view.findViewById(R.id.editText_a);
                    EditText b = (EditText) view.findViewById(R.id.editText_b);
                    EditText c = (EditText) view.findViewById(R.id.editText_c);
                    EditText d = (EditText) view.findViewById(R.id.editText_d);
                    EditText answer = (EditText) view.findViewById(R.id.editText_answer);

                    String My_Question = questions.getText().toString();
                    String My_A = a.getText().toString();
                    String My_B = b.getText().toString();
                    String My_C = c.getText().toString();
                    String My_D = d.getText().toString();
                    String My_Answer = answer.getText().toString();
                    if (questions.length() != 0 && a.length() != 0 && b.length() != 0 && c.length() != 0 && d.length() != 0
                            && answer.length() != 0) {

                        db = mdb.getWritableDatabase();
                        mCursor = db.rawQuery("SELECT * FROM " + DataQuiz.TABLE_NAME + " WHERE "
                                + DataQuiz.COL_QUIZ + "='" + My_Question + "'"
                                + " AND " + DataQuiz.COL_ANSWERS + "='" + My_Answer + "'", null);
                        if (mCursor.getCount() == 0) {
                            db.execSQL("INSERT INTO " + DataQuiz.TABLE_NAME + " ("
                                    + DataQuiz.COL_QUIZ + ", " + DataQuiz.COL_A + ", "
                                    + DataQuiz.COL_B + ", " + DataQuiz.COL_C + ", "
                                    + DataQuiz.COL_D + ", " + DataQuiz.COL_ANSWERS + ") VALUES ('"
                                    + My_Question + "', '" + My_A + "', '" + My_B + "', '" + My_C + "', '"
                                    + My_D + "', '" + My_Answer + "');");
                            Snackbar.make(findViewById(android.R.id.content), "เพิ่มข้อมูลเเล้ว", Snackbar.LENGTH_LONG).show();
                        }
                    }


                }
            });
            add.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            add.show();


            return true;
        }
        if (id == R.id.view) {
            Intent i = new Intent(this, ViewDataQuiz.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.rf) {
            finish();
            Intent i = new Intent(this, Quiz.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mdb.close();
        db.close();

    }
}



