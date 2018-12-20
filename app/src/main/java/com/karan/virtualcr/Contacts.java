package com.karan.virtualcr;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {
    ArrayList<Contents> contact = new ArrayList<>();
    ArrayList<Contents> userslist = new ArrayList<>();
    ArrayList<Contents> selectedusers = new ArrayList<>();
    private ArrayList<Boolean> isselected;
    AdapterCustom adapterCustom;
    private  int count=0;
    private  int counttouch=0;
    ListView listView;
    FirebaseAuth users;
    private int no_of_contacts;
    private Button buttondone;
    private android.support.v7.widget.Toolbar toolbar;

    private DatabaseReference firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        toolbar=findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.cardview_light_background));
        users = FirebaseAuth.getInstance();
        String[] column = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY, ContactsContract.CommonDataKinds.Phone.NUMBER};
        ContentResolver contentResolver = getContentResolver();
        listView = findViewById(R.id.Contact_list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        buttondone =findViewById(R.id.Done);
        firebase = FirebaseDatabase.getInstance().getReference("Users");

       Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, column, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY);
        while (cursor.moveToNext() ) {

            contact.add(new Contents(0, 0, 0, 0, cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)), cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)), null, null));

        }

      cursor.close();
        for ( Contents item : contact)
        {
            String mob_no = item.getsecondString();
            if (mob_no.length()<10)
                continue;
            isVirtualCRUser(item);
            count++;

        }

       //for(int i=0;i<userslist.siz)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(!isselected.get(position))
                {
                    counttouch++;
                    toolbar.setSubtitle(counttouch + " items selected");
                    selectedusers.add(userslist.get(position));
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    isselected.set(position,true);
                }
                else if(isselected.get(position))
                {
                    counttouch--;
                    toolbar.setSubtitle(counttouch + " items selected");
                    selectedusers.remove(userslist.get(position));
                    view.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                    isselected.set(position,false);
                }

            }
        });
        buttondone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedusers.size()>0) {
                    group_details_models newgroup = new group_details_models();
                    newgroup.setGroupMembers(selectedusers);
                    Intent intent = new Intent(getBaseContext(), Group_details.class);
                    intent.putExtra("selected users",newgroup);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    void isVirtualCRUser(final Contents item) {
        String temp = item.getsecondString();
        final StringBuilder mob_no=filterContact(temp);

        firebase.addValueEventListener(  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.i("within OnDAtavhange", "dataSnapshot.exists()");

                count--;
                if(dataSnapshot.child(mob_no.toString()).exists())
                {
                    userslist.add(item);
                }
                if (count==0)
                {
                    Log.i("hello","hello");
                    adapterCustom = new AdapterCustom(getBaseContext(), userslist, R.layout.cotactsview, 0, 0, 0, 0, R.id.Contact_name, R.id.contact_number, 0, 0);
                    listView.setAdapter(adapterCustom);
                    no_of_contacts=userslist.size();
                    isselected=new ArrayList<>();
                    for(int i=0;i<no_of_contacts;i++)
                        isselected.add(false);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


   static StringBuilder filterContact(String m) {
        if(m.length()>=10)
        {
        StringBuilder k = new StringBuilder(m.trim());
        k.reverse();
        StringBuilder refinedno = new StringBuilder(k.substring(0, 10));
        refinedno.reverse();
        return refinedno;
        }
        else
            return new StringBuilder(m);
    }

}