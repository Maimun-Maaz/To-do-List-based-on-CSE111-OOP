package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {
    // Instantiate your upgraded ToDoList backend class
    private ToDoList myTodoList = new ToDoList("my_tasks", 10);
    
    // Core Visual Layout Components
    private TextArea displayArea = new TextArea();
    
    // Input Fields for your expanded Task attributes
    private TextField titleInput = new TextField();
    private TextField descInput = new TextField();
    private TextField catInput = new TextField();
    private TextField priorityInput = new TextField();
    private TextField dueDateInput = new TextField();
    private TextField idInput = new TextField();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Advanced To-Do List Dashboard");

        // 1. Structured Form Input Layout
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(8);
        inputGrid.setPadding(new Insets(10));

        inputGrid.add(new Label("Task Title:"), 0, 0);
        inputGrid.add(titleInput, 1, 0);
        
        inputGrid.add(new Label("Description:"), 0, 1);
        inputGrid.add(descInput, 1, 1);
        
        inputGrid.add(new Label("Category:"), 0, 2);
        inputGrid.add(catInput, 1, 2);
        
        inputGrid.add(new Label("Priority Level:"), 0, 3);
        inputGrid.add(priorityInput, 1, 3);
        
        inputGrid.add(new Label("Due Date:"), 0, 4);
        inputGrid.add(dueDateInput, 1, 4);

        Button addButton = new Button("Add New Task");
        addButton.setStyle("-fx-font-weight: bold;");
        inputGrid.add(addButton, 1, 5);

        // 2. Center Task Display Output Area
        displayArea.setEditable(false);
        displayArea.setPromptText("No tasks added yet...");
        displayArea.setPrefHeight(250);

        // 3. Bottom Management Operations Action Row
        Label idLabel = new Label("Target Task ID:");
        idInput.setPrefWidth(50);
        Button completeButton = new Button("Mark Completed");
        Button pendingButton = new Button("Mark Pending");
        Button deleteButton = new Button("Delete Task");
        
        HBox actionSection = new HBox(10, idLabel, idInput, completeButton, pendingButton, deleteButton);
        actionSection.setPadding(new Insets(10, 5, 10, 5));

        // --- INTERACTIVE SYSTEM EVENT LOGIC ---

        // Submission click handling for multi-field data structures
        addButton.setOnAction(e -> {
            String title = titleInput.getText().trim();
            String desc = descInput.getText().trim();
            String cat = catInput.getText().trim();
            String priority = priorityInput.getText().trim();
            String due = dueDateInput.getText().trim();

            if (!title.isEmpty()) {
                // Safely invokes your multi-parameter method call
                myTodoList.addTask(title, desc, cat, priority, due);
                
                // Reset input context states
                titleInput.clear();
                descInput.clear();
                catInput.clear();
                priorityInput.clear();
                dueDateInput.clear();
                
                refreshVisualList();
            } else {
                System.out.println("Title field cannot be empty.");
            }
        });

        // Event handling to set completion state tracking flags
        completeButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idInput.getText().trim());
                myTodoList.markTaskCompleted(id);
                idInput.clear();
                refreshVisualList();
            } catch (NumberFormatException ex) {
                System.out.println("Error: Please provide a valid numerical ID mapping.");
            }
        });

        // Event handling to revert state tracking flags back to pending status
        pendingButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idInput.getText().trim());
                myTodoList.markTaskPending(id);
                idInput.clear();
                refreshVisualList();
            } catch (NumberFormatException ex) {
                System.out.println("Error: Please provide a valid numerical ID mapping.");
            }
        });

        // Node pointer array shift execution trigger handling
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idInput.getText().trim());
                myTodoList.deleteTask(id);
                idInput.clear();
                refreshVisualList();
            } catch (NumberFormatException ex) {
                System.out.println("Error: Please provide a valid numerical ID mapping.");
            }
        });

        // 4. Assemble Component Hierarchy Layout Pane Nodes
        VBox rootLayoutContainer = new VBox(10, inputGrid, displayArea, actionSection);
        rootLayoutContainer.setPadding(new Insets(15));

        Scene scene = new Scene(rootLayoutContainer, 550, 550);
        stage.setScene(scene);
        stage.show();
        
        refreshVisualList();
    }

    // Maps application arrays into readable formatting structures inside your UI View
    private void refreshVisualList() {
        if (myTodoList.getTotalTasks() == 0) {
            displayArea.setText("Your task list is empty. Add a task above to populate the record tracker!");
            return;
        }

        StringBuilder dataContainerStringBuilder = new StringBuilder();
        dataContainerStringBuilder.append("=================== LIVE TASK LIST OVERVIEW ===================\n");
        
        Task[] dataListArrayReference = myTodoList.getTasks();
        for (int indexValueCounter = 0; indexValueCounter < myTodoList.getTotalTasks(); indexValueCounter++) {
            Task structuralTaskTarget = dataListArrayReference[indexValueCounter];
            
            dataContainerStringBuilder.append(" ID          : ").append(structuralTaskTarget.getId()).append("\n")
              .append(" Title       : ").append(structuralTaskTarget.getTitle()).append("\n")
              .append(" Description : ").append(structuralTaskTarget.getDescription()).append("\n")
              .append(" Category    : ").append(structuralTaskTarget.getCategory()).append("\n")
              .append(" Priority    : ").append(structuralTaskTarget.getPriority()).append("\n")
              .append(" Due Date    : ").append(structuralTaskTarget.getDueDate()).append("\n")
              .append(" Status      : ").append(structuralTaskTarget.getStatus()).append("\n")
              .append("--------------------------------------------------------------------------------\n");
        }
        displayArea.setText(dataContainerStringBuilder.toString());
    }

    public static void main(String[] args) {
        launch();
    }
}