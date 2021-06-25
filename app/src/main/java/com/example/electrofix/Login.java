package com.example.electrofix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private List<Customer> cus_list;
    private List<Repairer> rep_list;
    private Spinner role;
    private EditText phone, pwd;
    private Button login, register_cus, register_rep;
    private AwesomeValidation awesomeValidation;
    String role_name;
    long role_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addItemsOnRoleSpinner();

        phone = findViewById(R.id.phone);
        role = findViewById(R.id.role_sp);
        pwd = findViewById(R.id.pwd);

        login = findViewById(R.id.login);
        register_cus = findViewById(R.id.register_cus_btn);
        register_rep = findViewById(R.id.register_rep_btn);

        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role_name = parent.getItemAtPosition(position).toString().toLowerCase();
                role_id = id;
                if (role_id != 0) {
                    Toast.makeText(parent.getContext(), role_name, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(parent.getContext(), role_name, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.phone, "^[0-9]{10,10}$", R.string.phone_error);
        awesomeValidation.addValidation(this, R.id.pwd, "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$", R.string.pwd_error);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_login();
            }
        });

        register_cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCusRegistration();
            }
        });
        register_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRepRegistration();
            }
        });

    }

    public void check_login() {
        if (awesomeValidation.validate()) {
            TextView error1 = (TextView) role.getSelectedView();
            if (role_id == 0) {
                error1.setError("");
                error1.setTextColor(Color.RED);
                error1.setText("Select User Type");
                return;
            }
            if (role_id == 1) {

                String ph = phone.getText().toString();
                String pass = pwd.getText().toString();

                Call<List<Customer>> call = RetrofitClient.getInstance().getApi().loginCus(ph, pass);
                call.enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        if (response.body().size() == 0) {
                            Toast.makeText(getApplicationContext(), "Customer not found", Toast.LENGTH_LONG).show();
                        } else {
                            cus_list = response.body();
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                            Intent obj1 = new Intent(Login.this, Cus_Main_Activity.class);
                            obj1.putExtra("customer", cus_list.get(0));
                            startActivity(obj1);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (role_id == 2) {

                String ph = phone.getText().toString();
                String pass = pwd.getText().toString();

                Call<List<Repairer>> call = RetrofitClient.getInstance().getApi().loginRep(ph, pass);
                call.enqueue(new Callback<List<Repairer>>() {
                    @Override
                    public void onResponse(Call<List<Repairer>> call, Response<List<Repairer>> response) {
                        if (response.body().size() == 0) {
                            Toast.makeText(getApplicationContext(), "Repairer not found", Toast.LENGTH_LONG).show();
                        } else {
                            rep_list = response.body();
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                            Intent obj2 = new Intent(Login.this, Rep_Main_Activity.class);
                            obj2.putExtra("repairer", rep_list.get(0));
                            startActivity(obj2);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Repairer>> call, Throwable t) {
                        Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }


    public void goToCusRegistration() {
        Intent obj = new Intent(Login.this, Customer_Signup.class);
        startActivity(obj);
    }

    public void goToRepRegistration() {
        Intent obj = new Intent(Login.this, Repairer_Signup.class);
        startActivity(obj);
    }

    public void addItemsOnRoleSpinner() {
        role = findViewById(R.id.role_sp);
        List<String> skill_list = new ArrayList<String>();
        skill_list.add("Select User Type");
        skill_list.add("Customer");
        skill_list.add("Repairer");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.role_spinner_item, skill_list);
        dataAdapter.setDropDownViewResource(R.layout.role_spinner_item);
        role.setAdapter(dataAdapter);
    }


}
