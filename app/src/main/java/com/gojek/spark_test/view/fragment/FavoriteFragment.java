package com.gojek.spark_test.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gojek.spark_test.R;
import com.gojek.spark_test.databinding.FragFavoritelistBinding;
import com.gojek.spark_test.db.FavoriteList;
import com.gojek.spark_test.view.activity.MapsActivity;
import com.gojek.spark_test.view.adapter.FavoriteAdapter;

import java.util.List;

public class FavoriteFragment extends Fragment {

    Context context;
    FragFavoritelistBinding binding;
    FavoriteAdapter favoriteAdapter;

    public FavoriteFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater.from(context), R.layout.frag_favoritelist, container, false);

        binding.favoriteRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.locationFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MapsActivity.class));
            }
        });
        return binding.getRoot();
    }

    private void getFavData() {

        List<FavoriteList> favoriteLists = ContactFragment.favoriteDatabase.dao().getFavoriteData();

        if (favoriteLists.size() == 0) {
            binding.nofavTV.setVisibility(View.VISIBLE);
        } else {
            binding.nofavTV.setVisibility(View.GONE);
            favoriteAdapter = new FavoriteAdapter(favoriteLists, getActivity());
            binding.favoriteRV.setAdapter(favoriteAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavData();
    }
}
