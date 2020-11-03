package com.ncarignan.sortinghat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    ArrayList<Student> students;
    public StudentAdapter(ArrayList<Student> students){
        this.students = students;
    }

    @NonNull
    @Override // gets called when fragment is first made
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // where we create the fragment
        // inflate == render for android
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student, parent, false);

        StudentViewHolder studentViewHolder = new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override // gets called every time a fragment scrolls into view (allows it to change the text in the fragment)
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        // take the holder, and add the correct student to it
        holder.student = students.get(position);
        // set the text of the element on the screen (change TextView ('student') to ('Harry Potter')
        ((TextView) holder.fragment.findViewById(R.id.student_name))
                .setText(holder.student.name + " : " + holder.student.house);
    }

    @Override // [gets called a ton] used to check if new things have been added and to check how many fragments should be loaded
    public int getItemCount() {
        return students.size(); // should be the quantity of students
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        // tying data to a specific view
        // holds the relationship  between fragment and Student
        Student student;
        View fragment;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.fragment = itemView;

        }
    }

}
