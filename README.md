# RideMate: Native Android Ride-Sharing Platform

## 📌 Project Overview
RideMate is a native Android application built with Kotlin and Jetpack Compose, designed to coordinate shared rides between drivers and passengers. 

The application implements a robust **offline-first architecture**, utilizing the Repository pattern to synchronize local data with cloud services, ensuring seamless ride lifecycle management, real-time seat requests, and dynamic route mapping.

---

## 🚀 Core Architecture & Features
- **Offline-First Data Sync:** Orchestrates data flow between local persistence (Room SQLite) and cloud storage (Firebase Firestore).
- **Modern Declarative UI:** Fully built with **Jetpack Compose** and the Navigation Component for fluid, state-driven interfaces.
- **Location & Routing Services:** Deep integration with Google Maps SDK, Geocoding, and Directions APIs for custom pickup/drop-off point selection.
- **State Management:** Strict adherence to **MVVM (Model-View-ViewModel)** architecture for predictable UI state transitions and lifecycle management.
- **Authentication & Security:** Secure access flows utilizing Firebase Authentication (Email/Password & Google OAuth).

---

## ⚙️ Tech Stack
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture:** MVVM, Repository Pattern
- **Local Database:** Room (SQLite)
- **Backend & Sync:** Firebase (Firestore, Auth)
- **Network & APIs:** Retrofit, Google Maps SDK
