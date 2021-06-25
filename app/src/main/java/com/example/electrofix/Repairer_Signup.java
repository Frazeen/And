package com.example.electrofix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.widget.ArrayAdapter;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repairer_Signup extends AppCompatActivity {

    private Spinner area, skill;
    private EditText un, phone, address, pwd, code, cnic, reg_no, fare;
    private Button singup, get_var_code;
    private AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    String code_sent, location_name, skill_name;
    long location_id = 0, skill_id = 0;

    private static Retrofit retrofit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairer__signup);

        addItemsOnAreaSpinner();
        addItemsOnSkillSpinner();

        un = findViewById(R.id.rep_un_edt);
        area = findViewById(R.id.area_sp);
        address = findViewById(R.id.address_edt);
        reg_no = findViewById(R.id.reg_no_edt);
        cnic = findViewById(R.id.cnic_edt);
        fare = findViewById(R.id.base_fare);
        skill = findViewById(R.id.skill_sp);
        pwd = findViewById(R.id.pwd_edt);
        phone = findViewById(R.id.phone_edt);
        get_var_code = findViewById(R.id.get_var_btn);
        code = findViewById(R.id.code_edt);
        singup = findViewById(R.id.rep_reg_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location_name = parent.getItemAtPosition(position).toString().toLowerCase();
                location_id = id;
                if (location_id != 0) {
                    Toast.makeText(parent.getContext(), location_name, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(parent.getContext(), location_name, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        skill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                skill_name = parent.getItemAtPosition(position).toString().toLowerCase();
                skill_id = id;
                if (skill_id != 0) {
                    Toast.makeText(parent.getContext(), skill_name, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(parent.getContext(), skill_name, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.rep_un_edt, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.uname_error);
        awesomeValidation.addValidation(this, R.id.phone_edt, "^[0-9]{10,10}$", R.string.phone_error);
        awesomeValidation.addValidation(this, R.id.reg_no_edt, "^[0-9]{5,15}$", R.string.reg_error);
        awesomeValidation.addValidation(this, R.id.cnic_edt, "^[0-9]{13,13}$", R.string.cnic_error);
        awesomeValidation.addValidation(this, R.id.base_fare, "^[0-9]{3,4}$", R.string.base_fare_error);
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

            TextView error1 = (TextView) area.getSelectedView();
            if (location_id == 0) {
                error1.setError("");
                error1.setTextColor(Color.RED);
                error1.setText("Select Area");
                return;
            }

            TextView error2 = (TextView) skill.getSelectedView();
            if (skill_id == 0) {
                error2.setError("");
                error2.setTextColor(Color.RED);
                error2.setText("Select Skill");
                return;
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

            TextView error1 = (TextView) area.getSelectedView();
            if (location_id == 0) {
                error1.setError("");
                error1.setTextColor(Color.RED);
                error1.setText("Select Area");
            }

            TextView error2 = (TextView) skill.getSelectedView();
            if (skill_id == 0) {
                error2.setError("");
                error2.setTextColor(Color.RED);
                error2.setText("Select Skill");
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
            String reg = reg_no.getText().toString();
            String cn = cnic.getText().toString();
            String bf = fare.getText().toString();
            String pass = pwd.getText().toString();
            String mobile = phone.getText().toString();
            String status = "pending";

            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().registerRepairer(user_name, location_name, ad, reg, cn, bf, skill_name, pass, mobile, status);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (!response.isSuccessful()) {
                            String s = response.body().string();
                            Toast.makeText(Repairer_Signup.this, s, Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getApplicationContext(), "Sign Up Successfully", Toast.LENGTH_LONG).show();
                        Intent obj = new Intent(Repairer_Signup.this, Login.class);
                        startActivity(obj);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(Repairer_Signup.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
                            String user_name = un.getText().toString();
                            String ad = address.getText().toString();
                            String reg = reg_no.getText().toString();
                            String cn = cnic.getText().toString();
                            String bf = fare.getText().toString();
                            String pass = pwd.getText().toString();
                            String mobile = phone.getText().toString();
                            String status = "p";

                            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().registerRepairer(user_name, location_name, ad, reg, cn, bf, skill_name, pass, mobile, status);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        if (!response.isSuccessful()) {
                                            String s = response.body().string();
                                            Toast.makeText(Repairer_Signup.this, s, Toast.LENGTH_LONG).show();
                                        } else {
                                            String s = response.body().string();
                                            if (s.equals("ok")) {
                                                Toast.makeText(getApplicationContext(), "Sign Up Successfully", Toast.LENGTH_LONG).show();
                                                Intent obj = new Intent(Repairer_Signup.this, Login.class);
                                                startActivity(obj);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(Repairer_Signup.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });


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
        area = findViewById(R.id.area_sp);
        List<String> area_list = new ArrayList<String>();
        area_list.add("Select Area");
        area_list.add("Hafeez Center");
        area_list.add("Hall Road");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.area_spinner_item, area_list);
        dataAdapter.setDropDownViewResource(R.layout.area_spinner_item);
        area.setAdapter(dataAdapter);
    }

    public void addItemsOnSkillSpinner() {
        skill = findViewById(R.id.skill_sp);
        List<String> skill_list = new ArrayList<String>();
        skill_list.add("Select Skill");
        skill_list.add("Mobile Repairer");
        skill_list.add("Computer Repairer");
        skill_list.add("Other Electronics");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.skill_spinner_item, skill_list);
        dataAdapter.setDropDownViewResource(R.layout.skill_spinner_item);
        skill.setAdapter(dataAdapter);
    }
}
