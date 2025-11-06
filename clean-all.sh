#!/bin/bash

echo "=========================================="
echo "Comprehensive Gradle Cleanup Script"
echo "=========================================="
echo ""

# Stop Gradle daemon
echo "1. Stopping Gradle daemon..."
./gradlew --stop 2>/dev/null || true
echo "   ✓ Gradle daemon stopped"
echo ""

# Clean build directories
echo "2. Cleaning build directories..."
rm -rf .gradle
rm -rf app/build
rm -rf build
rm -rf app/.cxx
rm -rf .idea/caches
rm -rf .idea/modules.xml
echo "   ✓ Build directories cleaned"
echo ""

# Clean Gradle cache (optional but recommended)
echo "3. Cleaning Gradle cache..."
if [ -d "$HOME/.gradle/caches" ]; then
    echo "   Warning: This will delete ALL Gradle caches for all projects"
    read -p "   Do you want to continue? (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        rm -rf "$HOME/.gradle/caches"
        echo "   ✓ Gradle cache cleaned"
    else
        echo "   ⚠ Skipped cache cleanup"
    fi
else
    echo "   ⚠ Gradle cache directory not found"
fi
echo ""

# Clean Gradle wrapper cache
echo "4. Cleaning Gradle wrapper cache..."
rm -rf "$HOME/.gradle/wrapper" 2>/dev/null || true
echo "   ✓ Gradle wrapper cache cleaned"
echo ""

echo "=========================================="
echo "Cleanup complete!"
echo "=========================================="
echo ""
echo "Next steps:"
echo "1. Open Android Studio"
echo "2. File → Invalidate Caches / Restart"
echo "3. File → Sync Project with Gradle Files"
echo ""

