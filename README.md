# OctaSpace — Rental Property App

An Android app for discovering and listing rental properties. Browse homes on an interactive map, post your own property with photos and videos, chat with owners/tenants, and manage listings — all in one place.

## Features

- **Property feed** — browse rental listings with photos, videos, and reels-style media
- **Interactive map search** — Google Maps + Places autocomplete with filters (price, BHK, area, furnishing)
- **Post a property** — multi-step form with media upload to AWS S3 + CloudFront delivery
- **Chat & enquiries** — real-time messaging between tenants and owners (Firebase)
- **Push notifications** — Firebase Cloud Messaging for enquiries and updates
- **Profile & listings management** — drafts, rented-out marking, account settings
- **Deep links** — `toletspot.com` app links open directly in the app
- **Offline handling** — network monitoring with graceful no-internet states

## Tech Stack

| Layer | Tech |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM, ViewModel, StateFlow |
| Networking | Retrofit, OkHttp, Gson, kotlinx.serialization |
| Media | ExoPlayer (Media3), Coil, Glide, Lottie |
| Maps | Google Maps Compose, Places SDK, Fused Location |
| Backend | REST API, Firebase (Realtime DB, FCM, Crashlytics) |
| Storage | AWS S3 + CloudFront, DataStore Preferences |
| Native | JNI/C++ (CMake) for config injection |
| Navigation | Navigation Compose + Accompanist animations |

## Screenshots

_Add screenshots to the `screenshots/` folder and link them here._

<!-- Example:
| Home | Map Search | Post Property |
|---|---|---|
| ![](screenshots/home.png) | ![](screenshots/map.png) | ![](screenshots/post.png) |
-->

## Setup

### Prerequisites

- Android Studio (latest stable)
- Android SDK 36, NDK `28.2.13676358`, CMake `3.22.1`
- JDK 11+

### 1. Firebase

Place your `google-services.json` in `app/`. Get it from the [Firebase Console](https://console.firebase.google.com) for package `com.toletspot.houseforrent`.

### 2. Secrets — `local.properties`

Secrets are injected at build time and **never committed**. Add these to `local.properties` in the project root:

```properties
sdk.dir=/path/to/Android/sdk

MAPS_API_KEY=your_google_maps_api_key
AWS_ACCESS_ID=your_s3_access_key_id
AWS_SECRET_KEY=your_s3_secret_key
AWS_BUCKET_NAME=your_s3_bucket
CLOUDFRONT_URL=https://your-distribution.cloudfront.net
```

- `MAPS_API_KEY` → generated as `R.string.maps_api_key` (used by the manifest and Places SDK)
- `AWS_*` / `CLOUDFRONT_URL` → passed to C++ via CMake compile definitions, surfaced through JNI in `MainActivity`

### 3. Build

```bash
./gradlew assembleDebug
```

## Project Structure

```
app/src/main/
├── cpp/propreelz.cpp          # JNI bridge for build-time config
├── java/com/toletspot/houseforrent/
│   ├── MainActivity.kt        # Entry point, JNI companion, Places init
│   ├── API/                   # Retrofit services + data classes
│   ├── Home_Screen/           # Feed, post property, profile, video modules
│   ├── RentoNewScreens/       # Map view and related screens
│   ├── WebView/               # About, feedback, FAQ screens
│   ├── Custom_Assets/         # Shared UI components & utilities
│   └── Constants.kt           # App-wide constants
└── res/                       # Resources, strings, themes
```

## License

[MIT](LICENSE)
