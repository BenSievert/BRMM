
package com.example.brmm;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class login_screen extends AppCompatActivity {
    private int counter;
    private int multiplier;
    private int time;
    private ArrayList<Faculty> faculty;
    private ArrayList<Student> students;

    DatabaseConnection connection = null;
    DatabaseWrapper wrapper = null;
    Thread thread2;


    public login_screen() {
        time = 60000; //1 minute
        counter = 0;
        multiplier = 1;
    }


    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);


        //Textviews
        TextView header = findViewById(R.id.login_screen_header);

        //Edittexts
        final EditText ULID = findViewById(R.id.ULID_Textbox);
        final EditText password = findViewById(R.id.Password_Textbox);

        //Buttons
        final Button OK = findViewById(R.id.Login_OK_Button);

        //Toasts
        final Toast toast = Toast.makeText(this, "Too many failed attempts", Toast.LENGTH_SHORT);

        //DataBase Connection
        DatabaseConnection connection = new DatabaseConnection();
        Thread thread = new Thread(connection);
        thread.start();
        try{
            thread.join();
        }
        catch (Exception e){
            System.out.println("Connection join failed");
        }
       wrapper = new DatabaseWrapper(connection.getConnection());

        //Login for BandMembers
        OK.setOnClickListener(
                new View.OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        String ULID_retrieved = ULID.getText().toString();
                        String password_retrieved = password.getText().toString();

                        //Authenticate(ULID_retrieved,password_retrieved);
                        if(checkCredentials(ULID_retrieved,password_retrieved))
                            login(ULID_retrieved);
                        else {
                            counter++;
                            //disables login button for 60 seconds after 5 failed attempts
                            if (counter == 5) {
                                counter = 0;
                                makeToast(time);
                                OK.setEnabled(false);
                                OK.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        OK.setEnabled(true);
                                    }
                                }, time * multiplier);

                                multiplier *=2;
                                time *= multiplier;
                            }

                        }


                    }


                }

        );


    }


    //Displays disable time till next login
    public void makeToast(int time){
        Toast toast;
        time = time / 60000;
        if (time == 1)
        toast = Toast.makeText(this, "Login disabled for "+ time + " minute", Toast.LENGTH_SHORT);
        else
            toast = Toast.makeText(this, "Login disabled for "+ time + " minutes", Toast.LENGTH_SHORT);
        toast.show();
    }

    //logs in to app
    private void login(String ulid){
        boolean isFaculty = false;

        //Obtains whether user is a faculty or student
        wrapper.setMethod("getFacultyRights");
        wrapper.setUlid(ulid);
        //start validation attempt
        thread2 = new Thread(wrapper);
        thread2.start();
        try{
            thread2.join();
        }
        catch (Exception e){
            System.out.println("Validation Failed");
        }

        //retrieve the faculty rights
        isFaculty = wrapper.getFacultyRights();
        if (isFaculty)
            System.out.println("Is a faculty");
        else
            System.out.println("Is NOT a faculty");


        Intent mainScreen = new Intent(this, main_screen.class);

        //sends faculty boolean to main screen
        mainScreen.putExtra("ISFACULTY", isFaculty);


        //gets list of faculty from database
        wrapper.setMethod("getFaculty");
        Thread getFacultyThread = new Thread(wrapper);
        getFacultyThread.start();
        try{
            getFacultyThread.join();
        }
        catch (Exception e) {
            System.out.println("get faculty join failed");
        }
        faculty = new ArrayList<Faculty>();
        faculty = wrapper.getFacultyList();
        if(faculty!=null) {
            String answer = "faculty occupied:" + faculty.isEmpty();
            System.out.println(faculty.size());

        }
        else
        {
            System.out.println("faculty empty");
        }

        //sends faculty list to main screen
        mainScreen.putExtra("FACULTY", wrapper.getFacultyList());



        //attempts to get student list from data base
        wrapper.setMethod("getStudents");
        Thread getStudentThread = new Thread(wrapper);
        getStudentThread.start();
        students = new ArrayList<>();
        try{
            getStudentThread.join();
        }
        catch (Exception e){
            System.out.println("get students join failed");
        }

        //sends list of students to main screen
        mainScreen.putExtra("STUDENT", (students = wrapper.getStudentList()));

        ////attempts to get parts list from data base
        wrapper.setMethod("getParts");
        Thread getPartThread = new Thread(wrapper);
        getPartThread.start();
        try{
            getPartThread.join();
        }
        catch (Exception e){
            System.out.println("get part join failed");
        }

        //sends list of parts to main screen
        mainScreen.putExtra("PART", wrapper.getPartList());


        //attempts to get instrument list from data base
        wrapper.setMethod("getInstruments");
        Thread getInstrumentThread = new Thread(wrapper);
        getInstrumentThread.start();
        try{
            getInstrumentThread.join();
        }
        catch (Exception e){
            System.out.println("get instuments join failed");
        }

        //sends list of instruments to main screen
        mainScreen.putExtra("INSTRUMENT", wrapper.getInstrumentList());


        //attempts to get instrument list from data base
        wrapper.setMethod("getUniqueSectionUsers");
        Thread getSectionsThread = new Thread(wrapper);
        getSectionsThread.start();
        try{
            getSectionsThread.join();
        }
        catch (Exception e){
            System.out.println("get sections join failed");
        }

        mainScreen.putExtra("SECTION", wrapper.getSectionList());

        //        attempts to get categories from database
        wrapper.setMethod("getSuperCategory");
        Thread getCategoryThread = new Thread(wrapper);

        getCategoryThread.start();
        try{
            getCategoryThread.join();
        }
        catch (Exception e){
            System.out.println("get category join failed");
        }

        //sends list of categories to main screen
        ArrayList<String> categoryString = wrapper.getSuperCategoryPairsArrayList();
        System.out.println(categoryString);
        ArrayList<Category> categoryList;
        TreeBuilder buildCategories = new TreeBuilder();
        categoryList = buildCategories.buildTree(categoryString);
        System.out.println("Categories After");
        System.out.println(categoryList);
        mainScreen.putExtra("CATEGORY", categoryList);



        //go to main screen
        startActivityForResult(mainScreen,0);

    }

    //checks whether user has valid login information
    private boolean checkCredentials(String ulid, String password) {
        wrapper.setMethod("checkLogin");
        wrapper.setUlid(ulid);
        wrapper.setPassword(password);
        thread2 = new Thread(wrapper);
        thread2.start();
        try{
            thread2.join();
        }
        catch (Exception e){
            System.out.println("Login join failed");
        }


        //returns true if username and password exist in the database
        return wrapper.getValidation();


    }

    //sends data back to database on logout
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 69) {
            if (resultCode == RESULT_OK) {

                ArrayList<Faculty> faculty_return = (ArrayList<Faculty>) data.getSerializableExtra("Faculty");
                ArrayList<Student> students_return = (ArrayList<Student>) data.getSerializableExtra("Students");

                //attempts to get instrument list from data base
                wrapper.setStudentArrayList(students_return);
                wrapper.setFacultyArrayList(faculty_return);
                wrapper.setInstrumentArrayList((ArrayList<Instrument>) data.getSerializableExtra("Instruments"));
                wrapper.setPartArrayList((ArrayList<Part>) data.getSerializableExtra("Parts"));
                wrapper.setSuperCategoryArrayList((ArrayList<Category>)data.getSerializableExtra("Categories"));


                wrapper.setMethod("superUpdateUser");
                Thread setMembersThread = new Thread(wrapper);
                setMembersThread.start();
                try{
                    setMembersThread.join();
                }
                catch (Exception e){
                    System.out.println("set members join failed");
                }

                wrapper.setMethod("superUpdateItem");
                Thread setInstrumentThread = new Thread(wrapper);
                setInstrumentThread.start();
                try{
                    setInstrumentThread.join();
                }
                catch (Exception e){
                    System.out.println("set insturments join failed");
                }


            }
        }

    }



}
