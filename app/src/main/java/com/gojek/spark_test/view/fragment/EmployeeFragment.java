package com.gojek.spark_test.view.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.gojek.spark_test.ConnectingInterface;
import com.gojek.spark_test.R;
import com.gojek.spark_test.databinding.FragEmployeeBinding;
import com.gojek.spark_test.db.FavoriteDatabase;
import com.gojek.spark_test.db.FavoriteList;
import com.gojek.spark_test.model.EmployeeModel;
import com.gojek.spark_test.presenter.MainPresenter;
import com.gojek.spark_test.view.adapter.EmployeeAdapter;
import com.gojek.spark_test.view.adapter.FavoriteAdapter;

import java.util.List;

public class EmployeeFragment extends Fragment implements ConnectingInterface.view {

    MainPresenter presenter;
    FragEmployeeBinding binding;
    EmployeeAdapter adapter;
    Context context;
    public static FavoriteDatabase favoriteDatabase;

    public EmployeeFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater.from(getContext()), R.layout.frag_employee, container, false);

        favoriteDatabase = Room.databaseBuilder(getActivity(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();

        if (isNetworkAvailable()) {
            presenter = new MainPresenter(getActivity(), this);
            presenter.fromPresenter();

        } else {
            getEmpData();
        }

        return binding.getRoot();
    }

    @Override
    public void success(List<EmployeeModel> data) {

        adapter = new EmployeeAdapter(getActivity(), data);
        binding.employeeRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.employeeRV.setAdapter(adapter);

    }

    @Override
    public void error(String error) {
        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
    }

    private void getEmpData() {

        List<EmployeeModel> favoriteLists = favoriteDatabase.dao().employeelist();

        Log.e("OFF",favoriteLists.size()+"");

        if (favoriteLists.size() == 0) {
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new EmployeeAdapter(getActivity(), favoriteLists);
            binding.employeeRV.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.employeeRV.setAdapter(adapter);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}