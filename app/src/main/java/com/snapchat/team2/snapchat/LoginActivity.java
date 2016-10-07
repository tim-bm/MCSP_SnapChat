package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.snapchat.team2.snapchat.Tools.ActManager;
import com.snapchat.team2.snapchat.networkService.LoginService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity {
    private String user_id = null;
    private SharedPreferences sharedPreferences = null;

    private EditText identity;
    private EditText password;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ActManager.getAppManager().addActivity(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initViews();
        setListeners();
        //sharedPreferences = getApplicationContext().getSharedPreferences("snapchat_user",MODE_PRIVATE);
        //let it login now
       /* Editor editor =sharedPreferences.edit();
        editor.putString("user_id","1");
        editor.commit();*/

    }

    private void initViews(){
        this.identity = (EditText)findViewById(R.id.login_identity);
        this.password = (EditText)findViewById(R.id.login_password);
        this.submit = (Button)findViewById(R.id.submit_login);
    }

    private void setListeners(){
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    submit.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                }
                else{
                    submit.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorChatlistHeaderRelease));
                }
            }
        });

        identity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    submit.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                }
                else{
                    submit.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorChatlistHeaderRelease));
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = identity.getText().toString();
                String pwd =password.getText().toString();
                if(email.equals("")||password.equals("")){
                    return;
                }
                if(!ValidateEmail(email)){

                    Toast.makeText(LoginActivity.this,"This is not a email",Toast.LENGTH_SHORT).show();
                    return;
                }
                operateLogin(email,pwd);
            }
        });
    }


    private boolean ValidateEmail(String email){
        String email_regx_string = getResources().getString(R.string.email_regex_string);
        Pattern p = Pattern.compile(email_regx_string,Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(email);
        return matcher.find();
    }

    private void operateLogin(String email,String pwd){
        RequestQueue rq = Volley.newRequestQueue(this);
        LoginService lgserve = new LoginService(rq,getResources().getString(R.string.serverAddress)+"user/login");
        lgserve.Login(this,email,pwd);
    }

    @Override
    protected void onDestroy(){



        ActManager.getAppManager().finishActivity(this);
        super.onDestroy();

    }




}
