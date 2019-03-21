package com.lazday.bukatokoonline.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lazday.bukatokoonline.App;
import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.activity.CartActivity;
import com.lazday.bukatokoonline.activity.SignupActivity;
import com.lazday.bukatokoonline.data.Model.User;
import com.lazday.bukatokoonline.data.Retrofit.Api;
import com.lazday.bukatokoonline.data.Retrofit.ApiInterface;
import com.lazday.bukatokoonline.utils.AuthState;
import com.lazday.bukatokoonline.utils.Converter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDialog {

    EditText Eemail,Epassword;

    public void showLoginDialog(final Context context, final Menu menu){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_login,null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();


        Eemail = view.findViewById(R.id.editEmail);
        Epassword = view.findViewById(R.id.editPass);




        view.findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Eemail.getText().toString();
                String password = Epassword.getText().toString();
                if(email.length() > 0 && password.length() >0){
                    if(Converter.isValidEmailId(email)){
                        Auth(email,password,context,alertDialog, menu);
                        //Log.d("BERHASIL",editEmail.getText().toString());
                    }else{
                        Toast.makeText(context, "Email not Valid",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Not Valid",Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SignupActivity.class));
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }

    public void showLoginDialog(final Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_login,null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();


        Eemail = view.findViewById(R.id.editEmail);
        Epassword = view.findViewById(R.id.editPass);




        view.findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Eemail.getText().toString();
                String password = Epassword.getText().toString();
                if(email.length() > 0 && password.length() >0){
                    if(Converter.isValidEmailId(email)){
                        Auth(email,password,context,alertDialog);
                        //Log.d("BERHASIL",editEmail.getText().toString());
                    }else{
                        Toast.makeText(context, "Email not Valid",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Not Valid",Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SignupActivity.class));
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }

    private void Auth(String email, final String pass, final Context context, final AlertDialog ad){
        ApiInterface apiInterface = Api.getUrl().create((ApiInterface.class));
        Call<User> call = apiInterface.authLogin(email, pass);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User.Data data = response.body().getData();
                    Toast.makeText(context,data.getName() + ", Logged in",Toast.LENGTH_SHORT).show();
                    //tutorial 33
                    App.prefsManager.createLoginSession(String.valueOf(data.getId()),data.getName(),
                            data.getEmail(),pass);
//                    context.finish();
                    ad.dismiss();;
                    context.startActivity(new Intent(context, CartActivity.class));
                }
                Toast.makeText(context,response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void Auth(String email, final String pass, final Context context, final AlertDialog ad,
                      final Menu menu){
        ApiInterface apiInterface = Api.getUrl().create((ApiInterface.class));
        Call<User> call = apiInterface.authLogin(email, pass);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User.Data data = response.body().getData();
                    Toast.makeText(context,data.getName() + ", Logged in",Toast.LENGTH_SHORT).show();
                    AuthState.isLoggedIn(menu);
                    //tutorial 33
                    App.prefsManager.createLoginSession(String.valueOf(data.getId()),data.getName(),
                            data.getEmail(),pass);
//                    context.finish();
                    ad.dismiss();;
                    context.startActivity(new Intent(context, CartActivity.class));
                }
                Toast.makeText(context,response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}
