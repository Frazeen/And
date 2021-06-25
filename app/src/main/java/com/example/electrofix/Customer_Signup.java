package com.example.electrofix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Customer_Signup extends AppCompatActivity {

    private Spinner area_sp;
    private EditText un, phone, address, pwd, code;
    private Button singup, get_var_code;
    private AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    String code_sent, location;
    long location_id = 0;

    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__signup);

        addItemsOnAreaSpinner();

        un = findViewById(R.id.cus_un_edt);
        area_sp = findViewById(R.id.area_sp);
        phone = findViewById(R.id.phone_edt);
        get_var_code = findViewById(R.id.get_var_btn);
        code = findViewById(R.id.code_edt);
        address = findViewById(R.id.address_edt);
        pwd = findViewById(R.id.pwd_edt);
        singup = findViewById(R.id.cus_reg_btn);

        firebaseAuth = FirebaseAuth.getInstance();


        area_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = parent.getItemAtPosition(position).toString().toLowerCase();
                location_id = id;
                if (location_id != 0) {
                    Toast.makeText(parent.getContext(), location, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(parent.getContext(), location, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.cus_un_edt, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.uname_error);
        awesomeValidation.addValidation(this, R.id.phone_edt, "^[0-9]{10,10}$", R.string.phone_error);
        awesomeValidation.addValidation(this, R.id.pwd_edt, "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$", R.string.pwd_error);


        get_var_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerificationCode();
            }
        });

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    public void getVerificationCode() {

        if (awesomeValidation.validate()) {

            TextView error = (TextView) area_sp.getSelectedView();
            if (location_id == 0) {
                error.setError("");
                error.setTextColor(Color.RED);
                error.setText("Select Area");
            }

            String ad = address.getText().toString();
            if (ad.trim().isEmpty()) {
                address.setError("address is required!");
                address.requestFocus();
                return;
            }


            String ph = "+92" + phone.getText().toString();
            Toast.makeText(getApplicationContext(), "getting Verification Code:" + ph, Toast.LENGTH_LONG).show();
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                    ph,        // Phone number to verify
//                    60,                 // Timeout duration
//                    TimeUnit.SECONDS,   // Unit of timeout
//                    this,               // Activity (for callback binding)
//                    mCallbacks);        // OnVerificationStateChangedCallbacks
        }
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText(getApplicationContext(), "Verification Completed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Toast.makeText(getApplicationContext(), "onCodeSent", Toast.LENGTH_SHORT).show();
            super.onCodeSent(s, forceResendingToken);
            code_sent = s;
            Toast.makeText(getApplicationContext(), "code is sent", Toast.LENGTH_SHORT).show();
        }
    };

    public void register() {
        if (awesomeValidation.validate()) {

            TextView error = (TextView) area_sp.getSelectedView();
            if (location_id == 0) {
                error.setError("");
                error.setTextColor(Color.RED);
                error.setText("Select Area");
            }

            String ad = address.getText().toString();
            if (ad.trim().isEmpty()) {
                address.setError("address is required!");
                address.requestFocus();
                return;
            }

            String cd = code.getText().toString();
            if (cd.trim().isEmpty()) {
                code.setError("code is required!");
                code.requestFocus();
                return;
            }

            if (cd.length() < 6) {
                code.setError("enter a valid code");
                code.requestFocus();
                return;
            }
            //Toast.makeText(getApplicationContext(), "register", Toast.LENGTH_SHORT).show();
//            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code_sent, cd);
//            signInWithPhoneAuthCredential(credential);

            String user_name = un.getText().toString();
            String pass = pwd.getText().toString();
            String mobile = phone.getText().toString();
            String status = "active";

            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().registerCustomer(user_name, location, ad, pass, mobile, status);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (!response.isSuccessful()) {
                            String s = response.body().string();
                            Toast.makeText(Customer_Signup.this, s, Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(Customer_Signup.this, response.body().string(), Toast.LENGTH_LONG).show();
                        Intent obj = new Intent(Customer_Signup.this, Login.class);
                        startActivity(obj);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(Customer_Signup.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "verification successfull", Toast.LENGTH_LONG).show();
//                            String user_name = un.getText().toString();
//                            String ad = address.getText().toString();
//                            String pass = pwd.getText().toString();
//                            String mobile = phone.getText().toString();
//                            String status = "active";
//
//                            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().registerCustomer(user_name, location, ad, pass, mobile, status);
//                            call.enqueue(new Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                    try {
//                                        if (!response.isSuccessful()) {
//                                            String s = response.body().string();
//                                            Toast.makeText(Customer_Signup.this, s, Toast.LENGTH_LONG).show();
//                                        }
//                                        Toast.makeText(Customer_Signup.this, response.body().string(), Toast.LENGTH_LONG).show();
//                            Intent obj = new Intent(Customer_Signup.this, Login.class);
//                            startActivity(obj);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                    Toast.makeText(Customer_Signup.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//                            });


//                            Intent obj = new Intent(Customer_Signup.this, Login.class);
//                            startActivity(obj);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                code.setError("Invalid Code");
                                code.requestFocus();
                                Toast.makeText(getApplicationContext(), "Incorrect Verification Code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    public void addItemsOnAreaSpinner() {
        area_sp = findViewById(R.id.area_sp);
        List<String> area_list = new ArrayList<String>();
        area_list.add("Select Area");
        area_list.add("Johar Town");
        area_list.add("Bahria Town");
        area_list.add("Anarkali");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.area_spinner_item, area_list);
        dataAdapter.setDropDownViewResource(R.layout.area_spinner_item);
        area_sp.setAdapter(dataAdapter);
    }
}
