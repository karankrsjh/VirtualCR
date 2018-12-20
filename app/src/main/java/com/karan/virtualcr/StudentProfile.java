package com.karan.virtualcr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentProfile extends AppCompatActivity implements View.OnClickListener{
    private TextView name;
    private TextView Class_roll_no;
    private TextView Exam_roll_no;
    private TextView Email_id;
    private ImageView pic;
    private Button Profile_done;
    private String Mobileno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        name=findViewById(R.id.Student_name);
        Class_roll_no=findViewById(R.id.Student_Rollno);
        Exam_roll_no=findViewById(R.id.Student_exam_Rollno);
        Email_id=findViewById(R.id.email);
        pic=findViewById(R.id.imageView10);
        Profile_done=findViewById(R.id.Profile_Done);
        Profile_done.setOnClickListener(this);
        SharedPreferences mobileprefrence = getSharedPreferences("User Mobile_no", Context.MODE_PRIVATE);
        Mobileno = Contacts.filterContact(mobileprefrence.getString("Mobile_no","")).toString();
        Toast.makeText(getBaseContext(),Mobileno,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Profile_Done) {
            String user_name = name.getText().toString();
            String class_roll = Class_roll_no.getText().toString();
            String exam_roll = Exam_roll_no.getText().toString();
            String Email = Email_id.getText().toString();
            if (TextUtils.isEmpty(user_name)) {
                name.setError("Name field can't be empty");
                return;
            }
            if (TextUtils.isEmpty(class_roll)) {
                Class_roll_no.setError("Class Roll_no. can't  be empty");
                return;
            }
            SharedPreferences userprefrence = getSharedPreferences("User Details", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed =userprefrence.edit();
            ed.putString("User Name",user_name);
            ed.putString("User Class Roll_no",class_roll);
            ed.putString("User Exam Roll_no",exam_roll);
            ed.putString("User Email",Email);
            ed.apply();
            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://virtualcr-51b83.firebaseio.com");
            firebaseDatabase.child("Users").child(Mobileno).child("Name").setValue(user_name);
            firebaseDatabase.child("Users").child(Mobileno).child("Class_Roll_no").setValue(class_roll);
            firebaseDatabase.child("Users").child(Mobileno).child("Exam_Roll_no").setValue(exam_roll);
            firebaseDatabase.child("Users").child(Mobileno).child("Email_id").setValue(Email);
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();

        }

    }
}
