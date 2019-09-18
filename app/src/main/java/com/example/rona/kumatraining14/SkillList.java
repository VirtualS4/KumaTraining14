package com.example.rona.kumatraining14;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import androidx.annotation.NonNull;

public class SkillList extends ArrayAdapter {
    private Activity con;
    private List<Skills> skilllist;

    public SkillList(Activity con,List<Skills> list){
        super(con, R.layout.activity_add_skills,list);
        this.con = con;
        this.skilllist = list;
    }

    @NonNull
    @Override
    public View getView(int position, View cview, ViewGroup parent){
        LayoutInflater inflater = con.getLayoutInflater();

        View ListViewSkill = inflater.inflate(R.layout.bear_list,null,true);

        TextView txt_name = ListViewSkill.findViewById(R.id.name);
        TextView txt_level = ListViewSkill.findViewById(R.id.level);

        Skills bear = skilllist.get(position);

        txt_name.setText(bear.getName());
        txt_level.setText(bear.getPower());

        return ListViewSkill;
    }
}
