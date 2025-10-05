package com.todolistapp;

public class Category {
    private String name;
    private String color;
    private String icon;

    public Category(String name, String color, String icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    // Predefined categories
    public static Category[] getDefaultCategories() {
        return new Category[]{
            new Category("Work", "#2196F3", "💼"),
            new Category("Personal", "#4CAF50", "👤"),
            new Category("Shopping", "#FF9800", "🛒"),
            new Category("Health", "#E91E63", "❤️"),
            new Category("Study", "#9C27B0", "📚"),
            new Category("Travel", "#00BCD4", "✈️")
        };
    }
}