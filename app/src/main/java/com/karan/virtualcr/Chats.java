package com.karan.virtualcr;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Chats extends Fragment {
    ArrayList<Contents> list;
    AdapterCustom  adapter;
    public static ArrayList <group_details_models> Groupslist=new ArrayList<>();
    static Set<Contents> dispaly_contents;
    DatabaseReference alert;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dispaly_contents=new HashSet<>();
        alert= FirebaseDatabase.getInstance().getReferenceFromUrl("https://virtualcr-51b83.firebaseio.com/Users/"+MainActivity.Mobile_no+"/ActiveGroups/");
        final ContentValues values = new ContentValues();
        SQLDatabase sqldbhelper=new SQLDatabase(getActivity().getBaseContext());
        final SQLiteDatabase sqlwrite = sqldbhelper.getWritableDatabase();

        final SQLiteDatabase SQLoutput = new SQLDatabase(getActivity().getBaseContext()).getReadableDatabase();
        final String[] projection = {
                Groupdatabase.Grouptabledata.COLOUMN1,
                Groupdatabase.Grouptabledata.COLOUMN2,
                Groupdatabase.Grouptabledata.COLOUMN3,
                Groupdatabase.Grouptabledata.COLOUMN4,

        };



        alert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( DataSnapshot s:dataSnapshot.getChildren()) {
                    dispaly_contents.add(new Contents(0,0,0,0,s.getKey(),null,null,s.getValue().toString()));
//                   values.put(Groupdatabase.Grouptabledata.COLOUMN1, s.getKey());
//                   values.put(Groupdatabase.Grouptabledata.COLOUMN2, "");
//                   values.put(Groupdatabase.Grouptabledata.COLOUMN3, "");
//                   values.put(Groupdatabase.Grouptabledata.COLOUMN4, "");
//                   sqlwrite.insert(Groupdatabase.Grouptabledata.TABLE_NAME, null, values);

                }


//                try(Cursor cursor =SQLoutput.query(
//                        Groupdatabase.Grouptabledata.TABLE_NAME,
//                        projection,null,null,null,null,null
//
//                )){while(cursor.moveToNext())
//                {
//                    dispaly_contets.add(new Contents(0,0,0,0,
//                            cursor.getString(cursor.getColumnIndex(Groupdatabase.Grouptabledata.COLOUMN1)),
//                            cursor.getString(cursor.getColumnIndex(Groupdatabase.Grouptabledata.COLOUMN2)),
//                            cursor.getString(cursor.getColumnIndex(Groupdatabase.Grouptabledata.COLOUMN3)),
//                            null));
//                }
//                }
                ListView listView=getActivity().findViewById(R.id.listview2);
               list=new ArrayList<>();
                list.addAll(dispaly_contents);
                adapter=new AdapterCustom(getActivity().getBaseContext(),list,R.layout.chatview,0,0,0,0,R.id.textView,R.id.textView2,R.id.textView3,0);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent =new Intent(getActivity().getBaseContext(),GroupInsideActivity.class);
                        intent.putExtra("Group_name",list.get(position).getfirstString());
                        intent.putExtra("Group_link",list.get(position).getFourthstring());
                        Toast.makeText(getContext(),list.get(position).getFourthstring(),Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        View rootView= inflater.inflate(R.layout.fragment_chats, container, false);
        return rootView;
    }

}
