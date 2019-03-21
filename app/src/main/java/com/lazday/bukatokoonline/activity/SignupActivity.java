package com.lazday.bukatokoonline.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.adapter.TabAdapter;
import com.lazday.bukatokoonline.fragment.auth.SigninFragment;
import com.lazday.bukatokoonline.fragment.auth.SignupFragment;

public class SignupActivity extends AppCompatActivity {

    public static TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        addTab(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setTitle("Welcome To AghaOlshop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addTab(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignupFragment(),"Register");
        adapter.addFragment(new SigninFragment(),"Log In");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
