# GalleryThief

Android Application to "Steal" - download- images from any web page.

Some details for using the app:
- Insert a valid url, for example "http://www.google.es", and press the search button on the keyboard.
- A floating button appears on the bottom right corner and you can start "stealing" the images from de Html document. Only elements with tag "img" are "stolen".
- The next screen shows you the "stolen" images and you can click on the footer images.
- On the modal sheet that appears you can save the image on the external storage SD Card.

Libraries and technologies used:

- MVVM pattern.
- Room to store the url images.
- Jetpack Compose for the UI.
- Clean arquitechture applied.
- Glide for showing the url images.
- Hilt for inyection.
- Accompanist library to use the latest WebView and its rememberWebViewState.
