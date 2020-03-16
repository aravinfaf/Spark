package com.gojek.spark_test.view.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.gojek.spark_test.view.fragment.ContactFragment;
import com.gojek.spark_test.view.fragment.EmployeeFragment;
import com.gojek.spark_test.view.fragment.FavoriteFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    Context context;

    public ViewPagerAdapter(@NonNull Fragment fragment, Context context) {
        super(fragment);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(position==0)
            return new EmployeeFragment(context);
        else if(position==1)
            return new ContactFragment(context);
        else
            return new FavoriteFragment(context);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
