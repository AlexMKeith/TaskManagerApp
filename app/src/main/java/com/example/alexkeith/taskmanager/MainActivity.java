package com.example.alexkeith.taskmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AddTaskFragment.ActivityCallback, EditTaskFragment.ActivityCallback, TaskAdapter.AdapterCallback{

    @BindView(R.id.tasks_recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.add_task_button)
    protected Button addTaskButton;
//    @BindView(R.id.item_row_layout)
//    protected ConstraintLayout itemRowLayout;
//    @BindView(R.id.edit_task_button)
//    protected Button editTaskButton;


    private TaskDatabase taskDatabase;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private AddTaskFragment addTaskFragment;
    private LinearLayoutManager linearLayoutManager;
    private EditTaskFragment editTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView = findViewById(R.id.tasks_recycler_view);
        taskDatabase = ((TaskApplication) getApplicationContext()).getDatabase();
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        taskAdapter = new TaskAdapter(taskDatabase.taskDao().getTask(),this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();
    }
    @OnClick(R.id.add_task_button)
    protected void addGameButtonClicked(){
            addTaskFragment = AddTaskFragment.newInstance();
            addTaskFragment.attachParent(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, addTaskFragment).commit();
            addTaskButton.setVisibility(View.GONE);
    }
//    @Override
    public void addClicked() {
        getSupportFragmentManager().beginTransaction().remove(addTaskFragment).commit();
        taskAdapter.updateList(taskDatabase.taskDao().getTask());
        addTaskButton.setVisibility(View.VISIBLE);
    }
    public void editClicked() {
        getSupportFragmentManager().beginTransaction().remove(editTaskFragment).commit();
        taskAdapter.updateList(taskDatabase.taskDao().getTask());
        addTaskButton.setVisibility(View.VISIBLE);
    }
//    public void editClickedUncompleted() {
//        getSupportFragmentManager().beginTransaction().remove(editTaskFragment).commit();
//        taskAdapter2.updateList(taskDatabase2.taskDao().getTask());
//        addTaskButton.setVisibility(View.VISIBLE);
//    }
//    @Override
    public Context getContext(){
        return getApplicationContext();

    }
//    @Override
    public void rowClicked(Task task) {
        editTaskFragment = EditTaskFragment.newInstance();
        editTaskFragment.attachParent(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, editTaskFragment).commit();
        addTaskButton.setVisibility(View.GONE);
    }
//    @Override
    public void rowLongClicked(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task?").setMessage("Are you sure you would like to delete this task?").setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                taskDatabase.taskDao().deleteTask(task);

                taskAdapter.updateList(taskDatabase.taskDao().getTask());

                Toast.makeText(MainActivity.this, "Task has been deleted!", Toast.LENGTH_LONG).show();
            }
        }).setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

}
