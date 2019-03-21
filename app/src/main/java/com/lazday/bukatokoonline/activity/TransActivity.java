package com.lazday.bukatokoonline.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.adapter.TabAdapter;
import com.lazday.bukatokoonline.fragment.trans.PaidFragment;
import com.lazday.bukatokoonline.fragment.trans.UnpaidFragment;

public class TransActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        addTab(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setTitle("Transfer Proof");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addTab(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new PaidFragment(),"Paid");
        adapter.addFragment(new UnpaidFragment(),"Unpaid");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
