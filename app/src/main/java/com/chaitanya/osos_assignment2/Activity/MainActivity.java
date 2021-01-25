package com.chaitanya.osos_assignment2.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chaitanya.osos_assignment2.Adapter.RecyclerViewInterface;
import com.chaitanya.osos_assignment2.Adapter.UsersAdapter;
import com.chaitanya.osos_assignment2.Pojo.User;
import com.chaitanya.osos_assignment2.R;
import com.chaitanya.osos_assignment2.Retrofit.Retrofit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, RecyclerViewInterface {

    LinearLayoutManager linearLayoutManager;
    RecyclerView dataRecycler;
    UsersAdapter userAdapter;
    List<User> userModelList;

    TextView infoText, retryText;

    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);


        infoText = findViewById(R.id.tv_info);
        retryText = findViewById(R.id.tv_retry);
        //Initializing RecyclerView , LinearLayoutManager and List

        userModelList = new ArrayList<>();

        dataRecycler = findViewById(R.id.recyclerview);
        dataRecycler.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        dataRecycler.setLayoutManager(linearLayoutManager);

        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(dataRecycler);


        loadRetrofitRecycler();


    }

    private void loadRetrofitRecycler() {

        retryText.setVisibility(View.GONE);

        Call<List<User>> call = Retrofit.getInstance().getMyApiService().getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {

                infoText.setVisibility(View.GONE);

                List<User> userList = response.body();
                assert userList != null;

                for (int i = 0; i < userList.size(); i++) {

                    User userModel = new User(
                            userList.get(i).getName(),
                            userList.get(i).getUsername(),
                            userList.get(i).getEmail(),
                            userList.get(i).getAddress().getStreet(),
                            userList.get(i).getAddress().getSuite(),
                            userList.get(i).getAddress().getCity(),
                            userList.get(i).getAddress().getZipcode(),
                            userList.get(i).getAddress().getGeo().getLat(),
                            userList.get(i).getAddress().getGeo().getLng()
                    );

                    userModelList.add(userModel);
                }

                userAdapter = new UsersAdapter(MainActivity.this,userModelList, dataRecycler, linearLayoutManager);
                dataRecycler.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();

            }


            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                //Its from a dependency I created to show toasts with color & icon


                retryText.setVisibility(View.VISIBLE);
                retryText.setOnClickListener(v -> loadRetrofitRecycler());

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    public void onRecyclerScrolled(LatLng latlng, String locationST) {

        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.addMarker(new MarkerOptions().position(latlng).title(locationST));

    }
}