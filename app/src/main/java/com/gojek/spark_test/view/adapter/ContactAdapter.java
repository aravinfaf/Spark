package com.gojek.spark_test.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.gojek.spark_test.R;
import com.gojek.spark_test.db.FavoriteList;
import com.gojek.spark_test.model.PhoneContactModel;
import com.gojek.spark_test.view.fragment.ContactFragment;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements Filterable {

    private Context mcontext;
    public List<FavoriteList> cont;
    public List<FavoriteList> mTempContactModel;

    public ContactAdapter(Context context, List<FavoriteList> items) {
        this.mcontext = context;
        this.cont = items;
        this.mTempContactModel=items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.contact_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        FavoriteList list = cont.get(position);

        int id = list.getId();
        String name = (list.getName());

        holder.title.setText(name);
        holder.phone.setText(list.getPhone());

        if (ContactFragment.favoriteDatabase.dao().isFavorite(list.getId()) == 1) {
            holder.favourite.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.favourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        holder.favourite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FavoriteList favoriteList = new FavoriteList();

                int id = list.getId();
                String image = list.getPhone();
                String name = list.getName();

                favoriteList.setId(id);
                favoriteList.setName(name);
                favoriteList.setPhone(image);

                if (ContactFragment.favoriteDatabase.dao().isFavorite(id) != 1) {
                    holder.favourite.setImageResource(R.drawable.ic_favorite);
                    ContactFragment.favoriteDatabase.dao().addData(favoriteList);
                } else {
                    holder.favourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    ContactFragment.favoriteDatabase.dao().delete(favoriteList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cont.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                mTempContactModel=new ArrayList<>();
                if(charSequence.toString().isEmpty()){
                    mTempContactModel=cont;
                }else {
                    for (FavoriteList model : cont) {
                        if (model.getName().toLowerCase().contains(charSequence.toString().toLowerCase()) || model.getPhone().contains(charSequence.toString())) {
                            mTempContactModel.add(model);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mTempContactModel;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mTempContactModel = (ArrayList<FavoriteList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void filterList(ArrayList<FavoriteList> filterdNames) {
        this.cont = filterdNames;
        Log.e("AdapterSize",filterdNames.size()+"");
        if(filterdNames.size()==0) {
            //  FancyToast.makeText(context, "No Matches Found!", FancyToast.LENGTH_LONG, FancyToast.INFO, false);
            Toast toast= Toast.makeText(mcontext, "No Matches Found!!!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView phone;
        public LinearLayout contact_select_layout;
        ImageView favourite;

        public ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(true);
            title = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.no);
            contact_select_layout = (LinearLayout) itemView.findViewById(R.id.contact_select_layout);
            favourite = (ImageView) itemView.findViewById(R.id.favourite);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}