package com.example.chaturl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    {
        public static int kol=100;
        private FirebaseListAdapter<sas> adapter;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference("message");
    String nick="Анонимус";
    ArrayList<String> messages=new ArrayList<>();
    ListView mListView;
    ScrollView scroll;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendm = (Button)findViewById(R.id.send);
        Button nicke = (Button)findViewById(R.id.nickmen);
        nicke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opDil();
            }
        });
        sendm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.texts);
                String mes=input.getText().toString();
                String mest=mes.trim();
                if(mest.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Введите сообщение", Toast.LENGTH_LONG).show();
                    return;
                }
                if(mes.length()>kol)
                {
                    Toast.makeText(getApplicationContext(), "Максимальная длинна сообщения 100 символов", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    ref.push().setValue(nick+":"+input.getText().toString());
                    input.setText("");
                }
            }
        });
        mListView = (ListView) findViewById(R.id.chat);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.gopss,R.id.label, messages);
        mListView.setAdapter(arrayAdapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                messages.add(value);
                arrayAdapter.notifyDataSetChanged();
                scrollMyListViewToBottom();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            scrollMyListViewToBottom();
            scroll=(ScrollView) findViewById(R.id.scrolles);
            EditText input = (EditText)findViewById(R.id.texts);
            input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus==true)
                    {
                        scrollMyScrollViewToBottom();
                    }
                    else
                    {
                        scrollMyScrollViewToUP();
                }
                }
            });
    }
        public void opDil() {
            try {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.nicked);

                Button ok = dialog.findViewById(R.id.setnick);
                final EditText nicd = dialog.findViewById(R.id.niker);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String sas=nicd.getText().toString();
                            if (sas.equals("") || sas.length()<5)
                            {
                                Toast.makeText(getApplicationContext(), "Никнейм должен быть не короче 5-ти символов", Toast.LENGTH_LONG).show();
                            } else
                            {
                                dialog.hide();
                                nick = nicd.getText().toString();
                                nick=nick.trim();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
                dialog.show();
            } catch (Exception ex) {
                //Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        private void scrollMyListViewToBottom() {
            mListView.post(new Runnable() {
                @Override
                public void run() {
                    mListView.setSelection(mListView.getCount() - 1);
                }
            });
        }
        private void scrollMyScrollViewToBottom() {
            scroll.post(new Runnable() {
                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
        private void scrollMyScrollViewToUP() {
            scroll.post(new Runnable() {
                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        }

    }
