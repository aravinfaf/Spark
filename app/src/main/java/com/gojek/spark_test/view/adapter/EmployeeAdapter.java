package com.gojek.spark_test.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gojek.spark_test.R;
import com.gojek.spark_test.model.EmployeeModel;
import java.util.List;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    Context context;
    List<EmployeeModel> data;

    public EmployeeAdapter(Context context, List<EmployeeModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.employee_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){

            RequestOptions options = new RequestOptions();
            options.centerCrop();

            Glide.with(context)
                    .load(data.get(position).getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .apply(options)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .into(holder.avatarIV);

            holder.nameTV.setText(""+data.get(position).getFirstName());
            holder.mailTV.setText(""+data.get(position).getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView avatarIV;
        TextView nameTV,mailTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            avatarIV=itemView.findViewById(R.id.avatarIV);
            nameTV=itemView.findViewById(R.id.nameTV);
            mailTV=itemView.findViewById(R.id.mailTV);
        }
    }
}
