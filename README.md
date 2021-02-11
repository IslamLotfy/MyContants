# MyContants app to read contacts from phone-book and sync it with server ( but now just write it to local db)
-retrieving Contacts list from ContactProvider with ContentResolver
-add Dagger Impl with Repository pattern and ViewModel
-use kotlin Coroutines to load Contacts asynchronously 
-add room database with it's module and dao and model
-insert contacts into database and read it and deliver it to viewModel via Repo ( as single source of truth)
-add serviceWatcher and contentObserver to observe changes in contacts even when the app is in the background or terminated 
-add lastUpdated field to contact and write it's max value to sharedPref to read it easily in contentObserver
-inject repository into service to move the data update to it
-update the rows of database when changed from phone-book