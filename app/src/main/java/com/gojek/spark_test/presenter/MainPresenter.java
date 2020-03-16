package com.gojek.spark_test.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.gojek.spark_test.ConnectingInterface;
import com.gojek.spark_test.model.EmployeeModel;
import com.gojek.spark_test.model.Model;
import com.gojek.spark_test.network.ApiClient;
import com.gojek.spark_test.network.ApiService;
import com.gojek.spark_test.view.fragment.EmployeeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements ConnectingInterface.presenter {

    ConnectingInterface.view view;
    Context context;
    ProgressDialog progressDialog;

    public MainPresenter(Context context, ConnectingInterface.view view) {
        this.view = view;
        this.context=context;
    }

    @Override
    public void fromPresenter() {

        ApiService apiService= ApiClient.getClient().create(ApiService.class);
        Call<Model> call=apiService.getAll();

        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    view.success(response.body().getData());

                    class SaveTask extends AsyncTask<Void, Void, Void> {

                        @Override
                        protected Void doInBackground(Void... voids) {


                            EmployeeFragment.favoriteDatabase.dao().deleteTable();;

                            for(int i=0;i<response.body().getData().size();i++) {

                                //creating a task
                                EmployeeModel model = new EmployeeModel();
                                model.setFirstName(response.body().getData().get(i).getFirstName());
                                model.setEmail(response.body().getData().get(i).getEmail());

                                EmployeeFragment.favoriteDatabase.dao().insertemployee(model);
                                //adding to database

                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);

                            // Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();
                        }
                    }

                    SaveTask st = new SaveTask();
                    st.execute();

                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                view.error("Try After Sometime");
                progressDialog.dismiss();
            }
        });

    }
}
