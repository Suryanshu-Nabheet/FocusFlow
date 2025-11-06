# FocusFlow 📱

A modern, feature-rich productivity Android application built with Jetpack Compose. FocusFlow helps you manage your notes, tasks, calendar, and time with a beautiful dark-themed interface.

## ✨ Features

### 📝 Notes Management
- Create, edit, and delete notes
- Color-coded note tags for better organization
- Rich text content support
- Automatic timestamp tracking (created/updated)

### ✅ Task Management
- Create and manage tasks with descriptions
- Mark tasks as complete/incomplete
- Set due dates and reminder times
- Priority levels (Low, Medium, High)
- Track task creation timestamps

### 📅 Calendar View
- Visual calendar interface
- View your scheduled tasks and events

### ⏱️ Timer
- Built-in timer functionality
- Focus and productivity tracking

### 🎨 Modern UI/UX
- Beautiful dark theme with electric blue accents
- Material Design 3 components
- Edge-to-edge display support
- Smooth navigation with bottom navigation bar
- Responsive and intuitive interface

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern declarative UI framework
- **Material Design 3** - Latest Material Design components

### Architecture & Libraries
- **MVVM Architecture** - Clean separation of concerns
- **Room Database** - Local data persistence
- **Hilt** - Dependency injection
- **Navigation Compose** - Type-safe navigation
- **Coroutines** - Asynchronous programming
- **Lifecycle Components** - Lifecycle-aware components

### Key Dependencies
- `androidx.compose.material3` - Material Design 3
- `androidx.room` - Database persistence
- `dagger.hilt` - Dependency injection
- `androidx.navigation.compose` - Navigation
- `kotlinx.coroutines` - Coroutines support

## 📋 Prerequisites

Before you begin, ensure you have the following installed:
- **Android Studio** (Hedgehog or later recommended)
- **JDK 11** or higher
- **Android SDK** with API level 24 (Android 7.0) or higher
- **Gradle** 8.13.0 or compatible version

## 🚀 Getting Started

### Clone the Repository
```bash
git clone https://github.com/yourusername/FocusFlow.git
cd FocusFlow
```

### Open in Android Studio
1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the cloned FocusFlow directory
4. Wait for Gradle sync to complete

### Build the Project
```bash
./gradlew build
```

### Run the App
1. Connect an Android device or start an emulator
2. Click "Run" in Android Studio, or
3. Run from command line:
```bash
./gradlew installDebug
```

## 📱 Minimum Requirements

- **Minimum SDK**: 24 (Android 7.0 Nougat)
- **Target SDK**: 36
- **Compile SDK**: 36

## 🏗️ Project Structure

```
app/src/main/java/com/android/focusflow/
├── data/
│   ├── dao/              # Room Data Access Objects
│   │   ├── NoteDao.kt
│   │   └── TaskDao.kt
│   ├── database/         # Database configuration
│   │   ├── DatabaseModule.kt
│   │   └── FocusFlowDatabase.kt
│   └── model/            # Data models
│       ├── Note.kt
│       └── Task.kt
├── navigation/           # Navigation setup
│   └── FocusFlowNavigation.kt
├── repository/           # Data repositories
│   ├── NoteRepository.kt
│   └── TaskRepository.kt
├── ui/                   # UI screens and components
│   ├── calendar/
│   │   └── CalendarScreen.kt
│   ├── notes/
│   │   ├── AddEditNoteScreen.kt
│   │   └── NotesScreen.kt
│   ├── tasks/
│   │   ├── AddEditTaskScreen.kt
│   │   └── TasksScreen.kt
│   ├── theme/            # Theme configuration
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── timer/
│       └── TimerScreen.kt
└── viewmodel/            # ViewModels
    ├── CalendarViewModel.kt
    ├── NotesViewModel.kt
    ├── TasksViewModel.kt
    └── TimerViewModel.kt
```

## 🎨 Theme & Design

FocusFlow features a modern dark theme with:
- **Primary Color**: Electric Blue (#1E90FF)
- **Background**: Pure Black (#000000)
- **Surface**: Dark Gray (#1A1A1A)
- **Accent Colors**: Various color tags for notes

## 📦 Dependencies

All dependencies are managed through `gradle/libs.versions.toml` for centralized version control. Key dependencies include:

- AndroidX Core KTX
- Jetpack Compose BOM
- Room Database
- Hilt for Dependency Injection
- Navigation Compose
- Coroutines

## 🔧 Configuration

### Build Configuration
- **Namespace**: `com.android.focusflow`
- **Application ID**: `com.android.focusflow`
- **Version Code**: 1
- **Version Name**: 1.0

### Gradle Setup
The project uses:
- Kotlin DSL for build scripts
- Version catalogs for dependency management
- KAPT for annotation processing (Hilt)

## 🧪 Testing

Run unit tests:
```bash
./gradlew test
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/yourusername/FocusFlow/issues).

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 👤 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)

## 🙏 Acknowledgments

- Material Design 3 team for the amazing design system
- Jetpack Compose team for the modern UI framework
- Android development community

## 📸 Screenshots

_Add screenshots of your app here_

## 🔮 Future Enhancements

- [ ] Cloud sync functionality
- [ ] Note search and filtering
- [ ] Task categories and tags
- [ ] Pomodoro timer integration
- [ ] Widget support
- [ ] Export/Import functionality
- [ ] Dark/Light theme toggle
- [ ] Multi-language support

---

Made with ❤️ using Jetpack Compose

