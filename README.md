# News App ✨



## Overview

The News  App is a  project that aims to provide a consistent news reading
 including Android,  This project leverages Jetpack Compose


## Features & Technologies:

* Explore Top News Headlines:
    * Stay updated with the day's top news stories effortlessly.
* Read Detailed Content:
    * Dive deeper into your favorite news articles for comprehensive insights.
* Personalized Search:
    * Easily find news articles tailored to your interests.
* Save for Later:
    * Bookmark articles to read offline and enjoy uninterrupted reading pleasure.

* Declarative UI with Jetpack Compose
    * Shared UI components across Android and desktop.
    * Material Design and Material Design 3 support.
* Type-Safe Navigation & State Management:
  * Type-Safe navigation using Jetpack Compose Navigation.
* MVI Architecture:
    * Model-View-Intent pattern for separation of concerns.
    * ViewModel management for UI-related data.
* Robust Networking:
    * HTTP client-server communication with Ktor.
    * JSON serialization/deserialization and content negotiation.
* Efficient Image Loading:
    * Image loading and caching with Coil.
* Local Data Storage:
    * Local database management with Realm Database .
    * Key-value pair storage with DataStore.


## Libraries 🛠️


- [Ktor](https://ktor.io/docs/http-client-multiplatform.html) - Provides multiplatform libraries required to make
  network calls to the REST API.

- [Room](https://developer.android.com/kotlin/multiplatform/room) - The Room persistence library provides an abstraction
  layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
- [DataStore](https://developer.android.com/kotlin/multiplatform/datastore) - The DataStore library stores data
  asynchronously, consistently, and transactionally, overcoming some of the drawbacks of SharedPreferences
- [Navigation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html) - Navigation is
  a key part of UI applications that allows users to move between different application screens.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library support for Kotlin coroutines 
 
- [Common ViewModel](https://www.jetbrains.com/help/kotlin/compose-viewmodel.html) The Android
  ViewModel approach to building UI can be implemented in common code using Compose Multiplatform.
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) - Provides sets of libraries for various
  serialization formats eg. JSON, protocol buffers, CBOR etc.

## Getting Started

### Installation 🛠️

1. Clone this repository:
   ```
   (https://github.com/MontasierAbdallah/News_App)
   ```

2. Open in the latest version of Android Studio or intellij idea.
3. Before running the project, obtain an API key from [News Api](https://newsapi.org/) .
4. Add a `local.properties` file to the project root.
5. Place your News API key in `local.properties` file as `API_KEY` property.

```properties
API_KEY=YOUR_API_KEY
```

### Run the app on your device or emulator:

- For Android, run the `composeApp` module by selecting the `app` configuration. If you need help with the
  configuration








## Contributing 🤝

Feel free to contribute to this project by submitting issues, pull requests, or providing valuable feedback. Your
contributions are always welcome! 🙌

## ❤ Show your support

Give a ⭐️ if this project helped you!



Your generosity is greatly appreciated! Thank you for supporting this project.

## Connect with me



## Author

**Montaser Abdallah**
