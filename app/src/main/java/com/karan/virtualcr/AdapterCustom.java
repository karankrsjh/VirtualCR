package com.karan.virtualcr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;


public class AdapterCustom extends ArrayAdapter<Contents> implements Serializable{
    private Context context;
    private ArrayList<Contents> content_list;
    private int resource_id,imageViewid1,imageViewid2,imageViewid3,imageViewid4,textViewid1,textViewid2,textViewid3,textViewid4;
    AdapterCustom(@NonNull Context context,ArrayList<Contents> Content_list,int resource_id,int imgID1,int imgId2,int imgID3,int imgID4,int text1,int text2,int text3,int text4)
    {
        super(context,0,Content_list);
        this.context = context;
        this.content_list= Content_list;
        this.resource_id=resource_id;
        this.imageViewid1=imgID1;
        this.imageViewid2=imgId2;
        this.imageViewid3=imgID3;
        this.imageViewid4=imgID4;
        this.textViewid1=text1;
        this.textViewid2=text2;
        this.textViewid3=text3;
        this.textViewid4=text4;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null)
            {
                convertView= LayoutInflater.from(context).inflate(resource_id,parent,false);
            }

            int imgdata1=content_list.get(position).getfirstinteger();
            if(imgdata1!=0)
            {
                ImageView imageView =convertView.findViewById(imageViewid1);
                imageView.setImageResource(imgdata1);
            }

            int imgdata2=content_list.get(position).getsecondinteger();
            if(imgdata2!=0)
            {
                ImageView imageView =convertView.findViewById(imageViewid2);
                imageView.setImageResource(imgdata2);
            }

            int imgdata3=content_list.get(position).getsecondinteger();
            if(imgdata3!=0)
            {
                ImageView imageView =convertView.findViewById(imageViewid3);
                imageView.setImageResource(imgdata3);
            }

            int imgdata4=content_list.get(position).getsecondinteger();
            if(imgdata4!=0)
            {
                ImageView imageView =convertView.findViewById(imageViewid4);
                imageView.setImageResource(imgdata4);
            }

            String textdata1=content_list.get(position).getfirstString();
            if(textdata1!=null)
            {
                TextView textView =convertView.findViewById(textViewid1);
                textView.setText(textdata1);
            }

            String textdata2=content_list.get(position).getsecondString();
            if(textdata2!=null)
            {
                TextView textView =convertView.findViewById(textViewid2);
                textView.setText(textdata2);
            }

             String textdata3=content_list.get(position).getthirdstring();
            if(textdata3!=null)
           {
               TextView textView =convertView.findViewById(textViewid3);
               textView.setText(textdata3);
           }
        String textdata4=content_list.get(position).getFourthstring();
        if(textdata4!=null)
        {
            TextView textView =convertView.findViewById(textViewid4);
            if(textView!=null)
            textView.setText(textdata4);
        }

        return convertView;
    }
}
