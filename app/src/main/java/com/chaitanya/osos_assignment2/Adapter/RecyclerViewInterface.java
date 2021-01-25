package com.chaitanya.osos_assignment2.Adapter;

import com.google.android.gms.maps.model.LatLng;

public interface RecyclerViewInterface {
    void onRecyclerScrolled(LatLng position, String location);
}
