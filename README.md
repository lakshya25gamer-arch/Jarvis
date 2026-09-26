# JARVIS V3 Android

Android implementation of the useful Concept Bytes JarvisV3 functionality, adapted from the provided JarvisV3 source archive.

## Included
- JARVIS-branded Android Compose UI
- Chat tab
- Realtime/session architecture
- OpenAI Realtime WebSocket client foundation
- Structured tool registry
- Date/time tool
- Remember / recall / forget memory
- Settings/API-key connection screen
- Mobile-safe architecture; no arbitrary shell execution
- Original V3 logo/avatar assets adapted into Android resources

## Deliberate mobile changes
The original V3 contains desktop-specific `run_os_command`, PyAutoGUI, terminal printing and Bambu printer automation. Those are not copied into Android because they depend on a desktop OS and unrestricted execution. They should be replaced with explicit Android capabilities and confirmation-gated actions.

## Build
Requires JDK 17, Android SDK 35, and Gradle 8.9.

```bash
gradle wrapper --gradle-version 8.9
./gradlew :app:testDebugUnitTest
./gradlew :app:assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

## Important
This project was generated from the supplied Concept Bytes V3 source as an Android adaptation. The build was not executed in the generation environment because an Android SDK/Gradle toolchain is not available there.


## GitHub APK build

This repository includes a GitHub Actions workflow at `.github/workflows/build-apk.yml`.
After pushing the repository to GitHub, the workflow builds the Android debug APK automatically.

1. Open the **Actions** tab.
2. Select **Build JARVIS V3 Android APK**.
3. Wait for the workflow to finish.
4. Open the completed run.
5. Download the **JarvisV3-debug-apk** artifact.
6. The artifact contains `app-debug.apk`.

The `concept-bytes-v3/` directory contains the supplied Concept Bytes V3 source used as the behavioral/reference source for this Android adaptation.

### Important
The Android implementation is an adaptation, not a verbatim Android port of the Python/Streamlit app. Desktop-only functions from the original V3 are not exposed as arbitrary Android shell execution.
