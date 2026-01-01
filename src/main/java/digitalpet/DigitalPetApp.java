package digitalpet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

/**
 * Main JavaFX application for the Digital Pet
 */
public class DigitalPetApp extends Application {
    private Pet pet;
    private DataManager dataManager;
    private List<UserStats> userStatsList;
    private UserStats todayStats;
    
    // UI Components
    private Label petNameLabel;
    private Label moodLabel;
    private Label evolutionLabel;
    private ProgressBar xpBar;
    private Label xpLabel;
    private Label levelLabel;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize data
        dataManager = new DataManager();
        pet = dataManager.loadPet();
        userStatsList = dataManager.loadUserStats();
        todayStats = dataManager.getTodayStats(userStatsList);
        
        // Update pet based on today's stats
        pet.updateMood(todayStats);
        pet.updateXP(todayStats);
        
        primaryStage.setTitle("🐾 Digital Pet - " + pet.getName());
        
        // Create main layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEEB, #98FB98);");
        
        // Create pet display area
        VBox petArea = createPetDisplayArea();
        root.setCenter(petArea);
        
        // Create control buttons
        HBox buttonArea = createButtonArea(primaryStage);
        root.setBottom(buttonArea);
        
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Update display
        updatePetDisplay();
    }
    
    private VBox createPetDisplayArea() {
        VBox petArea = new VBox(20);
        petArea.setAlignment(Pos.CENTER);
        
        // Pet name
        petNameLabel = new Label(pet.getName());
        petNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        petNameLabel.setTextFill(Color.DARKBLUE);
        
        // Pet visual (large emoji for now)
        Label petVisual = new Label("🐾");
        petVisual.setFont(Font.font(80));
        
        // Mood display
        moodLabel = new Label();
        moodLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        // Evolution stage
        evolutionLabel = new Label();
        evolutionLabel.setFont(Font.font("Arial", 16));
        evolutionLabel.setTextFill(Color.DARKGREEN);
        
        // Level and XP
        levelLabel = new Label();
        levelLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        xpBar = new ProgressBar();
        xpBar.setPrefWidth(300);
        xpBar.setPrefHeight(20);
        
        xpLabel = new Label();
        xpLabel.setFont(Font.font("Arial", 14));
        
        petArea.getChildren().addAll(
            petNameLabel, petVisual, moodLabel, 
            evolutionLabel, levelLabel, xpBar, xpLabel
        );
        
        return petArea;
    }
    
    private HBox createButtonArea(Stage primaryStage) {
        HBox buttonArea = new HBox(15);
        buttonArea.setAlignment(Pos.CENTER);
        buttonArea.setPadding(new Insets(20, 0, 0, 0));
        
        Button inputStatsBtn = new Button("📝 Input Today's Stats");
        inputStatsBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        inputStatsBtn.setOnAction(e -> openStatsInputWindow());
        
        Button viewStatsBtn = new Button("📊 View Stats History");
        viewStatsBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        viewStatsBtn.setOnAction(e -> openStatsHistoryWindow());
        
        Button saveBtn = new Button("💾 Save Pet");
        saveBtn.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        saveBtn.setOnAction(e -> {
            dataManager.savePet(pet);
            showAlert("Pet saved successfully!", Alert.AlertType.INFORMATION);
        });
        
        buttonArea.getChildren().addAll(inputStatsBtn, viewStatsBtn, saveBtn);
        return buttonArea;
    }
    
    private void updatePetDisplay() {
        petNameLabel.setText("🐾 " + pet.getName());
        moodLabel.setText(pet.getMood().toString());
        evolutionLabel.setText("Evolution: " + pet.getEvolutionStage());
        levelLabel.setText("Level " + pet.getLevel());
        
        double progress = pet.getXpProgress();
        xpBar.setProgress(progress);
        xpLabel.setText(pet.getXp() + " XP (Next level: " + pet.getXpForNextLevel() + " XP)");
        
        // Change mood label color based on mood
        switch (pet.getMood()) {
            case HAPPY:
            case CELEBRATE:
            case ENERGIZED:
                moodLabel.setTextFill(Color.GREEN);
                break;
            case WORRIED:
            case DIZZY:
                moodLabel.setTextFill(Color.RED);
                break;
            case SLEEPY:
                moodLabel.setTextFill(Color.GRAY);
                break;
            default:
                moodLabel.setTextFill(Color.BLACK);
        }
    }
    
    private void openStatsInputWindow() {
        Stage inputStage = new Stage();
        inputStage.setTitle("📝 Input Today's Stats");
        
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);
        
        // Create input fields
        Spinner<Integer> waterSpinner = new Spinner<>(0, 20, todayStats.getWaterIntake());
        Spinner<Integer> studySpinner = new Spinner<>(0, 24, todayStats.getStudyHours());
        Spinner<Integer> stepsSpinner = new Spinner<>(0, 50000, todayStats.getSteps(), 1000);
        Spinner<Integer> sleepSpinner = new Spinner<>(0, 24, todayStats.getSleepHours());
        Spinner<Integer> expensesSpinner = new Spinner<>(0, 10000, todayStats.getExpenses(), 10);
        CheckBox goalsCheckBox = new CheckBox("All daily goals achieved");
        goalsCheckBox.setSelected(todayStats.isGoalsAchieved());
        
        layout.getChildren().addAll(
            new Label("💧 Water intake (glasses):"), waterSpinner,
            new Label("📚 Study hours:"), studySpinner,
            new Label("👟 Steps taken:"), stepsSpinner,
            new Label("😴 Sleep hours:"), sleepSpinner,
            new Label("💰 Money spent ($):"), expensesSpinner,
            goalsCheckBox
        );
        
        Button saveButton = new Button("Save & Update Pet");
        saveButton.setOnAction(e -> {
            // Update today's stats
            todayStats.setWaterIntake(waterSpinner.getValue());
            todayStats.setStudyHours(studySpinner.getValue());
            todayStats.setSteps(stepsSpinner.getValue());
            todayStats.setSleepHours(sleepSpinner.getValue());
            todayStats.setExpenses(expensesSpinner.getValue());
            todayStats.setGoalsAchieved(goalsCheckBox.isSelected());
            
            // Update pet
            pet.updateMood(todayStats);
            pet.updateXP(todayStats);
            
            // Save data
            dataManager.addTodayStats(todayStats, userStatsList);
            dataManager.savePet(pet);
            
            // Update display
            updatePetDisplay();
            
            inputStage.close();
            showAlert("Stats saved and pet updated!", Alert.AlertType.INFORMATION);
        });
        
        layout.getChildren().add(saveButton);
        
        Scene scene = new Scene(layout, 350, 400);
        inputStage.setScene(scene);
        inputStage.show();
    }
    
    private void openStatsHistoryWindow() {
        Stage historyStage = new Stage();
        historyStage.setTitle("📊 Stats History");
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        
        Label titleLabel = new Label("Recent Stats History");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        TextArea historyArea = new TextArea();
        historyArea.setEditable(false);
        historyArea.setPrefRowCount(15);
        
        StringBuilder history = new StringBuilder();
        userStatsList.stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .limit(10)
                .forEach(stats -> {
                    history.append(stats.toString()).append("\n");
                    history.append("Health Score: ").append(stats.getHealthScore()).append("/100\n\n");
                });
        
        if (history.length() == 0) {
            history.append("No stats recorded yet. Start by inputting today's stats!");
        }
        
        historyArea.setText(history.toString());
        
        layout.getChildren().addAll(titleLabel, historyArea);
        
        Scene scene = new Scene(layout, 500, 400);
        historyStage.setScene(scene);
        historyStage.show();
    }
    
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}