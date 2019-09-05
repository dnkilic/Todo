## Todo

I choose a modular Gradle setup rather than a monolithic one. So that five developers may work on different features without depending on each other. Another advantage of this approach is decreasing the build time which is very important for big projects which are taking a long time to develop. It also helps us to use Dynamic Features and Instant Apps. One another advantage would be sharing the commons between app/tablet/wearable modules, so it'll decrease the code duplication as well.

Regarding architecture I choose MVVM. MVVM architecture is the official recommendation. With MVVM architecture we can use Android Architecture Components, this is the most important income of this selection. Coroutines have extension methods which work well with ViewModel`s. Another very important income of MVVM is the ease of testing. It is also the best option to share data among Fragments. One of the biggest advantages of ViewModel is retaining data during configuration changes.

In addition to architecture, I am using the repository pattern to consume data. So, the view does not aware of the source of data. In my case, I only have a database so for but in the future I may use another local/remote data sources without changing my views and view models. 

Also, I am using Dependency Injection, it grants compile-time safety, scalability and performance. I am using it with specific scopes, and whenever my view model cleared I am clearing my dependency graph.

During the design process of view, I follow the most recent paradigms. I am using DiffUtil
In my recycler view adapters as a smart way of updating recycler view items. For the selection of recycler view items, I used the selection library to not change my model class or adapter to support the selection of recycler view items. Since the app has ADD/DELETE/COMPLETE/SEARCH/EDIT actions I used ActionBar for almost all the action. User can set time or add the tag by using dialogs which can be triggered from ActionBar. 

To set the Alarms, I am using AlarmManager, BroadcastReceiver and NotificationManager.

Here I am providing screenshots of various actions;

# Detail Screen
Users can Create/Edit/View task from Detail screen
<p align="middle">
  <img src="/screenshots/create.png" width="150" />   
  <img src="/screenshots/edit.png" width="150" /> 
  <img src="/screenshots/error.png" width="150" />
</p>

Users can add label and set due date via Dialogs
<p align="middle">
  <img src="/screenshots/add_label.png" width="150" />   
  <img src="/screenshots/date_picker.png" width="150" /> 
</p>
 
 
# Dashboard Screen
Users can See/Search tasks from Detail screen
<p align="middle">
    <img src="/screenshots/shimmer.png" width="150" />   
    <img src="/screenshots/see_all.png" width="150" /> 
    <img src="/screenshots/search.png" width="150" /> 
    <img src="/screenshots/search_2.png" width="150" /> 
</p>

Users can Complete/Delete tasks from Detail screen
<p align="middle">
    <img src="/screenshots/complete_delete.png" width="150" />   
</p>

# Alarm
Users'll see Notification when their task deadline about to finish
<p align="middle">
    <img src="/screenshots/alarm.JPEG" width="150" />   
</p>