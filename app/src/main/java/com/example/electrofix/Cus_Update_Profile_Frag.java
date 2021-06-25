package com.example.electrofix;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.electrofix.R.id.phone_edt;


public class Cus_Update_Profile_Frag extends Fragment {

    private Activity thiscontext;
    private EditText un, pass;
    private TextView ar, ph;
    private Button changes;
    private AwesomeValidation awesomeValidation;
    private Customer cus;

    public Cus_Update_Profile_Frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cus__update__profile_, container, false);
        thiscontext = getActivity();

        cus = (Customer) getArguments().getSerializable("customer");

        un = view.findViewById(R.id.cus_un_edt);
        pass = view.findViewById(R.id.pwd_edt);
        ar = view.findViewById(R.id.area_tv);
        ph = view.findViewById(R.id.phone_tv);
        changes = view.findViewById(R.id.save_changes_btn);

        un.setText(cus.getUser_name());
        pass.setText(cus.getPwd());
        ar.setText(cus.getArea());
        ph.setText(cus.getPhone());

//        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
//        awesomeValidation.addValidation(thiscontext, R.id.cus_un_edt, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.uname_error);
//        awesomeValidation.addValidation(thiscontext, R.id.pwd_edt, "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$", R.string.pwd_error);


        changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = un.getText().toString();
                String ps = pass.getText().toString();

                if(name.trim().isEmpty() || ps.trim().isEmpty()) {
                    Toast.makeText(thiscontext, "Enter Fields", Toast.LENGTH_LONG).show();
                } else {
                    int id = cus.getId();


                    Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateCustomer(id, name,ps);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(thiscontext, "Customer Updated Successfully", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent();
//                            intent.setClass(getActivity(), Cus_Main_Activity.class);
//                            getActivity().startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(thiscontext, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

        return view;
    }

}
