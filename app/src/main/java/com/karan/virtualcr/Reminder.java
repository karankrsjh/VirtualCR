package com.karan.virtualcr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class Reminder extends Fragment {
    private ArrayList<Contents> reminder=new ArrayList();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        for(int i=0;i<5;i++)
            reminder.add(new Contents(0,0,0,0,"mathematics class"+i,""+i+":45",""+(i+1)+":45",null));
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_reminder, container, false);
        ListView listView=rootView.findViewById(R.id.reminder_listview);
        AdapterCustom adapterCustom=new AdapterCustom(this.getContext(),reminder,R.layout.reminderview,0,0,0,0,R.id.textView6,R.id.textView10,R.id.textView8,0);
        listView.setAdapter(adapterCustom);
        return rootView;
    }

}