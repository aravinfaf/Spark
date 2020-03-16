package com.gojek.spark_test.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.gojek.spark_test.dao.DAO;
import com.gojek.spark_test.model.EmployeeModel;
import com.gojek.spark_test.model.PhoneContactModel;

@Database(entities = {EmployeeModel.class,PhoneContactModel.class,FavoriteList.class},version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract DAO dao();
}
