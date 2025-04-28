# Task: Image Insertion and Manipulation in a Drawing App


https://github.com/user-attachments/assets/13406fa1-ccfd-4796-bc9e-5c6754211840



## Task Requirements

1. **Single-Page Compose UI**:
   - Create a single-screen Android application using Jetpack Compose for the UI.

2. **SurfaceView Integration**:
   - Embed a `SurfaceView` within your Compose layout for displaying and drawing on a bitmap.

3. **Bitmap Handling**:
   - Initialize the drawing bitmap with the same resolution as the device's screen.

4. **Image Selection**:
   - Implement functionality to fetch a random image from the device's storage. Assume storage read permissions are granted.
   - No need for a complex image picker UI; a simple image fetching solution is sufficient.

5. **Image Transformation**:
   - The user should be able to pan, zoom, and rotate the image interactively.
   - After the user finalizes the placement (e.g., pressing "Insert"), the transformed image should be permanently drawn onto the bitmap.

6. **Drawing on Bitmap**:
   - Perform the final drawing (after user confirms placement) of the transformed image onto the `SurfaceView`'s bitmap on a background thread.
   - Real-time manipulation (pan, zoom, rotate) can remain on the main UI thread to keep the UI responsive.
## Technologies Used

### 1. **Kotlin**
- **Description**: Kotlin is a modern, expressive, and safe programming language used for developing Android applications. It offers features such as null safety, extension functions, and concise syntax, making it a preferred choice for Android development.
- **Why Used**: Kotlin allows for writing clean, concise, and maintainable code for Android apps, and it has official support from Google for Android development.

### 2. **Jetpack Compose**
- **Description**: Jetpack Compose is a modern, fully declarative UI toolkit for Android, designed to simplify UI development. It allows for building UIs using Kotlin code without the need for XML layouts.
- **Why Used**: Jetpack Compose enables us to create dynamic, responsive UIs with less boilerplate code. It integrates seamlessly with Kotlin, enhancing productivity and scalability.

### 3. **ActivityResultContracts**
- **Description**: ActivityResultContracts is a part of Android's ActivityResult API, which simplifies managing activity result contracts like permissions, camera access, or file selections.
- **Why Used**: The app uses `ActivityResultContracts` to handle permissions and requests, making the code cleaner and easier to manage. It replaces the older `onActivityResult` approach and is more intuitive.

### 4. **Material3**
- **Description**: Material3 is the latest version of Google's Material Design UI system for Android, which provides components, themes, and styles for building aesthetically pleasing and consistent UIs.
- **Why Used**: Material3 gives the app a modern, polished look, improving the user experience with its rich set of UI components such as buttons, dialogs, text fields, and more.

## Setup Instructions

### Prerequisites
- Android Studio (Arctic Fox or higher)
- Kotlin 1.5 or higher

## How to Run:
1. Clone the repository:
   ```bash
   git clone https://github.com/androidaman/DScribe-Assignment.git
