# FocusFlow - JavaPoet Fix Instructions

## ✅ Changes Made

1. **Downgraded Hilt** from 2.52 to 2.48.1 (more stable with Kotlin 2.0)
2. **Added explicit JavaPoet 1.13.0** to kapt dependencies
3. **Enhanced dependency resolution** to force JavaPoet 1.13.0 across all configurations
4. **Optimized Gradle properties** for better build performance
5. **Created cleanup script** (`clean-all.sh`) for comprehensive cache clearing

## 🔧 Step-by-Step Fix Instructions

### Step 1: Stop All Gradle Processes

**In Android Studio:**
- Go to: **File → Settings → Build, Execution, Deployment → Build Tools → Gradle**
- Click: **"Stop Gradle daemons"**

**Or in Terminal:**
```bash
./gradlew --stop
```

### Step 2: Clean All Build Files

**Option A - Use the cleanup script (Recommended):**
```bash
./clean-all.sh
```

**Option B - Manual cleanup:**
```bash
# Stop Gradle daemon
./gradlew --stop

# Remove build directories
rm -rf .gradle
rm -rf app/build
rm -rf build
rm -rf app/.cxx
```

### Step 3: Invalidate Caches in Android Studio

1. **File → Invalidate Caches / Restart**
2. Select **"Invalidate and Restart"**
3. Wait for Android Studio to restart completely

### Step 4: Clean Gradle Cache (If Issue Persists)

**Close Android Studio completely**, then:

```bash
# Navigate to your home directory
cd ~

# Remove Gradle cache (WARNING: This affects all projects)
rm -rf .gradle/caches/

# Or just remove corrupted transforms
rm -rf .gradle/caches/transforms-*
```

### Step 5: Sync Project

1. **File → Sync Project with Gradle Files**
2. Wait for all dependencies to download
3. This may take 5-10 minutes on first sync

### Step 6: Rebuild Project

1. **Build → Clean Project**
2. **Build → Rebuild Project**

## ✅ Verification

After following all steps, the build should:
- ✅ Compile without JavaPoet errors
- ✅ Successfully process Hilt annotations
- ✅ Generate all required classes

## 🐛 If Issues Persist

1. **Check Java Version**: Ensure you're using Java 17 (required for AGP 8.13.0)
   ```bash
   java -version
   ```

2. **Check Gradle Version**: Should be 8.13 or compatible
   ```bash
   ./gradlew --version
   ```

3. **Try Manual Dependency Resolution**:
   - Open: **File → Project Structure → Dependencies**
   - Search for "javapoet"
   - Ensure version is 1.13.0

4. **Check Build Logs**:
   - **View → Tool Windows → Build**
   - Look for any red error messages

## 📝 Technical Details

### Why This Fix Works

1. **Hilt 2.48.1** is more stable and tested with Kotlin 2.0
2. **JavaPoet 1.13.0** is the last version with `canonicalName()` method
3. **Explicit kapt dependency** ensures JavaPoet is on annotation processor classpath
4. **Dependency resolution strategy** forces correct version across all configurations

### Files Modified

- `gradle/libs.versions.toml` - Hilt version downgraded
- `app/build.gradle.kts` - Added JavaPoet resolution and kapt config
- `gradle.properties` - Optimized build settings
- `clean-all.sh` - Created cleanup script

## 🎉 Expected Result

After completing all steps, you should see:
- ✅ No JavaPoet errors
- ✅ Successful build
- ✅ All Hilt components working correctly
- ✅ App ready to run

---

**Need Help?** Check the build logs or verify all dependencies are correctly resolved.

