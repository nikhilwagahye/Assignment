package com.example.theappguruz.phonestatereceiver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.theappguruz.phonestatereceiver.SQLiteDB.DatabaseManager;
import com.example.theappguruz.phonestatereceiver.adapter.DetailsAdapter;
import com.example.theappguruz.phonestatereceiver.model.CallEntry;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ListOfCallsDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManger;
    private TextView textViewNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_calls_details);
        askPermission();


        DatabaseManager databaseManager = new DatabaseManager(ListOfCallsDetailsActivity.this);
        List<CallEntry> list = databaseManager.getAllCallEntries();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        textViewNoData = (TextView) findViewById(R.id.textViewNoData);
        recyclerView.setHasFixedSize(true);
        mLayoutManger = new LinearLayoutManager(ListOfCallsDetailsActivity.this);
        recyclerView.setLayoutManager(mLayoutManger);


        if(list != null && list.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new DetailsAdapter(ListOfCallsDetailsActivity.this, list,recyclerView,textViewNoData));
            textViewNoData.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            textViewNoData.setVisibility(View.VISIBLE);
        }
    }


    private void askPermission() {
        if (!checkPermission()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_PHONE_STATE)) {
                new TedPermission(getApplicationContext())
                        .setPermissionListener(permissionlistener)
                        .setRationaleConfirmText("ALLOW")
                        .setRationaleMessage("This Requires Permission")
                        .setPermissions(Manifest.permission.READ_PHONE_STATE)
                        .check();
            } else {
                new TedPermission(getApplicationContext())
                        .setPermissionListener(permissionlistener)
                        .setDeniedCloseButtonText("Cancel")
                        .setDeniedMessage("If you reject permission,you can not use this service \n Please turn on permissions from Settings")
                        .setGotoSettingButtonText("Settings")
                        .setPermissions(Manifest.permission.READ_PHONE_STATE)
                        .check();
            }
        } else {


        }


    }

    private boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);

        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }
    };


    @Override
    public void onBackPressed() {
        finish();
    }
}
