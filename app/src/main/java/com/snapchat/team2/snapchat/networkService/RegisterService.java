package com.snapchat.team2.snapchat.networkService;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snapchat.team2.snapchat.MainActivity;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbService.UserDBService;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xu on 2016/10/9.
 */
public class RegisterService {



    private String requestURL=null;

    private RequestQueue requestQueue;
    private String result;

    public RegisterService(RequestQueue requestQueue,String url){
        this.requestURL = url;
        this.requestQueue=requestQueue;
    }

    //set the display view as parameters
    public void Register(final Activity activity, final String email, final String password, final String name, final String birth, final String phone,final Button button){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("得到的回复是:  "+response);

                        //deal with response
                        Gson gson = new Gson();
                        Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
                        Map<String,String> result_map = gson.fromJson(response, stringStringMap);

                        String status = result_map.get("status");
                        String id = result_map.get("id");
                        //String user_id = result_map.get("id");

                        if(status.equals("1")){
                            //System.out.println(info);
                            System.out.println("注册成功");
                            Intent intent = new Intent(activity, MainActivity.class);

                            intent.putExtra("user_id", result_map.get("id"));
                            activity.startActivity(intent);
                        }
                        else{
                            System.out.println("success to connect the data but register fail");
                            button.setText("Sign up");
                            button.setTextColor(ContextCompat.getColor(activity, R.color.white));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //read from database
                Toast.makeText(activity.getApplication(),"Fail to connect to the server",Toast.LENGTH_LONG).show();
                button.setText("Sign up");
                button.setTextColor(ContextCompat.getColor(activity, R.color.white));
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // setting post
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("pass", password);
                params.put("email", email);
                params.put("birth", birth);
                params.put("phone", phone);
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
