package com.lazday.bukatokoonline.fragment.auth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.activity.SignupActivity;
import com.lazday.bukatokoonline.adapter.TabAdapter;
import com.lazday.bukatokoonline.data.Model.User;
import com.lazday.bukatokoonline.data.Retrofit.Api;
import com.lazday.bukatokoonline.data.Retrofit.ApiInterface;
import com.lazday.bukatokoonline.utils.Converter;
import com.xwray.passwordview.PasswordView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    EditText editEmail, editName;
    PasswordView editPass, editConfirm;
    Button btnSignup;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        // Inflate the layout for this fragment

        editConfirm = view.findViewById(R.id.editConfirm);
        editEmail = view.findViewById(R.id.editEmail);
        editName = view.findViewById(R.id.editName);
        editPass = view.findViewById(R.id.editPass);
        btnSignup = view.findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editEmail.length() > 0 && editPass.length() >0 && editConfirm.length() >0 && editName.length()>0){
                    if(editPass.getText().toString().equals(editConfirm.getText().toString())) {
                        if(Converter.isValidEmailId(editEmail.getText().toString())){
                            //Toast.makeText(getActivity(), "OK LANJUTKAN",Toast.LENGTH_SHORT).show();
                            Auth(editName.getText().toString(),editEmail.getText().toString(),editPass.getText().toString());
                        }else{
                            Toast.makeText(getActivity(), "Email not Valid",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "please write the confirm form as the same as the password",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Not Valid, please fill all forms",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void Auth(String name, String email, String pass) {
        ApiInterface apiInterface = Api.getUrl().create((ApiInterface.class));
        Call<User> call = apiInterface.authRegister(name, email, pass);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User.Data data = response.body().getData();
                    Toast.makeText(getActivity(),data.getName() + ", please Log In",Toast.LENGTH_LONG).show();
                    SignupActivity.tabLayout.getTabAt(1).select();
                }
                Toast.makeText(getActivity(),response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}