package com.gojek.spark_test;

import com.gojek.spark_test.model.EmployeeModel;
import java.util.List;

public interface ConnectingInterface {

    interface presenter {
        void fromPresenter();
    }
    interface view {
        void success(List<EmployeeModel> data);
        void error(String error);
    }
}
