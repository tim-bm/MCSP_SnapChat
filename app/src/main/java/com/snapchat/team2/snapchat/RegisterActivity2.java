package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.snapchat.team2.snapchat.Tools.ActManager;
import com.snapchat.team2.snapchat.networkService.LoginService;
import com.snapchat.team2.snapchat.networkService.RegisterService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity2 extends Activity {

    private EditText name;
    private EditText birth;
    private EditText phone;
    private Button register;
    private TextView name_hint;
    private TextView birth_hint;
    private TextView phone_hint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ActManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_register2);
        initViews();
        addListeners();
    }

    protected void onDestroy(){
        ActManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    private void initViews(){
        name =(EditText) findViewById(R.id.register_name);
        birth =(EditText) findViewById(R.id.register_birth);
        phone =(EditText) findViewById(R.id.register_phone);
        register = (Button)findViewById(R.id.submit_register);
        name_hint = (TextView)findViewById(R.id.register_name_hint);
        birth_hint = (TextView)findViewById(R.id.register_birth_hint);
        phone_hint = (TextView)findViewById(R.id.register_phone_hint);
    }

    private void addListeners(){
        birth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isValidDate(s.toString())){
                    birth_hint.setText("input a valid date");
                    birth_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    return;
                }
                else{
                    birth_hint.setText("input a valid date");
                    birth_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorChatlistHeaderRelease));
                    return;
                }
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                phone_hint.setText("your phone number");
                phone_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.grey));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        birth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    birth_hint.setText("input a valid date");
                    birth_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.grey));
                    return;
                }
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    phone_hint.setText("your phone number");
                    phone_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.grey));
                    return;
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String b = birth.getText().toString();
                String p = phone.getText().toString();



                if(n.equals("")){
                    n="annonymous";
                }
                if(b.equals("")){
                    birth_hint.setText("input a valid date");
                    birth_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    return;
                }

                if(!isValidDate(b)){
                    birth_hint.setText("input a valid date");
                    birth_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    return;
                }
                if(p.equals("")||((!validateAusPhone(p))&&(!validatePureNumber(p)))){
                    phone_hint.setText("phone format wrong");
                    phone_hint.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    return;
                }

                //submit register

                String e = getIntent().getStringExtra("email");
                String pass = getIntent().getStringExtra("password");
                register.setText("submitting sign up...");

                register.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorChatlistHeaderRelease));

                OperateRegister(e,pass,n,b,p,register);

            }
        });

    }

    private void OperateRegister(String email, String password,String name,String bitrh,String phone,Button register){
        RequestQueue rq = Volley.newRequestQueue(this);
        RegisterService serve = new RegisterService(rq,getResources().getString(R.string.serverAddress)+"user/register");
        serve.Register(this,email,password,name,bitrh,phone,register);
    }

    private boolean validateAusPhone(String phone){
        String regex ="/^(?:\\+?61|0)[2-478](?:[ -]?[0-9]){8}$/";
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(phone);
        return matcher.find();
    }

    private boolean validatePureNumber(String phone){
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(phone);
        return matcher.find();
    }


    private boolean isValidDate(String inDate) {

        if (inDate == null)
            return false;

        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (inDate.trim().length() != dateFormat.toPattern().length())
            return false;

        dateFormat.setLenient(false);

        try {
            //parse the inDate parameter
            dateFormat.parse(inDate.trim());
        }
        catch (ParseException pe) {
            return false;
        }
        return true;
    }
}
