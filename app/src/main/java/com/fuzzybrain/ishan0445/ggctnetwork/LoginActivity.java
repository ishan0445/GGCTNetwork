package com.fuzzybrain.ishan0445.ggctnetwork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class LoginActivity extends AppCompatActivity {

    TextView tv;
    EditText uname,pass;
    Button bt;
    TextInputLayout tilUname;
    TextInputLayout tilPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        QBUser user = new QBUser();

        uname = (EditText) findViewById(R.id.etUsernameLogin);
        pass = (EditText) findViewById(R.id.etPasswordLogin);
        tv = (TextView) findViewById(R.id.tvLoginPage);
        bt = (Button) findViewById(R.id.btLogin);
        tilUname = (TextInputLayout) findViewById(R.id.tilUname);
        tilPass = (TextInputLayout) findViewById(R.id.tilPass);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String unm,pwd;
                unm = uname.getText().toString().toLowerCase();
                pwd = pass.getText().toString();

                QBUser user = new QBUser(unm,pwd);
                QBUsers.signIn(user, new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {

                        // We need an Editor object to make preference changes.
                        // All objects are from android.context.Context
                        SharedPreferences login = getSharedPreferences("Login", 0);
                        SharedPreferences.Editor editor = login.edit();
                        editor.putString("uname", unm);
                        editor.putString("pass", pwd);
                        // Commit the edits!
                        editor.apply();

                        Intent in = new Intent();
                        startActivity(in);
                    }

                    @Override
                    public void onError(QBResponseException e) {

                    }
                });

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(in);
                finish();
            }
        });

    }
}

