# 🐾 Digital Pet - Your Life Companion

A Java-based virtual pet that evolves and reacts based on your real-life daily habits! Watch your adorable digital companion grow as you maintain healthy habits and achieve your goals.

## ✨ Features

### 🎭 Dynamic Pet Moods
Your pet's mood changes based on your daily activities:
- 😊 **Happy** - When you drink enough water and study
- 🤓 **Smart** - When you focus on learning
- 😴 **Sleepy** - When you don't get enough exercise
- 😟 **Worried** - When you overspend
- 🎉 **Celebrating** - When you achieve all your goals
- 😵 **Dizzy** - When you don't get enough sleep
- 😄 **Energized** - When you sleep well and stay hydrated

### 📈 Evolution System
Your pet evolves through 5 stages based on XP:
1. **Egg** (Level 1)
2. **Baby Pet** (Level 2) 
3. **Teen Pet** (Level 3)
4. **Adult Pet** (Level 4)
5. **Legendary Form** (Level 5+)

### 💪 Habit Tracking
Track daily habits that affect your pet:
- 💧 Water intake (glasses)
- 📚 Study hours
- 👟 Steps taken
- 😴 Sleep hours
- 💰 Money spent
- ✅ Daily goals achieved

### 💾 Data Persistence
- Automatic saving of pet data and statistics
- JSON-based storage for easy backup
- Historical stats tracking

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Installation & Running

1. **Clone or download the project**
2. **Navigate to the project directory**
3. **Run the application:**
   ```bash
   mvn clean javafx:run
   ```

### Alternative: Compile and run manually
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="digitalpet.DigitalPetApp"
```

## 🎮 How to Play

1. **Launch the app** - Your pet starts as an egg named "Buddy"
2. **Input daily stats** - Click "📝 Input Today's Stats" to record your habits
3. **Watch your pet react** - Your pet's mood and XP change based on your input
4. **Track progress** - View your stats history and pet evolution
5. **Save regularly** - Use "💾 Save Pet" to preserve your progress

## 🏗️ Project Structure

```
src/main/java/digitalpet/
├── DigitalPetApp.java      # Main JavaFX application
├── Pet.java                # Core pet logic and evolution
├── Mood.java               # Pet mood enumeration
├── UserStats.java          # Daily habit tracking
├── DataManager.java        # JSON save/load functionality
└── LocalDateAdapter.java   # Date serialization helper
```

## 🎯 XP System

### Positive XP Gains:
- Study hours: +20 XP per hour
- Water intake (8+ glasses): +15 XP
- High step count (8000+): +25 XP, (5000+): +15 XP
- Good sleep (7-9 hours): +10 XP
- All goals achieved: +50 XP

### XP Penalties:
- Overspending (>$500): -10 XP
- Low activity (<3000 steps): -15 XP
- Poor sleep (<6 hours): -10 XP

## 🔧 Customization Ideas

Want to extend the project? Try adding:
- **Custom pet names and types**
- **Achievement system**
- **Pet animations and sounds**
- **Mini-games for bonus XP**
- **Social features (compare pets with friends)**
- **More detailed habit categories**
- **Pet accessories and customization**

## 📊 Technical Details

- **Language:** Java 11+
- **UI Framework:** JavaFX 17
- **Data Storage:** JSON (Gson library)
- **Build Tool:** Maven
- **Architecture:** Object-oriented with clean separation of concerns

## 🤝 Contributing

This is a perfect project for learning and extending! Feel free to:
- Add new pet moods and reactions
- Implement animations
- Create new evolution stages
- Add sound effects
- Improve the UI design

## 📝 License

This project is open source and available under the MIT License.

---

**Happy pet raising! 🐾✨**

*Remember: A healthy you means a happy pet!*