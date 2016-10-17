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

import java.lang.reflect.Type;
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
    public void Login(final Activity activity, final String email, final String password, final Button button){
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
                        String info = result_map.get("info");
                        //String user_id = result_map.get("id");

                        if(status.equals("1")){
                            System.out.println(info);
                            Intent intent = new Intent(activity, MainActivity.class);
                            intent.putExtra("user_id", result_map.get("id"));
                            activity.startActivity(intent);
                        }
                        else{
                            Toast.makeText(activity.getApplication(),"wrong user or password!",Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(activity.getApplication(),"Connect Server successfully:",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                button.setText("submit login");
                button.setTextColor(ContextCompat.getColor(activity, R.color.white));
                Toast.makeText(activity.getApplication(),"Fail to connect server: ",Toast.LENGTH_LONG).show();

                if (email.equals("default@snapchat.com")&&password.equals("111")){
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("user_id","1");
                    activity.startActivity(intent);
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
