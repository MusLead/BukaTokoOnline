package com.lazday.bukatokoonline.fragment.auth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lazday.bukatokoonline.App;
import com.lazday.bukatokoonline.R;
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
public class SigninFragment extends Fragment {

    EditText editEmail;
    PasswordView editPass;
    Button btnLogin;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        // Inflate the layout for this fragment

        editEmail = view.findViewById(R.id.editEmail);
        editPass = view.findViewById(R.id.editPass);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editEmail.length() > 0 && editPass.length() >0){
                    if(Converter.isValidEmailId(editEmail.getText().toString())){
                        Auth(editEmail.getText().toString(),editPass.getText().toString());
                        //Log.d("BERHASIL",editEmail.getText().toString());
                    }else{
                        Toast.makeText(getActivity(), "Email not Valid",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Not Valid",Toast.LENGTH_SHORT).show();
                }
                //Log.e("email:",editEmail.getText().toString());
                //Log.e("password:",editPass.getText().toString());

            }
        });

        return view;
    }

    private void Auth(String email, final String pass){
        ApiInterface apiInterface = Api.getUrl().create((ApiInterface.class));
        Call<User> call = apiInterface.authLogin(email, pass);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User.Data data = response.body().getData();
                    Toast.makeText(getActivity(),data.getName() + ", Logged in",Toast.LENGTH_SHORT).show();
                    //tutorial 33
                    App.prefsManager.createLoginSession(String.valueOf(data.getId()),data.getName(),
                            data.getEmail(),pass);
                    getActivity().finish();
                }
                Toast.makeText(getActivity(),response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}