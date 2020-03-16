package com.gojek.spark_test.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.gojek.spark_test.db.FavoriteList;
import com.gojek.spark_test.model.EmployeeModel;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    public void addData(FavoriteList favoriteList);

    @Query("select * from favoritelist")
    public List<FavoriteList>getFavoriteData();

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    public int isFavorite(int id);

    @Delete
    public void delete(FavoriteList favoriteList);

    @Insert
    void insertemployee(EmployeeModel employeeModel);

    @Query("DELETE FROM employeemodel")
    public void deleteTable();

    @Query("select * from employeemodel")
    public List<EmployeeModel> employeelist();
}
