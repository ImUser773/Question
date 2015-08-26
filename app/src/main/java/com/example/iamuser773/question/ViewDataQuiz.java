package com.example.iamuser773.question;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by IamUser773 on 21/8/2558.
 */
public class ViewDataQuiz extends AppCompatActivity {
    DataQuiz dq;
    SQLiteDatabase db;
    Cursor mcusor;
    ListView listView;
    int myposition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        listView = (ListView) findViewById(R.id.listView);




    }

    @Override
    protected void onResume() {
        super.onResume();
        dq = new DataQuiz(this);
        db = dq.getReadableDatabase();
        mcusor = db.rawQuery("SELECT * FROM " + DataQuiz.TABLE_NAME, null);
        ArrayList<String> quiz = new ArrayList<String>();
        mcusor.moveToFirst();
        while (!mcusor.isAfterLast()) {
            quiz.add("คำถาม = " + mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_QUIZ))
                    + "\n ข้อA = " + mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_A)) + "\n ข้อB = " +
                    mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_B))
                    + "\n ข้อC = " + mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_C)) + "\n ข้อD = " +
                    mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_D)) + "\n คำตอบคือ = " +
                    mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_ANSWERS)));

            mcusor.moveToNext();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,quiz);

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                getposittion(position);
                registerForContextMenu(listView);
                return false;
            }
        });


    }

    public int getposittion(int myposition) {
        this.myposition = myposition;
        return myposition;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater Mymenu = getMenuInflater();
        Mymenu.inflate(R.menu.my_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch (item.getItemId()) {
            case R.id.delete:
                Snackbar.make(findViewById(android.R.id.content), "" + myposition, Snackbar.LENGTH_LONG).show();
                mcusor.moveToPosition(myposition);
                String Question = mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_QUIZ));
                String answer = mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_ANSWERS));
                db.execSQL("DELETE FROM " + DataQuiz.TABLE_NAME + " WHERE " + DataQuiz.COL_QUIZ + "= '" + Question + "'"
                        + " AND " + DataQuiz.COL_ANSWERS + "= '" + answer + "';");


                onResume();
                return true;
            case R.id.update:
                AlertDialog.Builder update = new AlertDialog.Builder(this);
                update.setTitle("เเก้ไข");
                LayoutInflater inflate = getLayoutInflater();
                final View view = inflate.inflate(R.layout.update, null);
                update.setView(view);
                mcusor.moveToPosition(myposition);

                final String CheckQuestion = mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_QUIZ));
                final String CheckAnswer = mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_ANSWERS));
                Snackbar.make(findViewById(android.R.id.content), CheckQuestion + ":::" + CheckAnswer, Snackbar.LENGTH_LONG).show();


                final EditText question = (EditText) view.findViewById(R.id.update_ques);
                question.setText(mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_QUIZ)));
                final EditText update_a = (EditText) view.findViewById(R.id.update_a);
                update_a.setText(mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_A)));
                final EditText update_b = (EditText) view.findViewById(R.id.update_b);
                update_b.setText(mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_B)));
                final EditText update_c = (EditText) view.findViewById(R.id.update_c);
                update_c.setText(mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_C)));
                final EditText update_d = (EditText) view.findViewById(R.id.update_d);
                update_d.setText(mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_D)));
                final EditText update_answer = (EditText) view.findViewById(R.id.update_answer);
                update_answer.setText(mcusor.getString(mcusor.getColumnIndex(DataQuiz.COL_ANSWERS)));
                update.setNegativeButton("บันทึก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(findViewById(android.R.id.content), "" + myposition, Snackbar.LENGTH_LONG).show();
                        String My_Question = question.getText().toString();
                        String My_A = update_a.getText().toString();
                        String My_B = update_b.getText().toString();
                        String My_C = update_c.getText().toString();
                        String My_D = update_d.getText().toString();
                        String My_Answer = update_answer.getText().toString();

                        dq.getWritableDatabase();
                        db.execSQL("UPDATE " + DataQuiz.TABLE_NAME + " SET " + DataQuiz.COL_QUIZ + "='" + My_Question + "', "
                                + DataQuiz.COL_A + "='" + My_A + "', " + DataQuiz.COL_B + "='" + My_B + "', "
                                + DataQuiz.COL_C + "='" + My_C + "', " + DataQuiz.COL_D + "='" + My_D + "', "
                                + DataQuiz.COL_ANSWERS + "='" + My_Answer
                                + "' WHERE " + DataQuiz.COL_QUIZ + "='" + CheckQuestion + "'"
                                + " AND " + DataQuiz.COL_ANSWERS + "='" + CheckAnswer + "';");

                        onResume();


                    }
                });


                update.setPositiveButton("ยกเลิก", null);
                update.show();
                return true;


            default:
                return super.onContextItemSelected(item);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dq.close();
        db.close();
    }
}

