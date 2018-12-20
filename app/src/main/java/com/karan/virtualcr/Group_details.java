package com.karan.virtualcr;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class Group_details extends AppCompatActivity implements Serializable {

    private ImageView group_log;
    private EditText group_name;
    private TextView group_members;
    private Button Create_group;
    private String group_naam;
    private StringBuilder group_membernames=new StringBuilder();
    private group_details_models group_details_to_upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);
        group_details_to_upload=(group_details_models) getIntent().getSerializableExtra("selected users");
        group_log=findViewById(R.id.imageView12);
        group_name=findViewById(R.id.textView18);
        group_members=findViewById(R.id.textView23);
        Create_group =findViewById(R.id.button5);
        for(Contents item: group_details_to_upload.getGroupMembers())
            group_membernames.append(item.getfirstString()+",  ");
        int n=group_membernames.lastIndexOf(",");
        group_membernames.deleteCharAt(n);
        group_members.setText(group_membernames);
        Create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName=group_name.getText().toString().trim();
                if(TextUtils.isEmpty(groupName))
                    group_name.setError("Please Enter group name");
                else {
                    DatabaseReference group= FirebaseDatabase.getInstance().getReferenceFromUrl("https://virtualcr-51b83.firebaseio.com/Groups").push();
                    group_details_to_upload.setGroup_name(groupName);
                    group_details_to_upload.setGroup_admin(MainActivity.Mobile_no);
                    group.setValue(group_details_to_upload);
                    String Group=group.toString();
                    for(Contents groupmember:group_details_to_upload.getGroupMembers()) {
                        String Groupmembermobile=Contacts.filterContact(groupmember.getsecondString()).toString();
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://virtualcr-51b83.firebaseio.com/Users/"+Groupmembermobile);
                        databaseReference.child("ActiveGroups").child(groupName).setValue(Group);
                    }
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://virtualcr-51b83.firebaseio.com/Users/"+MainActivity.Mobile_no);
                    databaseReference.child("ActiveGroups").child(groupName).setValue(Group);
//                    SQLDatabase sqldbhelper=new SQLDatabase(getBaseContext());
//                    SQLiteDatabase sqldb = sqldbhelper.getWritableDatabase();
//                    ContentValues values = new ContentValues();
//                    values.put(Groupdatabase.Grouptabledata.COLOUMN1,n);
//                    values.put(Groupdatabase.Grouptabledata.COLOUMN2,"");
//                    values.put(Groupdatabase.Grouptabledata.COLOUMN3,"");
//                    values.put(Groupdatabase.Grouptabledata.COLOUMN4,"") ;
//                    sqldb.insert(Groupdatabase.Grouptabledata.TABLE_NAME, null, values);
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    MainActivity.loginstatus=true;
                   startActivity(intent);
                    finish();
                }
            }
        });
    }
}
