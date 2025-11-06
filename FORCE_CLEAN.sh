#!/bin/bash

echo "=========================================="
echo "FORCE CLEAN - This WILL delete Gradle cache"
echo "=========================================="
echo ""

# Stop all Gradle daemons
echo "1. Stopping ALL Gradle daemons..."
./gradlew --stop 2>/dev/null || true
pkill -f gradle || true
echo "   ✓ Gradle daemons stopped"
echo ""

# Delete local build files
echo "2. Deleting local build files..."
rm -rf .gradle
rm -rf app/build
rm -rf build
rm -rf app/.cxx
rm -rf .idea/caches
rm -rf app/.cxx
echo "   ✓ Local files cleaned"
echo ""

# Delete Gradle cache (THIS IS THE KEY FIX)
echo "3. DELETING GRADLE CACHE (This fixes the JavaPoet issue)..."
if [ -d "$HOME/.gradle/caches" ]; then
    rm -rf "$HOME/.gradle/caches"
    echo "   ✓ Gradle cache DELETED"
else
    echo "   ⚠ Cache directory not found"
fi
echo ""

# Delete Gradle daemon
echo "4. Deleting Gradle daemon..."
rm -rf "$HOME/.gradle/daemon" 2>/dev/null || true
echo "   ✓ Daemon cleaned"
echo ""

echo "=========================================="
echo "FORCE CLEAN COMPLETE!"
echo "=========================================="
echo ""
echo "NOW DO THIS:"
echo "1. Close Android Studio COMPLETELY"
echo "2. Open Android Studio"
echo "3. File → Invalidate Caches / Restart → Invalidate and Restart"
echo "4. File → Sync Project with Gradle Files"
echo "5. Wait for sync (5-10 minutes)"
echo ""

