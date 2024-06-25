package com.example.shashank.umentorapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.shashank.umentorapplication.R;

import java.util.List;
import java.util.Map;

public class EnrolledCourseAdapter extends RecyclerView.Adapter<EnrolledCourseAdapter.EnrolledCourseViewHolder> {

        private List<Map<String, Object>> enrolledCourses;

        public EnrolledCourseAdapter(List<Map<String, Object>> enrolledCourses) {
            this.enrolledCourses = enrolledCourses;
        }

        public void setEnrolledCourses(List<Map<String, Object>> enrolledCourses) {
            this.enrolledCourses = enrolledCourses;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public EnrolledCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enrolled_course_view_holder, parent, false);
            return new EnrolledCourseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EnrolledCourseViewHolder holder, int position) {
            Map<String, Object> course = enrolledCourses.get(position);
            holder.bind(course);
        }

        @Override
        public int getItemCount() {
            return enrolledCourses.size();
        }

        public static class EnrolledCourseViewHolder extends RecyclerView.ViewHolder {

            private TextView titleTextView;
            private TextView instructorTextView;
            private TextView durationTextView;
            private TextView lessonCountTextView;
            private ImageView courseImageView;

            public EnrolledCourseViewHolder(@NonNull View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.enrolledCoursesTitleTxt);
                instructorTextView = itemView.findViewById(R.id.enrolledCoursesInstructorTxt);
                durationTextView = itemView.findViewById(R.id.enrolledCourseTotalDurationTxt);
                lessonCountTextView = itemView.findViewById(R.id.NumberOfLessonTxt);
                courseImageView = itemView.findViewById(R.id.CoursePic);
            }

            public void bind(Map<String, Object> course) {
                titleTextView.setText((String) course.get("title"));
                instructorTextView.setText((String) course.get("instructor"));
                durationTextView.setText((String) course.get("duration"));
                lessonCountTextView.setText(String.valueOf(course.get("lessonCount")));

                // Load course image using Glide
                String imageUrl = (String) course.get("imageUrl");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Glide.with(itemView.getContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.guitar_lesson) // Placeholder image
                            .error(R.drawable.guitar_lesson) // Error image if loading fails
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(courseImageView);
                } else {
                    // Handle case where imageUrl is not available
                    courseImageView.setImageResource(R.drawable.guitar_lesson);
                }
            }
        }
}