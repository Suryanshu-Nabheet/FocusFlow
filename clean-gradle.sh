#!/bin/bash

echo "Cleaning Gradle cache and build files..."

# Stop Gradle daemon
./gradlew --stop

# Clean build directories
rm -rf .gradle
rm -rf app/build
rm -rf build
rm -rf app/.cxx

# Clean Gradle cache (optional - uncomment if needed)
# rm -rf ~/.gradle/caches/

echo "Clean complete! Please sync your project in Android Studio."

