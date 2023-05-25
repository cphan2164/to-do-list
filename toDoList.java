/* To Do List 
 * Created by Conor Phan
 * 
 * This program allows the user to run an application to make a to do list for themselves
 * The user is able to set fields such as date and time for each task
 * Each task is then added to a list, where a button next to it is created allowing for easy deletion
*/

package tasks;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class toDoList extends Application {

    // Create the fields for the application
    private TextField taskTextField;
    private DatePicker dueDatePicker;
    private TextField dueTimeTextField;
    private VBox tasksContainer;
    private List<Task> tasks;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Title of application
        primaryStage.setTitle("Task List App");

        //Array list for applciation
        tasks = new ArrayList<>();

        // Create input fields. First being an input text field for the task itself
        Label taskLabel = new Label("Enter a task:");
        taskTextField = new TextField();

        // Then a date picker, in case the task has a due date
        Label dueDateLabel = new Label("Due Date:");
        dueDatePicker = new DatePicker();

        // Then a time picker, in case the task has a time it is due at
        Label dueTimeLabel = new Label("Due Time:");
        dueTimeTextField = new TextField();

        // Create container for tasks
        tasksContainer = new VBox(5);
        tasksContainer.setStyle("-fx-background-color: #f5f5f5;"); // Set background color

        // Create button
        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> addTask());

        // Create layout
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10));

        layout.add(taskLabel, 0, 0);
        layout.add(taskTextField, 1, 0);

        layout.add(dueDateLabel, 0, 1);
        layout.add(dueDatePicker, 1, 1);

        layout.add(dueTimeLabel, 0, 2);
        layout.add(dueTimeTextField, 1, 2);

        layout.add(addButton, 0, 3, 2, 1);
        layout.add(tasksContainer, 0, 4, 2, 1);

        // Create scene
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add("style.css"); // Load external CSS file

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // This function adds the task in the application using the input field
    private void addTask() {
        String task = taskTextField.getText();
        String dueDate = dueDatePicker.getValue() != null ? dueDatePicker.getValue().toString() : "";
        String dueTime = dueTimeTextField.getText();
        if (!task.isEmpty()) {
            Task newTask = new Task(task, dueDate, dueTime);
            tasks.add(newTask);
            updateTaskList();
            taskTextField.clear();
            dueDatePicker.setValue(null);
            dueTimeTextField.clear();
        }
    }

    // This function updates the task list
    private void updateTaskList() {
        tasksContainer.getChildren().clear();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            HBox taskBox = new HBox(10);
            taskBox.setAlignment(Pos.CENTER_LEFT);

            Label taskLabel = new Label(task.getName());
            // When updating the list it checks if the task has a time or date
            Label dueDateTimeLabel = new Label(task.getDueDateTime());

            Button deleteButton = new Button("Delete");
            int index = i; // Need to store the index in a final variable for lambda expression
            deleteButton.setOnAction(e -> deleteTask(index));

            taskBox.getChildren().addAll(taskLabel, dueDateTimeLabel, deleteButton);
            tasksContainer.getChildren().add(taskBox);
        }
    }

    // This function deletes task from the list
    private void deleteTask(int index) {
        tasks.remove(index);
        updateTaskList();
    }

    // This is the class that is used to for the tasks.
    private static class Task {
        private String name;
        private String dueDate;
        private String dueTime;

        // The constructor takes the name, date, and time of the task
        public Task(String name, String dueDate, String dueTime) {
            this.name = name;
            this.dueDate = dueDate;
            this.dueTime = dueTime;
        }

        public String getName() {
            return name;
        }

        // If the task does not have a time and/or date an empty String is then used instead. This makes it so the constructor is always full
        public String getDueDateTime() {
            if (!dueDate.isEmpty() && !dueTime.isEmpty()) {
                return dueDate + " " + dueTime;
            } else if (!dueDate.isEmpty()) {
                return dueDate;
            } else if (!dueTime.isEmpty()) {
                return dueTime;
            } else {
                return "";
            }
        }
    }
}
