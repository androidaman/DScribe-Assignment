# Interview Task: Image Insertion and Manipulation in a Drawing App

## Objective
The goal of this task is to evaluate the candidate's ability to:
- Integrate `SurfaceView` within Jetpack Compose.
- Handle bitmap operations, including loading, scaling, and drawing.
- Implement image manipulation (pan, zoom, rotate).
- Manage drawing operations on a separate thread.

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

7. **Code Quality**:
   - Prioritize clean, well-organized, and maintainable code.
   - Use proper class and function decomposition, clear naming conventions, and comments where necessary.

## Evaluation Criteria:
- Correctness of the implementation.
- Handling of user interactions.
- Efficiency of the drawing process.
- Code organization, readability, and maintainability.
- Proper use of Jetpack Compose and `SurfaceView`.
- Handling of concurrency.

## How to Run:
1. Clone the repository:
   ```bash
   git clone https://github.com/androidaman/DScribe-Assignment.git
