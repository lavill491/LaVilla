package edu.csulb.cecs.lavilla;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Location;

public class LocationDataHandler extends ViewModel {
    private DatabaseReference database;

    public LocationDataHandler(Context context){

        this.database = FirebaseDatabase.getInstance().getReference();
    }

    public void setLocation(String loc_id, Location loc){
        this.database.child("locations").child(loc_id).setValue(loc);
    }

    public void getLocation(String rest_id, final FirebaseCallBack firebaseCallBack){
        DatabaseReference ref = database.child("locations").child(rest_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Location loc = dataSnapshot.getValue(Location.class);
                firebaseCallBack.getLocationMethod(loc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAllLocations(final FirebaseCallBack firebaseCallBack){
        final List<Location> allLocations = new ArrayList<>();
        Query ref = database.child("locations");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allLocations.clear();
                for(DataSnapshot locationSnapshot: dataSnapshot.getChildren()){
                    Location loc = locationSnapshot.getValue(Location.class);
                    System.out.println(loc.getCity()+ "    "+ loc.getStreet());
                    allLocations.add(loc);
                }
                firebaseCallBack.getAllLocationMethod(allLocations);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void removeLocation(String rest_id){
        this.database.child("locations").child(rest_id).removeValue();
    }

    public void removeAllLocations(){
        this.database.child("locations").removeValue();
    }

    public interface FirebaseCallBack{
        void getLocationMethod(Location location);
        void getAllLocationMethod(List<Location> allLocations);
    }
}
