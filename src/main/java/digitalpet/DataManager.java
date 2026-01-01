package digitalpet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading pet data and user statistics to/from JSON files
 */
public class DataManager {
    private static final String PET_DATA_FILE = "pet_data.json";
    private static final String STATS_DATA_FILE = "user_stats.json";
    private final Gson gson;
    
    public DataManager() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }
    
    /**
     * Saves pet data to JSON file
     */
    public void savePet(Pet pet) {
        try (FileWriter writer = new FileWriter(PET_DATA_FILE)) {
            gson.toJson(pet, writer);
            System.out.println("Pet data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving pet data: " + e.getMessage());
        }
    }
    
    /**
     * Loads pet data from JSON file
     */
    public Pet loadPet() {
        File file = new File(PET_DATA_FILE);
        if (!file.exists()) {
            System.out.println("No existing pet data found. Creating new pet...");
            return new Pet("Buddy");
        }
        
        try (FileReader reader = new FileReader(file)) {
            Pet pet = gson.fromJson(reader, Pet.class);
            System.out.println("Pet data loaded successfully!");
            return pet != null ? pet : new Pet("Buddy");
        } catch (IOException e) {
            System.err.println("Error loading pet data: " + e.getMessage());
            return new Pet("Buddy");
        }
    }
    
    /**
     * Saves user statistics to JSON file
     */
    public void saveUserStats(List<UserStats> statsList) {
        try (FileWriter writer = new FileWriter(STATS_DATA_FILE)) {
            gson.toJson(statsList, writer);
            System.out.println("User stats saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving user stats: " + e.getMessage());
        }
    }
    
    /**
     * Loads user statistics from JSON file
     */
    public List<UserStats> loadUserStats() {
        File file = new File(STATS_DATA_FILE);
        if (!file.exists()) {
            System.out.println("No existing stats data found. Starting fresh...");
            return new ArrayList<>();
        }
        
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<UserStats>>(){}.getType();
            List<UserStats> stats = gson.fromJson(reader, listType);
            System.out.println("User stats loaded successfully!");
            return stats != null ? stats : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error loading user stats: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Adds new user stats for today and saves
     */
    public void addTodayStats(UserStats todayStats, List<UserStats> existingStats) {
        // Remove any existing stats for today
        existingStats.removeIf(stats -> stats.getDate().equals(LocalDate.now()));
        
        // Add today's stats
        existingStats.add(todayStats);
        
        // Save updated list
        saveUserStats(existingStats);
    }
    
    /**
     * Gets stats for a specific date
     */
    public UserStats getStatsForDate(LocalDate date, List<UserStats> statsList) {
        return statsList.stream()
                .filter(stats -> stats.getDate().equals(date))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Gets today's stats or creates new empty stats
     */
    public UserStats getTodayStats(List<UserStats> statsList) {
        UserStats todayStats = getStatsForDate(LocalDate.now(), statsList);
        return todayStats != null ? todayStats : new UserStats();
    }
}