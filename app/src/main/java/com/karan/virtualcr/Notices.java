package com.karan.virtualcr;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/* *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Notices.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Notices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notices extends Fragment {
    private ArrayList<Contents> notificions=new ArrayList();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        for(int i=0;i<5;i++)
            notificions.add(new Contents(0,0,0,0,"you got a new message "+i,""+i+":45",null,null));
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_notices, container, false);
        ListView listView=rootView.findViewById(R.id.notice_listview);
        AdapterCustom adapterCustom=new AdapterCustom(this.getContext(),notificions,R.layout.noticeview,0,0,0,0,R.id.textView4,R.id.textView5,0,0);
        listView.setAdapter(adapterCustom);
        return rootView;
    }


}
