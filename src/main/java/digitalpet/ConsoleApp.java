package digitalpet;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based version of the Digital Pet application
 * Perfect for testing the core functionality without JavaFX issues
 */
public class ConsoleApp {
    private Pet pet;
    private DataManager dataManager;
    private List<UserStats> userStatsList;
    private Scanner scanner;
    
    public ConsoleApp() {
        this.scanner = new Scanner(System.in);
        this.dataManager = new DataManager();
        this.pet = dataManager.loadPet();
        this.userStatsList = dataManager.loadUserStats();
        
        // Update pet based on today's stats
        UserStats todayStats = dataManager.getTodayStats(userStatsList);
        pet.updateMood(todayStats);
        pet.updateXP(todayStats);
    }
    
    public void run() {
        System.out.println("🐾 Welcome to Digital Pet! 🐾");
        System.out.println("Your virtual companion that grows with your habits!");
        System.out.println();
        
        while (true) {
            displayPetStatus();
            showMenu();
            
            int choice = getChoice();
            switch (choice) {
                case 1:
                    inputTodayStats();
                    break;
                case 2:
                    viewStatsHistory();
                    break;
                case 3:
                    savePet();
                    break;
                case 4:
                    System.out.println("👋 Thanks for taking care of " + pet.getName() + "!");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }
    
    private void displayPetStatus() {
        System.out.println("═══════════════════════════════════════");
        System.out.println("🐾 " + pet.getName() + " - " + pet.getEvolutionStage());
        System.out.println("═══════════════════════════════════════");
        System.out.println("Mood: " + pet.getMood());
        System.out.println("Level: " + pet.getLevel());
        System.out.println("XP: " + pet.getXp() + " (Next level: " + pet.getXpForNextLevel() + " XP)");
        
        // Simple XP progress bar
        int progress = (int) (pet.getXpProgress() * 20);
        System.out.print("Progress: [");
        for (int i = 0; i < 20; i++) {
            System.out.print(i < progress ? "█" : "░");
        }
        System.out.println("] " + (int)(pet.getXpProgress() * 100) + "%");
        System.out.println("═══════════════════════════════════════");
    }
    
    private void showMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1. 📝 Input today's habits");
        System.out.println("2. 📊 View stats history");
        System.out.println("3. 💾 Save pet data");
        System.out.println("4. 🚪 Exit");
        System.out.print("Choose (1-4): ");
    }
    
    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void inputTodayStats() {
        System.out.println("\n📝 Input Today's Habits");
        System.out.println("═══════════════════════════");
        
        UserStats todayStats = dataManager.getTodayStats(userStatsList);
        
        try {
            System.out.print("💧 Water intake (glasses, current: " + todayStats.getWaterIntake() + "): ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                todayStats.setWaterIntake(Integer.parseInt(input));
            }
            
            System.out.print("📚 Study hours (current: " + todayStats.getStudyHours() + "): ");
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                todayStats.setStudyHours(Integer.parseInt(input));
            }
            
            System.out.print("👟 Steps taken (current: " + todayStats.getSteps() + "): ");
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                todayStats.setSteps(Integer.parseInt(input));
            }
            
            System.out.print("😴 Sleep hours (current: " + todayStats.getSleepHours() + "): ");
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                todayStats.setSleepHours(Integer.parseInt(input));
            }
            
            System.out.print("💰 Money spent $ (current: " + todayStats.getExpenses() + "): ");
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                todayStats.setExpenses(Integer.parseInt(input));
            }
            
            System.out.print("✅ All daily goals achieved? (y/n, current: " + (todayStats.isGoalsAchieved() ? "y" : "n") + "): ");
            input = scanner.nextLine().trim().toLowerCase();
            if (!input.isEmpty()) {
                todayStats.setGoalsAchieved(input.equals("y") || input.equals("yes"));
            }
            
            // Update pet based on new stats
            pet.updateMood(todayStats);
            pet.updateXP(todayStats);
            
            // Save data
            dataManager.addTodayStats(todayStats, userStatsList);
            dataManager.savePet(pet);
            
            System.out.println("\n✅ Stats updated! Your pet reacted to your habits!");
            
            // Show pet's reaction
            System.out.println("🐾 " + pet.getName() + " is now " + pet.getMood().getDisplayName().toLowerCase() + "!");
            System.out.println("💭 " + pet.getMood().getDescription());
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter numbers for numeric fields.");
        }
    }
    
    private void viewStatsHistory() {
        System.out.println("\n📊 Stats History");
        System.out.println("═══════════════════════════════════════");
        
        if (userStatsList.isEmpty()) {
            System.out.println("No stats recorded yet. Start by inputting today's habits!");
            return;
        }
        
        userStatsList.stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .limit(7) // Show last 7 days
                .forEach(stats -> {
                    System.out.println(stats);
                    System.out.println("Health Score: " + stats.getHealthScore() + "/100");
                    System.out.println("───────────────────────────────────────");
                });
    }
    
    private void savePet() {
        dataManager.savePet(pet);
        System.out.println("💾 Pet data saved successfully!");
    }
    
    public static void main(String[] args) {
        new ConsoleApp().run();
    }
}