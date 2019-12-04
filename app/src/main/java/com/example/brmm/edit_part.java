package com.example.brmm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class edit_part extends AppCompatActivity {
    private String name;
    private String sn;
    private double cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_part);

        //textviews
        /*
        TextView header = findViewById(R.id.edit_parts_header);
        TextView name_textview = findViewById(R.id.name_edit_part_textview);
        TextView sn_textview = findViewById(R.id.sn_edit_part_textview);
        TextView cost_textview = findViewById(R.id.cost_edit_part_textview)
        TextView compwith_textview = findViewById(R.id.compwith_edit_parts_textview);
        */

        //edittexts
        final EditText name_edittext = findViewById(R.id.name_edit_part_edittext);
        final EditText sn_edittext = findViewById(R.id.sn_edit_part_edittext);
        final EditText cost_edittext = findViewById(R.id.cost_edit_part_edittext);

        //compwith objects
        Spinner compwith_spin = findViewById(R.id.compwith_edit_part_dropdown);
        RecyclerView compwith_rview = findViewById(R.id.compwith_edit_part_rview);

        //buttons
        Button ok_button = findViewById(R.id.ok_edit_part_button);
        Button cancel_button = findViewById(R.id.cancel_edit_part_button);


        //wasnt sure exactly what this does
        compwith_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cost = Double.parseDouble(cost_edittext.getText().toString());
                name = name_edittext.getText().toString();
                sn = sn_edittext.getText().toString();

            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cost_edittext.setText("");
                name_edittext.setText("");
                sn_edittext.setText("");
            }
        });
    }
}
