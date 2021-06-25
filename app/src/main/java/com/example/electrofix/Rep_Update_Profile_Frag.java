package com.example.electrofix;


import android.app.Activity;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Rep_Update_Profile_Frag extends Fragment {


    private Activity thiscontext;
    private EditText un, pass;
    private TextView ar, ph;
    private Button changes;
    private AwesomeValidation awesomeValidation;
    private Repairer rep;

    public Rep_Update_Profile_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rep__update__profile_, container, false);
        thiscontext = getActivity();

        rep = (Repairer) getArguments().getSerializable("repairer");

        un = view.findViewById(R.id.rep_un_edt);
        pass = view.findViewById(R.id.pwd_edt);
        ar = view.findViewById(R.id.area_tv);
        ph = view.findViewById(R.id.phone_tv);
        changes = view.findViewById(R.id.save_changes_btn);

        un.setText(rep.getUser_name());
        pass.setText(rep.getPwd());
        ar.setText(rep.getArea());
        ph.setText(rep.getPhone());

        changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = un.getText().toString();
                String ps = pass.getText().toString();

                if(name.trim().isEmpty() || ps.trim().isEmpty()) {
                    Toast.makeText(thiscontext, "Enter Fields", Toast.LENGTH_LONG).show();
                } else {
                    int id = rep.getId();

                    Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateRepairer(id, name,ps);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(thiscontext, "Repairer Updated Successfully", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent();
//                            intent.setClass(getActivity(), Rep_Main_Activity.class);
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
