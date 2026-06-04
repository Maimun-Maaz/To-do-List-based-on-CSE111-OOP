package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ToDoList {

    private Task[] tasks;
    private int count;
    private int nextId;

    private String username;
    private String fileName;

    public ToDoList(String username, int size) {

        this.username = username;
        this.fileName = username + ".txt";

        tasks = new Task[size];
        count = 0;
        nextId = 1;

        loadTasks();
    }

    // ====================================
    // Internal Helper Methods
    // ====================================

    private int findTaskIndex(int id) {

        for (int i = 0; i < count; i++) {

            if (tasks[i].getId() == id) {
                return i;
            }
        }

        return -1;
    }

    private void expandArray() {

        Task[] temp = new Task[tasks.length * 2];

        for (int i = 0; i < count; i++) {
            temp[i] = tasks[i];
        }

        tasks = temp;
    }

    // ====================================
    // Save Tasks
    // ====================================

    public void saveTasks() {

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(fileName));

            for (int i = 0; i < count; i++) {

                writer.println(
                        tasks[i].getId() + "|" +
                        tasks[i].getTitle() + "|" +
                        tasks[i].getDescription() + "|" +
                        tasks[i].getCategory() + "|" +
                        tasks[i].getPriority() + "|" +
                        tasks[i].getDueDate() + "|" +
                        tasks[i].isCompleted()
                );
            }

            writer.close();

        } catch (Exception e) {

            System.out.println("Error saving tasks.");
        }
    }

    // ====================================
    // Load Tasks
    // ====================================

    public void loadTasks() {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(fileName));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        line.split("\\|");

                int id =
                        Integer.parseInt(data[0]);

                String title =
                        data[1];

                String description =
                        data[2];

                String category =
                        data[3];

                String priority =
                        data[4];

                String dueDate =
                        data[5];

                boolean completed =
                        Boolean.parseBoolean(data[6]);

                if (count == tasks.length) {
                    expandArray();
                }

                Task task =
                        new Task(
                                id,
                                title,
                                description,
                                category,
                                priority,
                                dueDate
                        );

                if (completed) {
                    task.markCompleted();
                }

                tasks[count] = task;

                count++;

                if (id >= nextId) {
                    nextId = id + 1;
                }
            }

            reader.close();

        } catch (Exception e) {

            System.out.println("No previous tasks found for "
                    + username + ".");
        }
    }

    // ====================================
    // Add Task
    // ====================================

    public void addTask(String title,
                        String description,
                        String category,
                        String priority,
                        String dueDate) {

        if (count == tasks.length) {
            expandArray();
        }

        tasks[count] =
                new Task(
                        nextId,
                        title,
                        description,
                        category,
                        priority,
                        dueDate
                );

        count++;
        nextId++;

        saveTasks();

        System.out.println("Task added successfully.");
    }

    // ====================================
    // View Task
    // ====================================

    public void viewTask(int id) {

        int index = findTaskIndex(id);

        if (index == -1) {

            System.out.println("Task not found.");
            return;
        }

        tasks[index].showTask();
    }

    // ====================================
    // View All Tasks
    // ====================================

    public void showAllTasks() {

        if (count == 0) {

            System.out.println("No tasks available.");
            return;
        }

        for (int i = 0; i < count; i++) {
            tasks[i].showTask();
        }
    }

    // ====================================
    // Complete Task
    // ====================================

    public void markTaskCompleted(int id) {

        int index = findTaskIndex(id);

        if (index == -1) {

            System.out.println("Task not found.");
            return;
        }

        tasks[index].markCompleted();

        saveTasks();

        System.out.println("Task completed.");
    }

    public void markTaskPending(int id) {

        int index = findTaskIndex(id);

        if (index == -1) {

            System.out.println("Task not found.");
            return;
        }

        tasks[index].markPending();

        saveTasks();

        System.out.println("Task marked pending.");
    }

    // ====================================
    // Delete Task
    // ====================================

    public void deleteTask(int id) {

        int index = findTaskIndex(id);

        if (index == -1) {

            System.out.println("Task not found.");
            return;
        }

        for (int i = index; i < count - 1; i++) {

            tasks[i] = tasks[i + 1];
        }

        tasks[count - 1] = null;
        count--;

        saveTasks();

        System.out.println("Task deleted.");
    }

    // ====================================
    // Edit Methods
    // ====================================

    public void editTaskTitle(int id,
                              String title) {

        int index = findTaskIndex(id);

        if (index == -1) {
            return;
        }

        tasks[index].editTitle(title);

        saveTasks();
    }

    public void editTaskDescription(int id,
                                    String description) {

        int index = findTaskIndex(id);

        if (index == -1) {
            return;
        }

        tasks[index].editDescription(description);

        saveTasks();
    }

    public void editTaskCategory(int id,
                                 String category) {

        int index = findTaskIndex(id);

        if (index == -1) {
            return;
        }

        tasks[index].editCategory(category);

        saveTasks();
    }

    public void editTaskPriority(int id,
                                 String priority) {

        int index = findTaskIndex(id);

        if (index == -1) {
            return;
        }

        tasks[index].editPriority(priority);

        saveTasks();
    }

    public void editTaskDueDate(int id,
                                String dueDate) {

        int index = findTaskIndex(id);

        if (index == -1) {
            return;
        }

        tasks[index].editDueDate(dueDate);

        saveTasks();
    }

    // ====================================
    // Search
    // ====================================

    public void searchTask(String keyword) {

        boolean found = false;

        for (int i = 0; i < count; i++) {

            if (tasks[i].getTitle()
                    .toLowerCase()
                    .contains(keyword.toLowerCase())) {

                tasks[i].showTask();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching task found.");
        }
    }

    // ====================================
    // Filters
    // ====================================

    public void showPendingTasks() {

        for (int i = 0; i < count; i++) {

            if (!tasks[i].isCompleted()) {
                tasks[i].showTask();
            }
        }
    }

    public void showCompletedTasks() {

        for (int i = 0; i < count; i++) {

            if (tasks[i].isCompleted()) {
                tasks[i].showTask();
            }
        }
    }

    public void showTasksByCategory(String category) {

        for (int i = 0; i < count; i++) {

            if (tasks[i]
                    .getCategory()
                    .equalsIgnoreCase(category)) {

                tasks[i].showTask();
            }
        }
    }

    public void showTasksByPriority(String priority) {

        for (int i = 0; i < count; i++) {

            if (tasks[i]
                    .getPriority()
                    .equalsIgnoreCase(priority)) {

                tasks[i].showTask();
            }
        }
    }

    // ====================================
    // Statistics
    // ====================================

    public int getTotalTasks() {
        return count;
    }

    public int getCompletedTasks() {

        int completed = 0;

        for (int i = 0; i < count; i++) {

            if (tasks[i].isCompleted()) {
                completed++;
            }
        }

        return completed;
    }

    public int getPendingTasks() {

        return count - getCompletedTasks();
    }

    public double getProgressPercentage() {

        if (count == 0) {
            return 0;
        }

        return ((double)
                getCompletedTasks()
                / count) * 100;
    }

    // ====================================
    // Sort By Title
    // ====================================

    public void sortByTitle() {

        for (int i = 0; i < count - 1; i++) {

            for (int j = 0; j < count - i - 1; j++) {

                if (tasks[j]
                        .getTitle()
                        .compareToIgnoreCase(
                                tasks[j + 1].getTitle()) > 0) {

                    Task temp = tasks[j];
                    tasks[j] = tasks[j + 1];
                    tasks[j + 1] = temp;
                }
            }
        }

        saveTasks();

        System.out.println("Tasks sorted.");
    }

    // ====================================
    // Clear All Tasks
    // ====================================

    public void clearAllTasks() {

        for (int i = 0; i < count; i++) {

            tasks[i] = null;
        }

        count = 0;

        saveTasks();

        System.out.println("All tasks removed.");
    }

    // ====================================
    // GUI Accessor
    // ====================================
    public Task[] getTasks() {
        return tasks;
    }
}