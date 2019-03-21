package com.lazday.bukatokoonline.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lazday.bukatokoonline.App;
import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.data.Model.User;
import com.lazday.bukatokoonline.data.database.PrefsManager;
import com.lazday.bukatokoonline.data.Retrofit.Api;
import com.lazday.bukatokoonline.data.Retrofit.ApiInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    LinearLayout linearEdit,linearData;
    TextView txtName,txtPassword,txtEmail;
    EditText editName,editPassword,editEmail,editConfirmPassword,editCurrentPassword;
    Button btnSave;

    String user_id;
    FloatingActionButton fab;

    HashMap<String, String> sessPrefs;
    private boolean availableEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findId();

        sessPrefs = App.prefsManager.getUSerDetails();

        txtName.setText(sessPrefs.get(PrefsManager.SESS_NAME));
        txtPassword.setText(sessPrefs.get(PrefsManager.SESS_PASS));
        txtEmail.setText(sessPrefs.get(PrefsManager.SESS_EMAIL));

        editEmail.setText(sessPrefs.get(PrefsManager.SESS_EMAIL));
        editName.setText(sessPrefs.get(PrefsManager.SESS_NAME));

        user_id = sessPrefs.get(PrefsManager.SESS_ID);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if (!availableEdit) {
                    editTrue(view);

                }else{

                    editFalse(view);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editCurrentPassword.getText().toString().equals(sessPrefs.get(PrefsManager.SESS_PASS))){
                    Toast.makeText(getApplicationContext(),"Invalid Current Password",Toast.LENGTH_LONG).show();
                }else if(!editPassword.getText().toString().equals(editConfirmPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(),"password and confirm passsword are not same",Toast.LENGTH_LONG).show();
                }else if(editPassword.length() <=0 ){
                    Toast.makeText(getApplicationContext(),"fill the password",Toast.LENGTH_LONG).show();
                }else{
                    setUpdate(view, editName.getText().toString(),editEmail.getText().toString(),editPassword.getText().toString());
                }
            }
        });

        getSupportActionBar().setTitle("Profile detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setUpdate(final View view, final String name, final String email, final String pass) {
        ApiInterface apiInterface = Api.getUrl().create((ApiInterface.class));
        Call<User> call = apiInterface.updateUser(sessPrefs.get(PrefsManager.SESS_ID),name,email, pass);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    App.prefsManager.logoutUser();
                    App.prefsManager.createLoginSession(user_id,name,email,pass);
                    editFalse(view);
                }
                Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void findId(){
        linearData = findViewById(R.id.linearData);
        linearEdit = findViewById(R.id.linearEdit);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        editEmail = findViewById(R.id.editEmail);
        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editSetPassword);
        editCurrentPassword = findViewById(R.id.editCurr);
        editConfirmPassword = findViewById(R.id.editConfirm);


        btnSave = findViewById(R.id.btnSave);
    }

    private void editTrue(View view){

        linearData.setVisibility(View.GONE);
        linearEdit.setVisibility(View.VISIBLE);
        fab.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_close));
        availableEdit = true;
//        Snackbar.make(view, "editTrue", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }

    private void editFalse(View view){

        linearData.setVisibility(View.VISIBLE);
        linearEdit.setVisibility(View.GONE);
        fab.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_fab_edit));
        availableEdit = false;
//        Snackbar.make(view, "editFalse", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
