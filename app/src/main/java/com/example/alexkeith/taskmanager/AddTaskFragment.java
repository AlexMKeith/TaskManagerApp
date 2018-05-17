package com.example.alexkeith.taskmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTaskFragment extends Fragment{
    private ActivityCallback activityCallback;
    private TaskDatabase taskDatabase;


    @BindView(R.id.add_task_name_edit_text)
    protected EditText taskName;
    @BindView(R.id.add_task_description_edit_text)
    protected EditText taskDescription;
    @BindView(R.id.add_task_due_date_edit_text)
    protected EditText taskDueDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    public static AddTaskFragment newInstance() {

        Bundle args = new Bundle();
        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();
        taskDatabase = ((TaskApplication) getActivity().getApplicationContext()).getDatabase();
    }
    @OnClick(R.id.add_task_fab)
    protected void addButtonClicked(){

        if(taskName.getText().toString().isEmpty() || taskDescription.getText().toString().isEmpty() || taskDueDate.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Try Again, please fill in all options!!",Toast.LENGTH_LONG).show();
        }else{
            String name = taskName.getText().toString();
            String description = taskDescription.getText().toString();
            String dueDate = taskDueDate.getText().toString();
            addTaskToDatabase(name, description, dueDate);
            Toast.makeText(getActivity(), "Game Added!!!",Toast.LENGTH_LONG).show();

        }
    }
    private void addTaskToDatabase(final String name, final String description, final String dueDate){
        Task task = new Task(name, description, dueDate);
        taskDatabase.taskDao().addTask(task);
        activityCallback.addClicked();

    }
    public void attachParent(ActivityCallback activityCallback){
        this.activityCallback = activityCallback;

    }
    public interface ActivityCallback{
        void addClicked();

    }
}
