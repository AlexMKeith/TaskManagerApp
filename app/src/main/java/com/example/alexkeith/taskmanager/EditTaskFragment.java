package com.example.alexkeith.taskmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

        public class EditTaskFragment extends Fragment {
        private com.example.alexkeith.taskmanager.AddTaskFragment.ActivityCallback activityCallback;
        private TaskDatabase taskDatabase;
        private AddTaskFragment addTaskFragment;


        @BindView(R.id.new_name_edit_text)
        protected EditText newTaskName;
        @BindView(R.id.new_description_edit_text)
        protected EditText newTaskDescription;
        @BindView(R.id.new_due_date_edit_text)
        protected EditText newTaskDueDate;
        @BindView(R.id.edit_task_button)
        protected Button editTaskButton;
        @BindView(R.id.completed_switch)
        protected Switch completedSwitch;
        @BindView(R.id.item_row_layout)
        protected ConstraintLayout itemRowLayout;
        protected Boolean isCompleted;
        protected Date dateCreated;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_edit_task, container, false);
            ButterKnife.bind(this,view);
            return view;
        }
        public static com.example.alexkeith.taskmanager.EditTaskFragment newInstance() {

            Bundle args = new Bundle();
            com.example.alexkeith.taskmanager.EditTaskFragment fragment = new com.example.alexkeith.taskmanager.EditTaskFragment();
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public void onStart() {
            super.onStart();
            taskDatabase = ((TaskApplication) getActivity().getApplicationContext()).getDatabase();
            newTaskName.setHint(addTaskFragment.taskName.getText().toString());
            newTaskDescription.setHint(addTaskFragment.taskDescription.getText().toString());
            newTaskDueDate.setHint(addTaskFragment.taskDueDate.getText().toString());
        }
        @OnClick(R.id.edit_task_button)
        protected void editButtonClicked(){

                String name = newTaskName.getText().toString();
                String description = newTaskDescription.getText().toString();
                String dueDate = newTaskDueDate.getText().toString();
                Date createdDate = dateCreated;
                editTaskInDatabase(name, description, dueDate, createdDate);
                Toast.makeText(getActivity(), "Task Edited!!!",Toast.LENGTH_LONG).show();
                editTaskButton.setVisibility(View.GONE);


        }
        @OnClick(R.id.completed_switch)
        protected void isComplete() {
            if (isCompleted = false) {
                isCompleted = true;
            } else {
                isCompleted = false;
            }
        }

        private void editTaskInDatabase(final String name, final String description, final String dueDate, final Date dateCreated){
            Task task = new Task(name, description, dueDate, dateCreated);
            taskDatabase.taskDao().addTask(task);
            activityCallback.addClicked();

        }
        public void attachParent(com.example.alexkeith.taskmanager.AddTaskFragment.ActivityCallback activityCallback){
            this.activityCallback = activityCallback;

        }
        public interface ActivityCallback{
            void addClicked();

        }
    }