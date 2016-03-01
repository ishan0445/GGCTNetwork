package com.fuzzybrain.ishan0445.ggctnetwork;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class SignUpActivity extends AppCompatActivity{

    Button submit;
    EditText fullName,rollNo,userName,email,pass,rePass;
    Spinner branch;
    Spinner semester;
    String bra,sem;
    ProgressBar pb;
    TextView tv;
    TextInputLayout tilFullName;
    TextInputLayout tilRollNumb;
    TextInputLayout tilUserName;
    TextInputLayout tilEmail;
    TextInputLayout tilPass;
    TextInputLayout tilRetypePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        submit = (Button) findViewById(R.id.btSubmit);
        fullName = (EditText) findViewById(R.id.etName);
        rollNo = (EditText) findViewById(R.id.etRoll);
        branch = (Spinner) findViewById(R.id.spnBranch);

        semester = (Spinner) findViewById(R.id.spnSemester);
        userName = (EditText) findViewById(R.id.etUName);
        email = (EditText) findViewById(R.id.etEmail);
        pass = (EditText) findViewById(R.id.etPassword);
        rePass = (EditText) findViewById(R.id.etRePassword);
       // sem = (EditText) findViewById(R.id.etSem);
        pb = (ProgressBar) findViewById(R.id.pbSignup);
        tv = (TextView) findViewById(R.id.tvLoginPage);
        tilFullName = (TextInputLayout) findViewById(R.id.tilFullName);
        tilRollNumb =(TextInputLayout) findViewById(R.id.tilRollNm);
        tilUserName = (TextInputLayout) findViewById(R.id.tilUserNm);
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        tilPass = (TextInputLayout) findViewById(R.id.tilPass);
        tilRetypePass = (TextInputLayout) findViewById(R.id.tilRePassword);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(in);
                finish();
            }
        });


        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        bra = "CSE";
                        break;
                    case 1:
                        bra = "ECE";
                        break;
                    case 2:
                        bra = "ME";
                        break;
                    case 3:
                        bra = "EX";
                        break;
                    case 4:
                        bra = "CE";
                        break;
                    case 5:
                        bra = "IT";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bra = "CSE";
            }
        });
        /*
        *
        * Array Adapter Of semester
        *
        * */
        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sem = "1";
                        break;
                    case 1:
                        sem = "2";
                        break;
                    case 2:
                        sem = "3";
                        break;
                    case 3:
                        sem = "4";
                        break;
                    case 4:
                        sem = "5";
                        break;
                    case 5:
                        sem = "6";
                        break;
                    case 6:
                        sem = "7";
                        break;
                    case 7:
                        sem = "8";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sem = "1";
            }
        });


        //Array Adapter Branch

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.branch_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch.setAdapter(adapter);

        //Array Adapter semester

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.semester_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester.setAdapter(adapter1);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int semm;
                String nm, roll, un, em, pwd, rePwd;
                nm = fullName.getText().toString();
                roll = rollNo.getText().toString();
                un = userName.getText().toString();
                em = email.getText().toString();
                pwd = pass.getText().toString();
                rePwd = rePass.getText().toString();
                semm = Integer.parseInt(sem);

                //Progress Bar
                pb.setVisibility(View.VISIBLE);

                UserSignUpTask(un, nm, em, pwd, semm,bra,roll);

            }
        });

    }



    void UserSignUpTask(final String mUname, final String mName, String mEmail, final String mPassword, final int mSem, final String bra, final String roll) {

        final QBUser qbUser = new QBUser(mUname,mPassword,mEmail);
        final QBCustomObject obj = new QBCustomObject("UserInfo");

        QBUsers.signUp(qbUser, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                // success


                QBUsers.signIn(mUname, mPassword, new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        obj.put("branch", bra);
                        obj.putInteger("semister", mSem);
                        obj.put("rollNumber", roll);
                        obj.put("fullName", mName);

                        QBCustomObjects.createObject(obj, new QBEntityCallback<QBCustomObject>() {
                            @Override
                            public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {

                                // We need an Editor object to make preference changes.
                                // All objects are from android.context.Context
                                SharedPreferences login = getSharedPreferences("Login", 0);
                                SharedPreferences.Editor editor = login.edit();
                                editor.putString("uname", mUname);
                                editor.putString("pass", mPassword);
                                // Commit the edits!
                                editor.apply();

                                Intent in = new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(in);
                                finish();
                            }

                            @Override
                            public void onError(QBResponseException e) {

                            }
                        });
                    }

                    @Override
                    public void onError(QBResponseException e) {

                    }
                });

                pb.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onError(QBResponseException error) {
                // error
                pb.setVisibility(View.INVISIBLE);
            }
        });

    }


}
