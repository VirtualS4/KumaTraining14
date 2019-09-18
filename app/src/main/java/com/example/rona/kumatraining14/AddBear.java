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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddBear extends AppCompatActivity {

    Button add;
    EditText txt;
    Spinner cute;
    ListView lv;
    List<Bear> blist;

    DatabaseReference db;

    @Override
    protected void onStart() {
        super.onStart();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blist.clear();
                for(DataSnapshot bearss: dataSnapshot.getChildren()){
                    Bear bear = bearss.getValue(Bear.class);

                    blist.add(bear);
                }

                ArrayAdapter adapter = new BearList(AddBear.this,blist);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beartivity2);

        db = FirebaseDatabase.getInstance().getReference("Bears");


        add = findViewById(R.id.btn_add);
        txt = findViewById(R.id.txt_name);
        cute = findViewById(R.id.spinner);

        lv = findViewById(R.id.listview);

        blist = new ArrayList<Bear>();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBear();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bear bear = blist.get(position);

                Intent bearsense = new Intent(getApplicationContext(), AddSkills.class);
                bearsense.putExtra("bearid",bear.getId());
                bearsense.putExtra("bearname",bear.getName());

                startActivity(bearsense);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Bear bear = blist.get(position);

                showUpdateDialog(bear.getId(),bear.getName(),bear.getLevel());
                return false;
            }
        });
    }

    private void showUpdateDialog(final String id,String name,String level){
        AlertDialog.Builder dialogb = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogv = inflater.inflate(R.layout.update_bear,null);

        dialogb.setView(dialogv);

        final EditText txt_name = dialogv.findViewById(R.id.txt_name);
        final Spinner txt_level = dialogv.findViewById(R.id.spn_level);
        final Button btn = dialogv.findViewById(R.id.btn_confirm);
        final Button btn_delete = dialogv.findViewById(R.id.btn_delete);


        txt_name.setText(name);

        for(int i = 0; i<txt_level.getAdapter().getCount();i++){
            if(txt_level.getItemAtPosition(i).toString().equals(level)){
                txt_level.setSelection(i);
                break;
            }else{

            }
        }
        dialogb.setTitle("Updating Bear : "+name );
        final AlertDialog ad = dialogb.create();
        ad.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = txt_name.getText().toString();
                String lvl =  txt_level.getSelectedItem().toString();
                updateBear(id,nama,lvl);
                ad.dismiss();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteBear(id);
            }
        });



    }
    private boolean updateBear(String id,String name,String level){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Bears").child(id);

        Bear bear = new Bear(id,name,level);

        db.setValue(bear);

        return true;
    }
    private void addBear(){
        String name = txt.getText().toString();
        String level = cute.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){

            String id = db.push().getKey();

            Bear newbear = new Bear(id,name,level);

            db.child(id).setValue(newbear);

            Toast.makeText(this,"Bear is in",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Bear Has  No Name QwQ ",Toast.LENGTH_LONG).show();
        }
    }

    private void deleteBear(String id){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Bears").child(id);
        db.removeValue();
        db = FirebaseDatabase.getInstance().getReference("Skills").child(id);
        db.removeValue();


        Toast.makeText(this,"Bear is gone QwQ",Toast.LENGTH_LONG).show();
    }
}
