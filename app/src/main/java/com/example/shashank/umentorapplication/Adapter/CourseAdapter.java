package com.example.shashank.umentorapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shashank.umentorapplication.Activity.CourseDetailActivity;
import com.example.shashank.umentorapplication.Domain.CourseDomain;
import com.example.shashank.umentorapplication.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<CourseDomain> courses;
    private Context context;

    public CourseAdapter(List<CourseDomain> courses, Context context) {
        this.courses = courses;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseDomain course = courses.get(position);
        holder.bind(course);

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            // Pass data to CourseDetailActivity
            Intent intent = new Intent(context, CourseDetailActivity.class);
            intent.putExtra("courseTitle", course.getTitle());
            intent.putExtra("courseInstructor", course.getInstructor());
            intent.putExtra("courseDuration", course.getDuration());
            intent.putExtra("courseImage", course.getImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView instructorTextView;
        private TextView durationTextView;
        private ImageView courseImageView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTxt);
            instructorTextView = itemView.findViewById(R.id.instructorTxt);
            durationTextView = itemView.findViewById(R.id.durationTxt);
            courseImageView = itemView.findViewById(R.id.CoursePic);
        }

        public void bind(CourseDomain course) {
            titleTextView.setText(course.getTitle());
            instructorTextView.setText(course.getInstructor());
            durationTextView.setText(course.getDuration());

            // Load image using Glide (assuming imageUrl is stored in Firestore)
            Glide.with(itemView.getContext())
                    .load(course.getImageUrl())
                    .placeholder(R.drawable.guitar_lesson) // Placeholder image while loading
                    .error(R.drawable.guitar_lesson) // Image to display if load fails
                    .into(courseImageView);
        }
    }
}