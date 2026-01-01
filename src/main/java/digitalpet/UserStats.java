package digitalpet;

import java.time.LocalDate;

/**
 * Represents user's daily statistics and habits
 * Used to calculate pet mood and XP changes
 */
public class UserStats {
    private LocalDate date;
    private int waterIntake; // glasses of water
    private int studyHours;
    private int steps;
    private int sleepHours;
    private int expenses; // in dollars
    private boolean goalsAchieved;
    
    public UserStats() {
        this.date = LocalDate.now();
        this.waterIntake = 0;
        this.studyHours = 0;
        this.steps = 0;
        this.sleepHours = 8;
        this.expenses = 0;
        this.goalsAchieved = false;
    }
    
    public UserStats(LocalDate date, int waterIntake, int studyHours, int steps, 
                    int sleepHours, int expenses, boolean goalsAchieved) {
        this.date = date;
        this.waterIntake = waterIntake;
        this.studyHours = studyHours;
        this.steps = steps;
        this.sleepHours = sleepHours;
        this.expenses = expenses;
        this.goalsAchieved = goalsAchieved;
    }
    
    /**
     * Calculates a simple health score based on all stats
     */
    public int getHealthScore() {
        int score = 0;
        
        // Water intake (0-30 points)
        score += Math.min(waterIntake * 3, 30);
        
        // Study hours (0-25 points)
        score += Math.min(studyHours * 8, 25);
        
        // Steps (0-20 points)
        score += Math.min(steps / 400, 20);
        
        // Sleep (0-15 points)
        if (sleepHours >= 7 && sleepHours <= 9) {
            score += 15;
        } else if (sleepHours >= 6 && sleepHours <= 10) {
            score += 10;
        }
        
        // Goals achieved (0-10 points)
        if (goalsAchieved) {
            score += 10;
        }
        
        // Penalty for overspending
        if (expenses > 500) {
            score -= 10;
        }
        
        return Math.max(0, score);
    }
    
    // Getters and setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public int getWaterIntake() { return waterIntake; }
    public void setWaterIntake(int waterIntake) { this.waterIntake = waterIntake; }
    
    public int getStudyHours() { return studyHours; }
    public void setStudyHours(int studyHours) { this.studyHours = studyHours; }
    
    public int getSteps() { return steps; }
    public void setSteps(int steps) { this.steps = steps; }
    
    public int getSleepHours() { return sleepHours; }
    public void setSleepHours(int sleepHours) { this.sleepHours = sleepHours; }
    
    public int getExpenses() { return expenses; }
    public void setExpenses(int expenses) { this.expenses = expenses; }
    
    public boolean isGoalsAchieved() { return goalsAchieved; }
    public void setGoalsAchieved(boolean goalsAchieved) { this.goalsAchieved = goalsAchieved; }
    
    @Override
    public String toString() {
        return String.format("Stats for %s: Water=%d, Study=%dh, Steps=%d, Sleep=%dh, Expenses=$%d, Goals=%s",
                date, waterIntake, studyHours, steps, sleepHours, expenses, goalsAchieved ? "✓" : "✗");
    }
}