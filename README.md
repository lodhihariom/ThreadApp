# ThreadApp

ThreadApp is a social media application built using **Kotlin**, **Jetpack Compose**, and **Firebase**. It allows users to register, log in, and view threads posted by other users. The app also supports uploading images and fetching user data.

## Features

- User Authentication (Sign Up and Login)
- Post Threads with Text and Images
- View Threads with User Details
- Firebase Realtime Database Integration
- Cloudinary for Image Uploads
- Jetpack Compose for UI Design

## Tech Stack

- **Language**: Kotlin
- **Framework**: Jetpack Compose
- **Backend**: Firebase Realtime Database
- **Image Hosting**: Cloudinary
- **Build Tool**: Gradle

## Screens

1. **Sign Up Screen**: Allows users to register by providing their details and uploading a profile picture.
2. **Login Screen**: Enables users to log in with their email and password.
3. **Home Screen**: Displays a list of threads with user details and timestamps.

## Project Structure
app/ ├── src/ │ ├── main/ │ │ ├── java/com/example/threadapp/ │ │ │ ├── Screen/ # UI Screens (SignUp, Login, Home) │ │ │ ├── ViewModel/ # ViewModels (AuthViewModel, HomeViewModel) │ │ │ ├── Model/ # Data Models (User, ThreadData) │ │ ├── res/ # Resources (Images, Layouts, etc.) ├── build.gradle # Gradle Build File
## Installation

1. Clone the repository:
   ```bash
   git clone <>
   cd ThreadApp
   
