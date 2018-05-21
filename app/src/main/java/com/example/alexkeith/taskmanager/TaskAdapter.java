package com.example.alexkeith.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> taskList;
    private AdapterCallback adapterCallback;

    public TaskAdapter(List<Task> taskList, AdapterCallback adapterCallback) {
        this.taskList = taskList;
        this.adapterCallback = adapterCallback;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_manager, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(taskList.get(position));

        holder.itemView.setOnClickListener(holder.onClick(taskList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(taskList.get(position)));
    }
    @Override
    public int getItemCount() {
        return taskList.size();
    }
    public void updateList(List<Task> list){
        taskList = list;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_row_layout)
        protected ConstraintLayout rowLayout;
        @BindView(R.id.item_title)
        protected TextView taskName;
        @BindView(R.id.item_description)
        protected TextView taskDescription;
        @BindView(R.id.item_date)
        protected TextView taskDueDate;
        @BindView(R.id.date_completed)
        protected TextView dateCompleted;
        @BindView(R.id.date_created)
        protected TextView dateCreated;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bind(Task task) {
            taskName.setText(task.getTaskName());
            taskDescription.setText(adapterCallback.getContext().getString(R.string.task_description, task.getTaskDescription()));
            taskDueDate.setText(task.getDueDate());

            if (task.isCompleted() == true) {
                rowLayout.setBackgroundResource(R.color.green);
            } else {
                rowLayout.setBackgroundResource(R.color.red);
            }
        }

        public View.OnClickListener onClick(final Task task){
            return new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    adapterCallback.rowClicked(task);
                }
            };
        }
        public View.OnLongClickListener onLongClick(final Task task) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterCallback.rowLongClicked(task);
                    return true;
                }
            };
        }
    }
    public interface AdapterCallback{
        Context getContext();
        void rowClicked(Task task);
        void rowLongClicked(Task task);
    }
}
