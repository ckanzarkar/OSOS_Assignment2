package com.chaitanya.osos_assignment2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitanya.osos_assignment2.Pojo.User;
import com.chaitanya.osos_assignment2.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private final List<User> userModelList;

    RecyclerView dataRecycler;
    RecyclerViewInterface RecyclerViewInterface;
    LinearLayoutManager linearLayoutManager;

    public UsersAdapter(RecyclerViewInterface RecyclerViewInterface, List<User> userModelList, RecyclerView dataRecycler, LinearLayoutManager linearLayoutManager) {
        this.RecyclerViewInterface = RecyclerViewInterface;
        this.userModelList = userModelList;
        this.dataRecycler = dataRecycler;
        this.linearLayoutManager = linearLayoutManager;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.userdata_recycler_layout, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        User model = userModelList.get(position);

        holder.nameTextView.setText(model.getName());
        holder.usernameTextView.setText(model.getUsername());
        holder.emailTextView.setText(model.getEmail());
        holder.streetTextView.setText(model.getStreet());
        holder.cityTextView.setText(model.getCity());
        holder.zipcodeTextView.setText(model.getZipcode());



        //to get the current visible item in recyclerView based on scroll
        dataRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                User modelLocation = userModelList.get(firstVisibleItem);
                holder.addressIndexTextview.setText("Address : " + String.valueOf(firstVisibleItem + 1));

                LatLng latLng = new LatLng(Double.parseDouble(modelLocation.getLat()), Double.parseDouble(modelLocation.getLng()));
                String locationST = modelLocation.getStreet() + ", " + modelLocation.getSuite() + ", " + modelLocation.getCity() + ", " + modelLocation.getZipcode();

                RecyclerViewInterface.onRecyclerScrolled(latLng, locationST);

            }
        });
    }


    @Override
    public int getItemCount() {
        return userModelList.size();
    }


    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView usernameTextView;
        TextView emailTextView;
        TextView streetTextView;
        TextView cityTextView;
        TextView zipcodeTextView;
        TextView addressIndexTextview;


        UserViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv_name);
            usernameTextView = itemView.findViewById(R.id.tv_username);
            emailTextView = itemView.findViewById(R.id.tv_email);
            streetTextView = itemView.findViewById(R.id.tv_address_street);
            cityTextView = itemView.findViewById(R.id.tv_address_city);
            zipcodeTextView = itemView.findViewById(R.id.tv_address_zipcode);
            addressIndexTextview = itemView.findViewById(R.id.tv_address);
        }
    }
}
