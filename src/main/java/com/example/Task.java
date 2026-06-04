package com.example;

public class Task {

    private int id;
    private String title;
    private String description;
    private String category;
    private String priority;
    private String dueDate;
    private boolean completed;

    public Task(int id,
                String title,
                String description,
                String category,
                String priority,
                String dueDate) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.dueDate = dueDate;
        completed = false;
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    // Status methods

    public void markCompleted() {
        completed = true;
    }

    public void markPending() {
        completed = false;
    }

    public String getStatus() {

        if (completed) {
            return "Completed";
        }

        return "Pending";
    }

    // Edit methods

    public void editTitle(String title) {
        this.title = title;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public void editCategory(String category) {
        this.category = category;
    }

    public void editPriority(String priority) {
        this.priority = priority;
    }

    public void editDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    // Display

    public void showTask() {

        System.out.println("-----------------------------------");
        System.out.println("ID          : " + id);
        System.out.println("Title       : " + title);
        System.out.println("Description : " + description);
        System.out.println("Category    : " + category);
        System.out.println("Priority    : " + priority);
        System.out.println("Due Date    : " + dueDate);
        System.out.println("Status      : " + getStatus());
    }
}
    

