package digitalpet;

/**
 * Enum representing different pet moods
 * Each mood corresponds to different visual states and animations
 */
public enum Mood {
    HAPPY("😊", "Happy", "Your pet is joyful and content!"),
    SMART("🤓", "Smart", "Your pet feels intellectually stimulated!"),
    SLEEPY("😴", "Sleepy", "Your pet needs more activity and energy!"),
    WORRIED("😟", "Worried", "Your pet is concerned about your spending!"),
    CELEBRATE("🎉", "Celebrating", "Your pet is celebrating your achievements!"),
    DIZZY("😵", "Dizzy", "Your pet feels overwhelmed and tired!"),
    ENERGIZED("😄", "Energized", "Your pet is full of energy and vitality!"),
    NEUTRAL("😐", "Neutral", "Your pet is feeling okay, nothing special.");
    
    private final String emoji;
    private final String displayName;
    private final String description;
    
    Mood(String emoji, String displayName, String description) {
        this.emoji = emoji;
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getEmoji() {
        return emoji;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return emoji + " " + displayName;
    }
}