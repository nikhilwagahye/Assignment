package com.example.theappguruz.phonestatereceiver.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.theappguruz.phonestatereceiver.R;
import com.example.theappguruz.phonestatereceiver.SQLiteDB.DatabaseManager;
import com.example.theappguruz.phonestatereceiver.model.CallEntry;

import java.util.ArrayList;
import java.util.List;


public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private final DatabaseManager databaseManager;
    Activity context;
    List<CallEntry> list = new ArrayList<>();
    RecyclerView recyclerView;
    TextView textView;
    public DetailsAdapter(Activity context, List<CallEntry> list, RecyclerView recyclerView, TextView textView) {
        super();
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
        this.textView = textView;
        databaseManager = new DatabaseManager(context);

    }

    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.detail_item, viewGroup, false);
        DetailsAdapter.ViewHolder viewHolder = new DetailsAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DetailsAdapter.ViewHolder viewHolder, final int position) {

        final CallEntry callEntry = list.get(position);
        viewHolder.textViewName.setText(callEntry.getName());
        viewHolder.textViewPhNumber.setText(callEntry.getPhoneNumber());
        viewHolder.textViewEmailId.setText(callEntry.getEmailId());
        viewHolder.textViewQuery.setText(callEntry.getQuery());
        viewHolder.textViewDate.setText(callEntry.getTimeStamp());


        viewHolder.imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + callEntry.getPhoneNumber()));
                context.startActivity(intent);
            }
        });

        /*viewHolder.imageViewSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.AC);
                intent.setData(Uri.parse("tel:" + callEntry.getPhoneNumber()));
                context.startActivity(intent);
            }
        });*/


        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAndUpdate(callEntry.getCallId(),position);

            }
        });
    }

    private void deleteAndUpdate(int callId, int position) {
        int id = databaseManager.deleteCallEntry(callId);
        if (id > 0) {
            list.remove(position);
            this.notifyDataSetChanged();

            if(list.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        public TextView textViewDate;
        public TextView textViewName;
        public TextView textViewPhNumber;
        public TextView textViewEmailId;
        public TextView textViewQuery;
        public ImageView imageViewCall;
        public ImageView imageViewSMS;
        public ImageView imageViewDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewPhNumber = (TextView) itemView.findViewById(R.id.textViewPhNumber);
            textViewEmailId = (TextView) itemView.findViewById(R.id.textViewEmailId);
            textViewQuery = (TextView) itemView.findViewById(R.id.textViewQuery);
            imageViewCall = (ImageView) itemView.findViewById(R.id.imageViewCall);
            imageViewSMS = (ImageView) itemView.findViewById(R.id.imageViewSMS);
            imageViewDelete = (ImageView) itemView.findViewById(R.id.imageViewDelete);
        }
    }
}

