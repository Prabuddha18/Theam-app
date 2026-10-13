# DEKU Theme Studio

A native Android personalization and theme studio application for Android (optimized for OxygenOS / OnePlus Nord CE 4 Lite 5G and modern Android 14+ devices). Built with **Kotlin** and **Jetpack Compose** following Material Design 3 and a dot-matrix minimalist visual identity.

---

## Features

- **Theme Studio**: Preview and customize themes inspired by Nothing OS, Google Pixel Material You, Cyberpunk, AMOLED Black, Minimal, and custom palettes.
- **Home Dashboard**: Dot-matrix digital clock, real-time date display, active theme indicator, and quick customization actions.
- **Wallpaper Engine**: Bundle presets (Obsidian Monolith, Paper Blueprint, Ember Horizon, Nord CE Studio) plus custom system photo picker integration with system wallpaper management APIs.
- **Icon Studio**: High-contrast geometric vector icon previews and launcher compatibility guides.
- **Widget Studio**: Real Android AppWidget implementations (Dot-matrix clock, compact clock, notes widget) via AppWidgetProvider and RemoteViews.
- **Honest System Transparency**: Explains Android security constraints and OEM limitations without false root claims or broken hacks.
- **Step-by-Step Sideload Guide**: Built-in instructions tailored for OxygenOS and the OnePlus Nord CE 4 Lite 5G.

---

## Tech Stack & Architecture

- **Language:** Kotlin 2.0+
- **UI Framework:** Jetpack Compose (Material 3)
- **Architecture:** MVVM (Model-View-ViewModel) with StateFlow
- **Widgets:** Android AppWidgetProvider & RemoteViews
- **Local Persistence:** SharedPreferences / DataStore for persistent theme selections
- **Minimum SDK:** API 26 (Android 8.0)
- **Target SDK:** API 34+ (Android 14)

---

## How to Build & Run Locally

### Prerequisites
- Android Studio Ladybug / Koala or newer
- JDK 17+
- Android SDK 34+

### Clone & Build
```bash
git clone <your-repository-url>
cd <repository-folder>
./gradlew assembleDebug
```
The APK will be generated at:
`app/build/outputs/apk/debug/app-debug.apk`

### Run Tests
```bash
./gradlew testDebugUnitTest
```
