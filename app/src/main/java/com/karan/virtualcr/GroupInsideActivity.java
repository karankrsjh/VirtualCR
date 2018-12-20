package com.karan.virtualcr;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupInsideActivity extends AppCompatActivity {
   private String groupName;
   private String group_link;
   private LinearLayout ll;
   private ImageButton send;
    private boolean admin=false;
   private FirebaseListAdapter<chatModel> adapter;
   String Group_admin;
   private HashMap<String,Object> group_details;
   DatabaseReference databaseReference;
    private ArrayList<Contents> questions=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.group_inside);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        groupName=getIntent().getStringExtra("Group_name");
        group_link=getIntent().getStringExtra("Group_link");
        ll=findViewById(R.id.Linear);
        ll.setVisibility(View.GONE);
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl(group_link);
        toolbar.setTitle(groupName);


        ListView listOfMessages = (ListView)findViewById(R.id.insidegrouplistview);

        adapter = new FirebaseListAdapter<chatModel>(this, chatModel.class,
                R.layout.chatforothers, FirebaseDatabase.getInstance().getReferenceFromUrl(group_link+"/Chats/")) {
            @Override
            protected void populateView(View v, chatModel model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.textView4);
                TextView messageUser = (TextView)v.findViewById(R.id.editText2);
                TextView messageTime = (TextView)v.findViewById(R.id.textView25);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy\n(HH:mm:ss)",
                        model.getMessageTime()));

                Button comment=v.findViewById(R.id.textView15);
                comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        )
                    }
                });
            }
        };
        listOfMessages.setAdapter(adapter);
        send=findViewById(R.id.imageButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.editText);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReferenceFromUrl(group_link+"/Chats/")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getPhoneNumber())
                        );

                // Clear the input
                input.setText("");
            }
        });
        final DatabaseReference get_details = FirebaseDatabase.getInstance().getReferenceFromUrl(group_link+"/groupadmin");
        get_details.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // if(group_details.getClass()==MainActivity.Mobile_no
                if(MainActivity.Mobile_no.equals(dataSnapshot.getValue().toString()))
                {
                    admin=true;
                }
                else
                {
                    admin=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
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

        return super.onOptionsItemSelected(item);
    }


}
