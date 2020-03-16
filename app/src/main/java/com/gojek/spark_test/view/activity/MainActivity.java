package com.gojek.spark_test.view.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.gojek.spark_test.R;
import com.gojek.spark_test.databinding.ActivityMain2Binding;
import com.gojek.spark_test.view.fragment.ViewPagerTabFragment;

public class MainActivity extends FragmentActivity {

    ActivityMain2Binding main2Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main2Binding= DataBindingUtil.setContentView(this,R.layout.activity_main2);
        getSupportFragmentManager().beginTransaction().replace(main2Binding.container.getId(),new ViewPagerTabFragment(this) ).commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
