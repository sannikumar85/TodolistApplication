package com.todolistapp;

public class TodoItem {
    private int id;
    private String text;
    private boolean isCompleted;
    private long createdAt;
    private Category category;
    private int priority; // 1 = Low, 2 = Medium, 3 = High
    private String dueDate;
    private String description;

    public TodoItem(int id, String text) {
        this.id = id;
        this.text = text;
        this.isCompleted = false;
        this.createdAt = System.currentTimeMillis();
        
        // Safely set default category
        try {
            Category[] defaultCategories = Category.getDefaultCategories();
            if (defaultCategories != null && defaultCategories.length > 1) {
                this.category = defaultCategories[1]; // Default to Personal
            } else {
                this.category = new Category("Personal", "#4CAF50", "👤"); // Fallback
            }
        } catch (Exception e) {
            this.category = new Category("Personal", "#4CAF50", "👤"); // Safe fallback
        }
        
        this.priority = 2; // Default to Medium
        this.dueDate = "";
        this.description = "";
    }

    public TodoItem(int id, String text, Category category, int priority) {
        this(id, text);
        this.category = category;
        this.priority = priority;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void toggleCompletion() {
        this.isCompleted = !this.isCompleted;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
    
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriorityText() {
        switch (priority) {
            case 1: return "Low";
            case 2: return "Medium";
            case 3: return "High";
            default: return "Medium";
        }
    }

    public String getPriorityColor() {
        switch (priority) {
            case 1: return "#4CAF50"; // Green
            case 2: return "#FF9800"; // Orange
            case 3: return "#F44336"; // Red
            default: return "#FF9800";
        }
    }
}