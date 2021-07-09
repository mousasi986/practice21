package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {

    List<Message> messagesList;
    Context context;
    private LayoutInflater mInflater;

    public MessageAdapter(Context context,List<Message> objects){

        super(context,0,objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        messagesList = objects;
    }

    @Override
    public Message getItem(int position){

        return messagesList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;
        if(convertView == null){
            View view = mInflater.inflate(R.layout.myproject,parent,false);
            viewHolder = ViewHolder.create((RelativeLayout) view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Message item = getItem(position);

        viewHolder.textViewMessage.setText(item.getText());
        viewHolder.textViewName.setText(item.getText());
        Picasso.with(context).load(item.getImage());
        return viewHolder.rootView;
    }

    private static class ViewHolder{
        public final RelativeLayout rootView;
        public final ImageView  imageView;
        public final TextView textViewName;
        public final TextView textViewMessage;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewMessage) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.textViewMessage = textViewMessage;
        }

        public static ViewHolder create(RelativeLayout rootView){
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewMessage = (TextView) rootView.findViewById(R.id.textViewMessage);
            return new ViewHolder(rootView,imageView,textViewName,textViewMessage);

        }
    }




}
