# EventSync

## Project Overview
EventSync is a native Android application designed to streamline campus event management. It provides a centralized, role-based platform for two types of users:
* **Organizers:** Can create, edit, manage, and delete campus events.
* **Participants:** Can discover, view, and join events happening around campus.

## Architecture & Tech Stack
This application strictly follows the **MVVM (Model-View-ViewModel)** architecture to ensure separation of concerns, scalability, and clean code practices.
* **Language:** Java
* **UI/Design:** XML, Material Design Components, RecyclerView
* **Local Database:** SQLite (Offline-first data persistence)
* **Architecture Components:** Android Jetpack (ViewModel, LiveData)

## Setup Instructions
1. Clone this repository to your local machine using `git clone [repository_url]`.
2. Open the project in **Android Studio**.
3. Allow Gradle to sync and download the required dependencies.
4. Run the application on an Android Emulator (API 24+) or a physical device.

## Challenges and Solutions (Demo 3 & 4)
* **Challenge 1: Build Configuration & Asset Management**
    * **Issue:** Integration of team-contributed code resulted in build failures due to missing AndroidX configurations and deleted/untracked launcher icons.
    * **Solution:** Created `gradle.properties` to enable AndroidX/Jetifier and generated placeholder adaptive icons to restore project buildability.
* **Challenge 2: Integration of Disconnected Modules**
    * **Issue:** New Participant UI screens were not registered in the `AndroidManifest.xml`, and the starting `MainActivity` was still a blank placeholder.
    * **Solution:** Manually registered all new activities and updated `MainActivity` to automatically initialize sample SQLite data and redirect to the `ParticipantDashboardActivity`.
