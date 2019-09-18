package com.example.rona.kumatraining14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddSkills extends AppCompatActivity {
    TextView name;
    EditText skill;
    ListView lvs;
    SeekBar power;
    Button btn;

    DatabaseReference db;

    List<Skills> listskill;

    Intent bearsense;
    @Override
    protected void onStart() {
        super.onStart();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listskill.clear();
                for(DataSnapshot bearss: dataSnapshot.getChildren()){
                    Skills bear = bearss.getValue(Skills.class);

                    listskill.add(bear);
                }
                ArrayAdapter adapter = new SkillList(AddSkills.this,listskill);
                lvs.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skills);
        bearsense = getIntent();
        String id = bearsense.getStringExtra("bearid");
        db = FirebaseDatabase.getInstance().getReference("Skills").child(id);

        name = findViewById(R.id.bear_name);
        skill = findViewById(R.id.txt_skill);
        lvs = findViewById(R.id.listviewskills);
        power = findViewById(R.id.powerlevel);
        btn = findViewById(R.id.btn_add);

        bearsense = getIntent();

        String name = bearsense.getStringExtra("bearname");
        this.name.setText(name);



        listskill = new ArrayList<Skills>();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSkills();
            }
        });

        lvs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Skills skill = listskill.get(position);
                String bid = bearsense.getStringExtra("bearid");
                String name = bearsense.getStringExtra("bearname");
                showUpdateDialog(bid,skill.getId(),name,skill.getName(),skill.getPower());
                return false;
            }
        });

    }


    private void showUpdateDialog(final String bid,final String id, String name, String skillname, final String power){
        AlertDialog.Builder dialogb = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogv = inflater.inflate(R.layout.update_skills,null);

        dialogb.setView(dialogv);

        final EditText txt_skill = dialogv.findViewById(R.id.txt_skill);
        final SeekBar powerlevel = dialogv.findViewById(R.id.powerlevel);
        final Button btn = dialogv.findViewById(R.id.btn_confirm);
        final Button btn_delete = dialogv.findViewById(R.id.btn_delete);

        txt_skill.setText(skillname);
        powerlevel.setProgress(Integer.parseInt(power));

        dialogb.setTitle("Updating "+name+" Skills : "+skillname );
        final AlertDialog ad = dialogb.create();
        ad.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = txt_skill.getText().toString();
                String power = String.valueOf(powerlevel.getProgress());
                updateSkills(bid,id,nama,power);
                ad.dismiss();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSkills(bid,id);
            }
        });



    }

    private boolean updateSkills(String bid,String id,String name,String power){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Skills").child(bid).child(id);

        Skills bear = new Skills(id,name,power);

        db.setValue(bear);

        return true;
    }

    private void saveSkills (){
        String name = this.skill.getText().toString();
        int level = power.getProgress();


        if(!TextUtils.isEmpty(name)){

            String id = db.push().getKey();

            Skills newskill = new Skills(id,name,String.valueOf(level));

            db.child(id).setValue(newskill);

            Toast.makeText(this,"Bear is in",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Bear Has  No Name QwQ ",Toast.LENGTH_LONG).show();
        }
    }

    private void deleteSkills(String bid,String id){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Skills").child(bid).child(id);

        db.removeValue();
    }
}
