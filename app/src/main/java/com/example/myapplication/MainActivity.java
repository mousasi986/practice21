package com.example.myapplication;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView ListView;
    private View parentView;

    private ArrayList<Message> messagesList;
    private MessageAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messagesList = new ArrayList<>();

        parentView = findViewById(R.id.parentLayout);

        ListView = (ListView) findViewById(R.id.listView);
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(parentView,messagesList.get(position).getImage() + "->" + messagesList.get(position).getText() + "->" + messagesList.get(position).getTime(), Snackbar.LENGTH_LONG).show();

            }
        });

        Toast toast = Toast.makeText(getApplicationContext(),R.string.string_click_to_load,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(InternetConnection.checkConnection(getApplicationContext())){
                    final ProgressDialog dialog;

                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setTitle(getString(R.string.string_getting_json_title));
                    dialog.setMessage(getString(R.string.string_getting_json_message));
                    dialog.show();

                    MessageApi api =  RetroClient.getMessageApi();

                    Call<List<MessageList>> call = api.messages();

                    call.enqueue(new Callback<List<MessageList>>() {
                        @Override
                        public void onResponse(Call<List<MessageList>> call, Response<List<MessageList>> response) {
                            dialog.dismiss();

                            if(response.isSuccessful()){

                                messagesList = response.body().getMessages();

                                adapter = new MessageAdapter(MainActivity.this,messagesList);
                                ListView.setAdapter(adapter);

                            } else {
                                Snackbar.make(parentView,R.string.string_wrong,Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<MessageList>> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });
                }else{
                    Snackbar.make(parentView,R.string.connection_wrong,Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

}