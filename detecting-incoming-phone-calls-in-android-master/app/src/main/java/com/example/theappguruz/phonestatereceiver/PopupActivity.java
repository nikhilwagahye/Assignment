package com.example.theappguruz.phonestatereceiver;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PopupActivity extends Activity {

    private RelativeLayout relativeLayoutSubmit;
    private ImageView imageViewClose;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
         phoneNumber = getIntent().getStringExtra("phoneNo");

        relativeLayoutSubmit = (RelativeLayout) findViewById(R.id.relativeLayoutSubmit);
        imageViewClose = (ImageView) findViewById(R.id.imageViewClose);
        
        relativeLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PopupActivity.this, CallEntryActivity.class);
                intent.putExtra("phoneNo",phoneNumber);
                startActivity(intent);
                finish();

            }
        });
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupActivity.this.finish();
            }
        });
    }
}
