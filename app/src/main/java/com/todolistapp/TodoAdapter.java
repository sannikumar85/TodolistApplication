package com.todolistapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    
    private List<TodoItem> todoList;
    private OnItemClickListener listener;
    
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
    
    public TodoAdapter(List<TodoItem> todoList, OnItemClickListener listener) {
        this.todoList = todoList;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem todoItem = todoList.get(position);
        
        holder.textViewTask.setText(todoItem.getText());
        holder.checkBoxCompleted.setChecked(todoItem.isCompleted());
        
        // Format creation time
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());
        holder.textViewTime.setText(sdf.format(new Date(todoItem.getCreatedAt())));
        
        // Set category (with null checks)
        if (todoItem.getCategory() != null && holder.textViewCategory != null) {
            holder.textViewCategory.setText(todoItem.getCategory().getIcon() + " " + todoItem.getCategory().getName());
            
            try {
                // Set category background color
                GradientDrawable categoryBg = new GradientDrawable();
                categoryBg.setShape(GradientDrawable.RECTANGLE);
                categoryBg.setCornerRadius(12f);
                categoryBg.setColor(Color.parseColor(todoItem.getCategory().getColor()));
                holder.textViewCategory.setBackground(categoryBg);
            } catch (Exception e) {
                // Fallback if color parsing fails
                e.printStackTrace();
            }
        }
        
        // Set priority indicator (with null checks)
        if (holder.textViewPriority != null) {
            holder.textViewPriority.setText("●");
            try {
                GradientDrawable priorityBg = new GradientDrawable();
                priorityBg.setShape(GradientDrawable.OVAL);
                priorityBg.setColor(Color.parseColor(todoItem.getPriorityColor()));
                holder.textViewPriority.setBackground(priorityBg);
            } catch (Exception e) {
                // Fallback if color parsing fails
                e.printStackTrace();
            }
        }
        
        // Set priority indicator bar color (with null check)
        if (holder.priorityIndicator != null) {
            try {
                holder.priorityIndicator.setBackgroundColor(Color.parseColor(todoItem.getPriorityColor()));
            } catch (Exception e) {
                // Fallback if color parsing fails
                e.printStackTrace();
            }
        }
        
        // Style based on completion status
        if (todoItem.isCompleted()) {
            holder.textViewTask.setPaintFlags(holder.textViewTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textViewTask.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.text_secondary));
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.task_completed));
            holder.checkBoxCompleted.setText("✅");
        } else {
            holder.textViewTask.setPaintFlags(holder.textViewTask.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.textViewTask.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.text_primary));
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.surface_white));
            holder.checkBoxCompleted.setText("⭕");
        }
        
        // Set click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
        
        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onItemLongClick(position);
            }
            return true;
        });
        
        holder.checkBoxCompleted.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return todoList.size();
    }
    
    static class TodoViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewTask;
        TextView textViewTime;
        TextView textViewCategory;
        TextView textViewPriority;
        CheckBox checkBoxCompleted;
        View priorityIndicator;
        
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                cardView = itemView.findViewById(R.id.card_view);
                textViewTask = itemView.findViewById(R.id.text_view_task);
                textViewTime = itemView.findViewById(R.id.text_view_time);
                checkBoxCompleted = itemView.findViewById(R.id.checkbox_completed);
                
                // These might be null if layout doesn't have them
                textViewCategory = itemView.findViewById(R.id.text_view_category);
                textViewPriority = itemView.findViewById(R.id.text_view_priority);
                priorityIndicator = itemView.findViewById(R.id.priority_indicator);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}