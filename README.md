# Music Management 

## Introduction

App for Android based on the LastFM API. With the help of the app the user should be able to search for artists, pick one of their top
albums and save it locally.

Music management is a task for an Android position at AppsFactory. (https://www.appsfactory.de/en).

## Features by UI

### 1. **Main Screen**
   - All locally stored albums are shown here.
   - A tap on one of these albums opens a detail-page.
   - (Main and Detail) work without having an internet connection.
   - It's possible to open the search screen from this view.
   
### 2. **Search Page**
   - Possible to search for an artist on the LastFMApi.
   - The search results, which include found artists, are displayed in a list.
   - A selection of one list-item opens the Top Albums screen.
      
### 3. **Top Albums**
   - Find the best albums by an artist
   - Possible to store (and delete stored) albums locally.
   - Tap on an album opens the detail-page with the following information:
      a. Name
      b. Artist
      d. Tracks

## Tech Stack

### Core
- MVVM, Repository pattern and usecases
- Navigation Components [Single Activity] (https://developer.android.com/guide/navigation/get-started)
- 100% [Kotlin](https://kotlinlang.org/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) (structured concurrency)
- [Hilt](https://dagger.dev/hilt/) (DI)

### Local Persistence
- [Room DB](https://developer.android.com/training/data-storage/room) (SQLite ORM)

### Networking
- [Retrofit](https://square.github.io/retrofit/) (REST client)
- [Gson](https://github.com/google/gson) (JSON serialization)

### Other
- [OkHttp interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) (logs HTTP request/response)
- [Last.FM](https://www.last.fm/api) (Music Discovery API)
