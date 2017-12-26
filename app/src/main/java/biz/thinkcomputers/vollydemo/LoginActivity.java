package biz.thinkcomputers.vollydemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{

    private EditText mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    String server_url = "http://shdc.icampus360.in/webservices/webservice.asmx?op=login";
    AlertDialog.Builder builder;
    String email,password,name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        builder = new AlertDialog.Builder(LoginActivity.this);

         mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 email = mEmailView.getText().toString();
                 password = mPasswordView.getText().toString();

                 if(email.equals("")||password.equals("")){
                     builder.setTitle("Please inter username and password");
                 }else{

                     StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {

                             try {
                                 JSONArray jsonArray = new JSONArray(response);

                                 for(int i = 0; i<jsonArray.length();i++){
                                     JSONObject jsonObject = jsonArray.getJSONObject(i);
                                     name = jsonObject.getString("Usr_Name");

                                 }
                                 if(name.length()>0){
                                     Intent intent = new Intent(LoginActivity.this,DashBoardActivity.class);
                                     intent.putExtra("abc",name);
                                     startActivity(intent);
                                 }else{
                                     builder.setTitle("Login Error...");
                                     builder.show();
                                 }

                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }

                         }
                     }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {
                             Toast.makeText(LoginActivity.this,"Error..",Toast.LENGTH_SHORT).show();
                             error.printStackTrace();
                         }
                     }){

                         @Override
                         protected Map<String, String> getParams() throws AuthFailureError {
                             Map<String,String> param = new HashMap<>();
                             param.put("email_Id",email);
                             param.put("password",password);
                         return param;
                     }

                     };

                     MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
                 }
             }
         });

    }

}

