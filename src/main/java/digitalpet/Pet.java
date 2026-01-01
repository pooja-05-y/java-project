package digitalpet;

/**
 * Core Pet class that represents the virtual pet
 * Handles mood, XP, level, and evolution logic
 */
public class Pet {
    private int xp;
    private int level;
    private Mood mood;
    private String evolutionStage;
    private String name;
    
    public Pet(String name) {
        this.name = name;
        this.xp = 0;
        this.level = 1;
        this.mood = Mood.NEUTRAL;
        this.evolutionStage = "Egg";
    }
    
    /**
     * Updates pet mood based on user's daily habits
     */
    public void updateMood(UserStats stats) {
        // Priority-based mood system
        if (stats.isGoalsAchieved()) {
            mood = Mood.CELEBRATE;
        } else if (stats.getWaterIntake() >= 8 && stats.getStudyHours() >= 2) {
            mood = Mood.HAPPY;
        } else if (stats.getStudyHours() >= 3) {
            mood = Mood.SMART;
        } else if (stats.getExpenses() > 500) {
            mood = Mood.WORRIED;
        } else if (stats.getSteps() < 3000) {
            mood = Mood.SLEEPY;
        } else if (stats.getSleepHours() < 6) {
            mood = Mood.DIZZY;
        } else if (stats.getSleepHours() >= 8 && stats.getWaterIntake() >= 6) {
            mood = Mood.ENERGIZED;
        } else {
            mood = Mood.NEUTRAL;
        }
    }
    
    /**
     * Updates XP based on user habits and calculates level
     */
    public void updateXP(UserStats stats) {
        // Positive XP gains
        xp += stats.getStudyHours() * 20;
        xp += stats.getWaterIntake() >= 8 ? 15 : 0;
        xp += stats.getSteps() > 8000 ? 25 : (stats.getSteps() > 5000 ? 15 : 0);
        xp += stats.getSleepHours() >= 7 && stats.getSleepHours() <= 9 ? 10 : 0;
        xp += stats.isGoalsAchieved() ? 50 : 0;
        
        // Negative XP penalties
        xp -= stats.getExpenses() > 500 ? 10 : 0;
        xp -= stats.getSteps() < 3000 ? 15 : 0;
        xp -= stats.getSleepHours() < 6 ? 10 : 0;
        
        // Ensure XP doesn't go negative
        if (xp < 0) xp = 0;
        
        // Calculate level and evolution
        int newLevel = (xp / 100) + 1;
        if (newLevel != level) {
            level = newLevel;
            updateEvolution();
        }
    }
    
    /**
     * Updates evolution stage based on level
     */
    private void updateEvolution() {
        switch (level) {
            case 1:
                evolutionStage = "Egg";
                break;
            case 2:
                evolutionStage = "Baby Pet";
                break;
            case 3:
                evolutionStage = "Teen Pet";
                break;
            case 4:
                evolutionStage = "Adult Pet";
                break;
            case 5:
            default:
                evolutionStage = "Legendary Form";
                break;
        }
    }
    
    // Getters and setters
    public int getXp() { return xp; }
    public void setXp(int xp) { this.xp = xp; }
    
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    
    public Mood getMood() { return mood; }
    public void setMood(Mood mood) { this.mood = mood; }
    
    public String getEvolutionStage() { return evolutionStage; }
    public void setEvolutionStage(String evolutionStage) { this.evolutionStage = evolutionStage; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getXpForNextLevel() {
        return (level * 100) - xp;
    }
    
    public double getXpProgress() {
        int currentLevelXp = xp - ((level - 1) * 100);
        return currentLevelXp / 100.0;
    }
}