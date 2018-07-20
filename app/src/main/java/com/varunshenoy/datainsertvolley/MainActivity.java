package com.varunshenoy.datainsertvolley;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText Name, Email;
    AlertDialog.Builder builder;

    private String server_url="http://192.168.1.105/updateinfo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.bn);
        Name=(EditText)findViewById(R.id.name);
        Email=(EditText)findViewById(R.id.email);
        builder=new AlertDialog.Builder(MainActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name,email;
                name=Name.getText().toString().trim();
                email=Email.getText().toString().trim();

                StringRequest stringRequest=new StringRequest(Request.Method.POST, server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                builder.setTitle("Server Response");
                                builder.setMessage("Response: "+response);
                                builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Name.setText("");
                                        Email.setText("");
                                        Name.requestFocus();
                                        return;
                                    }
                                });

                                AlertDialog alertDialog=builder.create();
                                alertDialog.show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "Error..", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params=new HashMap<String, String>();

                        params.put("name",name);
                        params.put("email",email);
                        return params;
                    }
                };

                MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }
}
