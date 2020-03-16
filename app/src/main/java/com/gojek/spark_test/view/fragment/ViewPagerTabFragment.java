package com.gojek.spark_test.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.gojek.spark_test.R;
import com.gojek.spark_test.databinding.FragmentViewpagerTabBinding;
import com.gojek.spark_test.view.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerTabFragment extends Fragment {

    Context context;
    FragmentViewpagerTabBinding binding;

    public ViewPagerTabFragment(Context context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater.from(context),R.layout.fragment_viewpager_tab,null,false);

        binding.pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.pager.setAdapter(new ViewPagerAdapter(this,context));

        new TabLayoutMediator(binding.tabLayout,binding.pager,(tab, position) -> {
                if(position==0){
                    tab.setText("Employees");
                }else if(position==1){
                    tab.setText("Contatcs");
                }else{
                    tab.setText("Favorites");
                }
        }).attach();

        return binding.getRoot();
    }
}
