package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.snapchat.team2.snapchat.Tools.ActManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {
    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private Button submit_register;

    private TextView email_address_hint;
    private TextView password_hint;
    private TextView confirm_password_hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ActManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_register);
        initViews();
        addListeners();
    }
    protected void onDestroy(){
        ActManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    private void initViews(){
        email = (EditText)findViewById(R.id.register_email);
        password = (EditText)findViewById(R.id.register_password);
        confirm_password = (EditText)findViewById(R.id.register_confirm_password);
        submit_register = (Button)findViewById(R.id.submit_register1);

        email_address_hint = (TextView)findViewById(R.id.register_email_hint);
        password_hint = (TextView)findViewById(R.id.register_password_hint);
        confirm_password_hint = (TextView)findViewById(R.id.register_confirm_password_hint);
    }

    private void addListeners(){
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    email_address_hint.setText("your e-mail address");
                    email_address_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.grey));
                    submit_register.setClickable(false);
                    return;
                }
                if(!ValidateEmail(s.toString())){
                    email_address_hint.setText("input a correct email address");
                    email_address_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    submit_register.setClickable(false);
                    return;
                }
                email_address_hint.setText("correct email address");
                email_address_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorChatlistHeaderRelease));
                submit_register.setClickable(true);
            }
        });

        confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    confirm_password_hint.setText("confirm your password");
                    confirm_password_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.grey));
                    return;
                }
                if(s.toString().equals(password.getText().toString())){
                    System.out.println(s.toString());
                    System.out.println(password.getText().toString());
                    confirm_password_hint.setText("password matches");
                    confirm_password_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorChatlistHeaderRelease));
                    return;
                }
            }
        });
        submit_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString();
                String p = password.getText().toString();
                String cp =confirm_password.getText().toString();

                if(e.equals("")||p.equals("")||cp.equals("")||!cp.equals(p)){
                    return;
                }

                if(p.equals("")){
                    Toast.makeText(RegisterActivity.this,"input a password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!p.equals(cp)){
                    Toast.makeText(RegisterActivity.this,"Sorry,password not confirmed",Toast.LENGTH_SHORT).show();
                    return;
                }

                //submit the register request to server email e password p
                Intent intent =new Intent(RegisterActivity.this,RegisterActivity2.class);
                intent.putExtra("email",e);
                intent.putExtra("password",p);
                startActivity(intent);

            }
        });
    }

    private boolean ValidateEmail(String email){
        System.out.println("validate "+email);
        String email_regx_string = getResources().getString(R.string.email_regex_string);
        Pattern p = Pattern.compile(email_regx_string,Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(email);
        return matcher.find();
    }

}
