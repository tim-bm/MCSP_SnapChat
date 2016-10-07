package com.snapchat.team2.snapchat.networkService;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.snapchat.team2.snapchat.LoginActivity;
import com.snapchat.team2.snapchat.MainActivity;
import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbService.UserDBService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xu on 2016/10/7.
 */
public class LoginService {


    private String requestURL=null;

    private RequestQueue requestQueue;
    private String result;

    public LoginService(RequestQueue requestQueue,String url){
        this.requestURL = url;
        this.requestQueue=requestQueue;
    }

    //set the display view as parameters
    public void Login(final Activity activity, final String email, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("得到的回复是:  "+response);
                        Toast.makeText(activity.getApplication(),"Connect Server successfully: "+response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //read from database
                UserDBService userDBService = new UserDBService(DBManager.getInstance(activity));
                String get_user_id = userDBService.login(email,password);
                if(get_user_id!=null){
                    System.out.println("Log in success!");
                    Toast.makeText(activity.getApplication(),"Failed in connecting server: log in from local success",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("user_id",get_user_id);
                    activity.startActivity(intent);
                }
                else{
                    System.out.println("Log in from local fail, email address or password is wrong");
                    Toast.makeText(activity.getApplication(),"Log in from local fail, email address or password is wrong",Toast.LENGTH_LONG).show();
                }


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // setting post
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("pass", password);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
