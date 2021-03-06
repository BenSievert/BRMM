package com.example.brmm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class edit_member extends AppCompatActivity {

    //Textviews
    private TextView header_textview;
    private TextView member_type_textview;
    private TextView fname_textview;
    private TextView lname_textview;
    private TextView ULID_textview;
    private TextView role_textview;
    private TextView UID_textview;
    private TextView section_textview;
    private TextView notes_textview;

    //Edittexts
    private EditText fname_edittext;
    private EditText lname_edittext;
    private EditText ULID_edittext;
    private EditText role_edittext;
    private EditText UID_edittext;
    private EditText notes_edittext;

    //Dropdowns
    private Spinner section_spin;

    //Radio
    private RadioGroup rgroup;
    private RadioButton student_rb;
    private RadioButton faculty_rb;

    //Strings and String Lists
    private String section;
    private ArrayList<String> sections;

    //integers
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);

        //Textviews
        header_textview = findViewById(R.id.edit_member_header);
        member_type_textview = findViewById(R.id.edit_member_type_textview);
        fname_textview = findViewById(R.id.fname_edit_member_textview);
        lname_textview = findViewById(R.id.lname_edit_member_textview);
        ULID_textview = findViewById(R.id.ulid_edit_member_textview);
        role_textview = findViewById(R.id.role_edit_member_textview);
        UID_textview = findViewById(R.id.UID_edit_member_textview);
        section_textview = findViewById(R.id.section_edit_member_textview);
        notes_textview = findViewById(R.id.notes_edit_member_textview);

        //Edittexts
        fname_edittext = findViewById(R.id.fname_edit_member_edittext);
        lname_edittext = findViewById(R.id.lname_edit_member_edittext);
        ULID_edittext = findViewById(R.id.ulid_edit_member_edittext);
        role_edittext = findViewById(R.id.role_edit_member_edittext);
        UID_edittext = findViewById(R.id.UID_edit_member_edittext);
        notes_edittext = findViewById(R.id.notes_edit_member_edittext);

        //Dropdowns
        section_spin = findViewById(R.id.section_edit_member_dropdown);
        final Spinner pick_spin = findViewById(R.id.pick_edit_member_dropdown);


        //Radio
        rgroup = findViewById(R.id.member_type_edit_member_radiogroup);
        student_rb = findViewById(R.id.student_edit_member_radiobutton);
        faculty_rb = findViewById(R.id.faculty_edit_member_radiobutton);

        //Buttons
        Button cancel_member = findViewById(R.id.cancel_edit_member_button);
        Button ok_button = findViewById(R.id.ok_edit_member_button);

        //sets up section list spinner
        sections = getIntent().getStringArrayListExtra("sectionlist");
        if (sections != null) {
            ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, sections);
            section_spin.setAdapter(sectionAdapter);
        }

        //sets up BandMember spinner
        final ArrayList<Integer> members = new ArrayList<>();
        final ArrayList<BandMember> temp = (ArrayList<BandMember>) getIntent().getSerializableExtra("memberlist");
        if (temp != null) {
            for (BandMember bm : temp) {
                members.add(bm.getUID());
            }
            if (members != null) {
                ArrayAdapter<Integer> memberAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, members);
                pick_spin.setAdapter(memberAdapter);
            }
        }



        //edits the member
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName;
                String lastName;
                String ulid;
                int UID;
                String role;
                String notes;
                Boolean isFaculty = false;
                //temp logic to prevent error
                BandMember member = new BandMember();

                firstName = fname_edittext.getText().toString();
                lastName = lname_edittext.getText().toString();
                ulid = ULID_edittext.getText().toString();
                role = role_edittext.getText().toString();
                try {
                    UID = Integer.parseInt(UID_edittext.getText().toString());
                } catch (NumberFormatException ex) {
                    UID = 0;
                }

                notes = notes_edittext.getText().toString();
                if (student_rb.isChecked())
                    isFaculty = false;
                if (faculty_rb.isChecked())
                    isFaculty = true;

                if (!firstName.equals(""))
                    member.setFname(firstName);
                if (!lastName.equals(""))
                    member.setLname(lastName);
                if (!ulid.equals(""))
                    member.setUlid(ulid);
                if (!role.equals("")) {
                    if (member instanceof Faculty)
                        ((Faculty) member).setRole(role);

                }
                if (UID != 0)
                    member.setUID(UID);
                if (!notes.equals("")) {
                    if (member instanceof Student)
                        ((Student) member).setNotes(notes);
                }

                Intent intent = getIntent();
                intent.putExtra("member", member);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //cancels all fields
        cancel_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname_edittext.setText("");
                lname_edittext.setText("");
                ULID_edittext.setText("");
                UID_edittext.setText("");
                role_edittext.setText("");
                notes_edittext.setText("");
                student_rb.setChecked(false);
                faculty_rb.setChecked(false);
                section_spin.setSelection(0);
                finish();
            }
        });
        //pick_spin logic
        pick_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(members!=null) {
                    count = 0;
                    for (BandMember bm : temp) {

                        if (Integer.parseInt(pick_spin.getSelectedItem().toString()) == bm.getUID()) {
                            fname_edittext.setText(temp.get(count).getFname());
                            lname_edittext.setText(temp.get(count).getLname());
                            ULID_edittext.setText(temp.get(count).getUlid());
                            UID_edittext.setText(Integer.toString(temp.get(count).getUID()));
                            notes_edittext.setText("");
                            if(sections!=null) {
                                int pos = sections.indexOf(temp.get(count).getSection());
                                section_spin.setSelection(pos);
                            }
                            if (bm instanceof Student) {
                                student_rb.setChecked(true);

                            } else {
                                faculty_rb.setChecked(true);
                            }
                            Intent intent = getIntent();
                            intent.putExtra("count", count);
                            break;
                        }
                        count++;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                section = "";
            }
        });

        //section spinner logic
        section_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                section = section_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                section = "";
            }
        });

        //sets visibility
        rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (faculty_rb.isChecked() == true) {
                    role_textview.setVisibility(View.VISIBLE);
                    role_edittext.setVisibility(View.VISIBLE);
                    section_textview.setVisibility(View.INVISIBLE);
                    section_spin.setVisibility(View.INVISIBLE);
                    notes_textview.setVisibility(View.INVISIBLE);
                    notes_edittext.setVisibility(View.INVISIBLE);
                } else {
                    role_textview.setVisibility(View.INVISIBLE);
                    role_edittext.setVisibility(View.INVISIBLE);
                    section_textview.setVisibility(View.VISIBLE);
                    section_spin.setVisibility(View.VISIBLE);
                    notes_textview.setVisibility(View.VISIBLE);
                    notes_edittext.setVisibility(View.VISIBLE);
                }
            }
        });



    }
    //Timeout Timer
    private Timer timer;

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        timer.cancel();
        timer.purge();
        timer = new Timer();
        TimerTask timeOutTask = new TimerTask() {
            @Override
            public void run() {

                timeOut(); }
        };
        timer.schedule(timeOutTask, main_screen.logoutTime);
    }
    //sets timer to null when no longer on screen
    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        timer.purge();
    }
    //resets timer when resuming activity
    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        TimerTask timeOutTask = new TimerTask() {
            @Override
            public void run() {
                timeOut(); }
        };
        timer.schedule(timeOutTask, main_screen.logoutTime);
    }
    //return to main screen
    private void timeOut(){
        Intent intent = new Intent();
        intent.putExtra("timeOut", true);
        setResult(RESULT_OK,intent);
        finish();
    }
}
