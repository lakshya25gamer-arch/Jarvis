# JARVIS V3 — GitHub Upload Guide

This archive is prepared with the Android project at the repository root.

## Important
Do NOT upload this ZIP as a single file if you want GitHub Actions to build the APK.

1. Extract this ZIP on your phone/computer.
2. Open your GitHub repository.
3. Upload the extracted files and folders so that these paths are at the repository root:
   - `app/`
   - `.github/workflows/build-apk.yml`
   - `build.gradle.kts`
   - `settings.gradle.kts`
   - `gradle.properties`
   - `.gitignore`
   - `README.md`
   - `CONCEPT_BYTES_ATTRIBUTION.md`
   - `concept-bytes-v3/`
4. Commit the files to the `main` branch.
5. Open the repository's **Actions** tab.
6. The **Build JARVIS V3 Android APK** workflow should build the debug APK.

The Android project and the supplied Concept Bytes V3 reference source are kept in separate directories.
