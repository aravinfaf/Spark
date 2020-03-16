package com.gojek.spark_test.view.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import com.gojek.spark_test.R;
import com.gojek.spark_test.databinding.FragContactBinding;
import com.gojek.spark_test.db.FavoriteDatabase;
import com.gojek.spark_test.db.FavoriteList;
import com.gojek.spark_test.view.adapter.ContactAdapter;
import java.util.ArrayList;

public class ContactFragment extends Fragment {

    Context context;
    FragContactBinding binding;
    ArrayList<FavoriteList> mContactModels = new ArrayList<>();
    ContactAdapter adapter;
    public static FavoriteDatabase favoriteDatabase;
    static int i=1;

    public ContactFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater.from(context), R.layout.frag_contact, container, false);

        checkPermission();

        favoriteDatabase = Room.databaseBuilder(getActivity(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();

        adapter = new ContactAdapter(getActivity(), mContactModels);
        binding.contactsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.contactsRV.setAdapter(adapter);

        binding.searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //adapter.getFilter().filter(charSequence);
                filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return binding.getRoot();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]
                                {android.Manifest.permission.READ_CONTACTS}
                        , 1);

            } else {
                getContacts();
            }
        } else {
            getContacts();
        }
    }

    void getContacts() {

            ArrayList<String> mContactList = new ArrayList<>();
            ContentResolver cr = context.getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));

                            FavoriteList contactModel = new FavoriteList();
                            contactModel.setId(i++);
                            contactModel.setName(name);
                            contactModel.setPhone(phoneNo);

                            Log.e("I",i+"");

                            if (!mContactList.contains(phoneNo)) {
                                mContactList.add(contactModel.getPhone().trim());
                                mContactModels.add(contactModel);
                            }
                        }
                        pCur.close();
                    }
                }
            }
            if (cur != null) {
                cur.close();
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts();
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(context, "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<FavoriteList> filterdNames = new ArrayList<FavoriteList>();

        //looping through existing elements

        for (FavoriteList s : mContactModels) {
            //if the existing elements contains the search input
            if (s.getName().toLowerCase().contains(text.toLowerCase()) || s.getPhone().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }
        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

}
