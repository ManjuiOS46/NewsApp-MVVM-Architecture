# NewsApp — MVVM Architecture

An Android **News Application** built using modern Android development practices, following **MVVM architecture** and **Jetpack Compose**.
Fetches real-time news from [NewsAPI](https://newsapi.org) and presents it across multiple browsable screens with **offline support, pagination, and background sync**.

<p align="center">
  <img width="400" height="600" alt="NewsApp Banner" src="https://github.com/user-attachments/assets/74d9038e-513a-4010-b1b1-a53e36a9fd75" />
</p>

---

## ✨ Features

- 📰 Browse **top headlines** from around the world
- 🌍 Filter news by **country**, **language**, or **source**
- 🔍 **Search** for any topic using the Everything endpoint
- 📦 **Offline-first** — articles cached in Room Database and available without internet
- 📄 **Pagination** on the Top Headlines screen
- ⏰ **Background sync** via WorkManager every morning
- 🌐 Opens articles in **Chrome Custom Tabs** for a seamless reading experience
- ✅ Full **loading / success / error + retry** state handling on every screen
- 🌐 Language screen lets you pick **two languages simultaneously** — results are zipped, mixed, and randomised

---

## 📸 Screenshots

<p align="center">
  <img width="216" height="444" alt="Home" src="https://github.com/user-attachments/assets/f73cb726-5c81-4a35-a6d7-5ef17f1f289c" />
  <img width="216" height="444" alt="Languages" src="https://github.com/user-attachments/assets/a19b6cb3-a57f-496a-af2e-76242fd3034f" />
  <img width="216" height="444" alt="Top Headlines" src="https://github.com/user-attachments/assets/4a7c383f-4d2a-420d-b84c-748ee597b001" />
  <img width="216" height="444" alt="Countries" src="https://github.com/user-attachments/assets/5ddb021b-f7eb-429d-aff5-d02597d6eb0a" />
  <img width="216" height="444" alt="Search" src="https://github.com/user-attachments/assets/61212893-8625-4710-82b6-3a6f946ee106" />
</p>

---

## 🛠️ Tech Stack & Dependencies

| Library | Purpose |
|---|---|
| **Jetpack Compose** | Modern UI toolkit for building native Android UIs |
| **Glide** | Efficient image loading and caching |
| **Retrofit** | Type-safe HTTP client for network requests |
| **Dagger Hilt** | Dependency injection |
| **Room** | SQLite object mapping for local data storage |
| **Paging Compose** | Simplified implementation of paginated lists |
| **WorkManager** | Background sync scheduling |
| **Mockito & JUnit 4** | Unit testing |

---

## 🚀 How to Run

1. **Get API Key** — Obtain a free API key from [newsapi.org](https://newsapi.org)
2. **Add API Key** — Add the obtained API key to the `AppUtils.kt` file in the project
3. **Build and Run** — Build and run the app on an emulator or physical device

---

## 📁 Project Structure

```
├── NewsApplication.kt
├── data
│   ├── local
│   │   ├── AppDatabase.kt
│   │   ├── AppDatabaseService.kt
│   │   ├── DatabaseService.kt
│   │   ├── dao
│   │   └── entity
│   ├── network
│   │   ├── api
│   │   └── models
│   ├── pagination
│   │   └── TopHeadlineRemoteMediator.kt
│   └── repository
│       └── TopHeadlineRepository.kt
├── di
│   ├── module
│   │   └── ApplicationModule.kt
│   └── qualifiers.kt
├── ui
│   ├── MainActivity.kt
│   ├── MainScreen.kt
│   ├── UIState.kt
│   ├── component
│   │   ├── ApiArticleItem.kt
│   │   ├── ArticleItem.kt
│   │   ├── ErrorScreen.kt
│   │   ├── LoadingScreen.kt
│   │   ├── NewsListScreen.kt
│   │   ├── OfflineNewsListScreen.kt
│   │   └── PaginationNewsListScreen.kt
│   ├── countries
│   │   ├── CountriesScreen.kt
│   │   └── CountriesViewModel.kt
│   ├── language
│   │   ├── LanguageScreen.kt
│   │   └── LanguageViewModel.kt
│   ├── navigation
│   │   ├── AppNavGraph.kt
│   │   └── Routes.kt
│   ├── newssources
│   │   ├── NewsSourcesScreen.kt
│   │   └── NewsSourcesViewModel.kt
│   ├── offlinearticles
│   │   ├── OfflineArticleScreen.kt
│   │   └── OfflineArticlesViewModel.kt
│   ├── search
│   │   ├── SearchScreen.kt
│   │   └── SearchViewModel.kt
│   ├── theme
│   │   └── Theme.kt
│   ├── topheadline
│   │   ├── TopHeadlineScreen.kt
│   │   └── TopHeadlineViewModel.kt
│   └── topheadlinepagination
│       ├── TopHeadlinePaginationScreen.kt
│       └── TopHeadlinePaginationViewModel.kt
├── utils
│   ├── AppUtils.kt
│   ├── DispatcherProvider.kt
│   └── NetworkHelper.kt
└── worker
    └── ArticleUpdateWorker.kt
```

---

## 🤝 Contributing

Feel free to improve or add features to the project. Create an issue or find a pending one. All pull requests are welcome! 😄

If this project helps you, show some love ❤️ by putting a ⭐ on this project ✌️
