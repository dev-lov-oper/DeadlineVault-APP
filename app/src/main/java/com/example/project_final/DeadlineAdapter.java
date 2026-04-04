package com.example.project_final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeadlineAdapter extends RecyclerView.Adapter<DeadlineAdapter.ViewHolder> {

    List<Deadline> list;

    public DeadlineAdapter(List<Deadline> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deadline, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Deadline d = list.get(position);

        holder.title.setText(d.getTitle());
        holder.subject.setText(d.getSubject());

       
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date today = new Date();
            Date dueDate = sdf.parse(d.getDueDate());

            long diff = dueDate.getTime() - today.getTime();
            int days = (int) (diff / (1000 * 60 * 60 * 24));

            holder.days.setText(days + " days left");

            View colorStrip = holder.itemView.findViewById(R.id.colorStrip);

            // Urgency logic
            if (days <= 2) {
                colorStrip.setBackgroundColor(0xFFFF0000); // RED
            } else if (days <= 7) {
                colorStrip.setBackgroundColor(0xFFFFA500); // AMBER
            } else {
                colorStrip.setBackgroundColor(0xFF00FF00); // GREEN
            }

        } catch (Exception e) {
            e.printStackTrace();
            holder.days.setText("Invalid date");
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    // ViewHolder Class
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, subject, days;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTaskTitle);
            subject = itemView.findViewById(R.id.tvSubject);
            days = itemView.findViewById(R.id.tvDays);
        }
    }
}
