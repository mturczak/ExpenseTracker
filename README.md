# 💰 Expense Tracker

A modern Android application for tracking personal expenses with categories, statistics, and visualizations.

## 📱 Features

- **Add Expenses**: Easily add new expenses with amount, category, description, and date
- **Edit & Delete**: Modify or remove existing expenses with a simple tap
- **Categories**: Organize expenses into predefined categories:
  - 🍔 Food & Dining
  - 🚗 Transportation
  - 🏠 Housing
  - 🎮 Entertainment
  - 🏥 Healthcare
  - 🛒 Shopping
  - 💼 Other
- **Statistics**: View expense breakdown by category with interactive charts
- **Total Tracking**: Monitor your total expenses in real-time
- **Date Selection**: Choose custom dates for each expense entry

## 🛠️ Technologies Used

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI**: 
  - Material Design 3
  - View Binding
  - Fragment-based navigation
  - Bottom Navigation
- **Database**: Room (SQLite)
- **Async Operations**: Kotlin Coroutines & Flow
- **Lifecycle**: LiveData & ViewModel
- **Charts**: MPAndroidChart for statistics visualization

## 📋 Requirements

- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 24 or higher (Target SDK 36)
- Kotlin 1.9+
- Gradle 8.0+
- JDK 11 or higher

## 🚀 Getting Started

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/ExpenseTracker.git
cd ExpenseTracker
```

2. Open the project in Android Studio

3. **Important**: Fix JAVA_HOME if needed:
   - If you encounter build errors related to JAVA_HOME, ensure it's correctly set
   - For macOS/Linux: `export JAVA_HOME=/path/to/your/jdk`
   - For Windows: Set JAVA_HOME in System Environment Variables

4. Sync the project with Gradle files

5. Run the app on an emulator or physical device

### Building

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test
```

## 📱 App Structure

```
app/src/main/java/com/example/expensetracker/
├── data/
│   ├── dao/                    # Data Access Objects
│   │   └── ExpenseDao.kt
│   ├── database/               # Room Database setup
│   │   └── ExpenseDatabase.kt
│   ├── model/                  # Data models
│   │   └── ExpenseEntity.kt
│   └── repository/             # Repository pattern
│       └── ExpenseRepository.kt
├── ui/
│   ├── adapter/                # RecyclerView Adapters
│   │   ├── ExpenseAdapter.kt
│   │   └── CategoryStatAdapter.kt
│   ├── fragment/               # UI Fragments
│   │   ├── ExpensesFragment.kt
│   │   └── StatisticsFragment.kt
│   └── viewmodel/              # ViewModels
│       └── ExpenseViewModel.kt
├── utils/                      # Utility classes
│   └── ExpenseCategory.kt
└── MainActivity.kt             # Main Activity
```

## 🎨 Screenshots

<!-- Add screenshots here -->
*Screenshots coming soon*

## 📊 Database Schema

### ExpenseEntity
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key (auto-generated) |
| amount | Double | Expense amount |
| category | String | Expense category |
| description | String | Expense description |
| date | Long | Timestamp of the expense |

## 🔄 Data Flow

1. **User Input** → Fragment receives user interaction
2. **ViewModel** → Processes business logic
3. **Repository** → Handles data operations
4. **DAO** → Executes database queries
5. **Room Database** → Stores data persistently
6. **LiveData/Flow** → Notifies UI of changes
7. **UI Update** → Fragment displays updated data

## 🧪 Testing

The project includes:
- Unit tests for ViewModels and Repository
- Instrumented tests for Database operations

Run tests with:
```bash
./gradlew test           # Unit tests
./gradlew connectedCheck # Instrumented tests
```

## 📝 Known Issues

- Ensure JAVA_HOME is correctly set without extra characters
- Kapt currently falls back to language version 1.9 (expected behavior)

## 🔮 Future Enhancements

- [ ] Export expenses to CSV/PDF
- [ ] Budget limits and notifications
- [ ] Recurring expenses
- [ ] Multiple currencies support
- [ ] Data backup and restore
- [ ] Dark mode improvements
- [ ] Search and filter functionality
- [ ] Monthly/yearly reports

## 👤 Author

Miłosz Turczak

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) for chart visualization
- Material Design Guidelines
- Android Jetpack Libraries
