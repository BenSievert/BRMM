package com.example.brmm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class edit_member extends AppCompatActivity {

    //Textviews
    private TextView header_textview;
    private TextView member_type_textview;
    private TextView fname_textview;
    private TextView lname_textview;
    private TextView dept_textview;
    private TextView role_textview;
    private TextView UID_textview;
    private TextView section_textview;
    private TextView notes_textview;
    //Edittexts
    private EditText fname_edittext;
    private EditText lname_edittext;
    private EditText dept_edittext;
    private EditText role_edittext;
    private EditText UID_edittext;
    private EditText notes_edittext;
    //Dropdowns
    private Spinner section_spin;

    //Radio
    private RadioGroup rgroup;
    private RadioButton student_rb;
    private RadioButton faculty_rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);

        //Textviews
        header_textview = findViewById(R.id.edit_member_header);
        member_type_textview = findViewById(R.id.edit_member_type_textview);
        fname_textview = findViewById(R.id.fname_edit_member_textview);
        lname_textview = findViewById(R.id.lname_edit_member_textview);
        dept_textview = findViewById(R.id.dept_edit_member_textview);
        role_textview = findViewById(R.id.role_edit_member_textview);
        UID_textview = findViewById(R.id.UID_edit_member_textview);
        section_textview = findViewById(R.id.section_edit_member_textview);
        notes_textview = findViewById(R.id.notes_edit_member_textview);

        //Edittexts
        fname_edittext = findViewById(R.id.fname_edit_member_edittext);
        lname_edittext = findViewById(R.id.fname_edit_member_edittext);
        dept_edittext = findViewById(R.id.fname_edit_member_edittext);
        role_edittext = findViewById(R.id.fname_edit_member_edittext);
        UID_edittext = findViewById(R.id.fname_edit_member_edittext);
        notes_edittext = findViewById(R.id.notes_edit_member_edittext);

        //Dropdowns
        section_spin = findViewById(R.id.section_edit_member_dropdown);

        //Radio
        rgroup = findViewById(R.id.member_type_edit_member_radiogroup);
        student_rb = findViewById(R.id.student_edit_member_radiobutton);
        faculty_rb = findViewById(R.id.faculty_edit_member_radiobutton);

    }
}
