package com.example.rona.kumatraining14;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import androidx.annotation.NonNull;

public class BearList extends ArrayAdapter {
    private Activity con;
    private List<Bear> bearlist;

    public BearList(Activity con,List<Bear> list){
        super(con, R.layout.bear_list,list);
        this.con = con;
        this.bearlist = list;
    }

    @NonNull
    @Override
    public View getView(int position, View cview, ViewGroup parent){
        LayoutInflater inflater = con.getLayoutInflater();

        View ListViewBear = inflater.inflate(R.layout.bear_list,null,true);

        TextView txt_name = ListViewBear.findViewById(R.id.name);
        TextView txt_level = ListViewBear.findViewById(R.id.level);

        Bear bear = bearlist.get(position);

        txt_name.setText(bear.getName());
        txt_level.setText(bear.getLevel());

        return ListViewBear;
    }
}
