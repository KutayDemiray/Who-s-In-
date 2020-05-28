# CS102 ~ Personal Log page ~
****
## Cemhan Kaan Özaltan
****

On this page I will keep a weekly record of what I have done for the CS102 group project. This page will be submitted together with the rest of the repository, in partial fulfillment of the CS102 course requirements.

### ~ 04.03.2020 - 10.03.2020 ~
During our requirements stage meeting, I brainstormed with my group about our app's features and wrote the introduction part of the report.

### ~ 27.03.2020 - 03.04.2020 ~
During our UI design stage meeting, I talked about our app's UI with my group and wrote the introduction part of the report.

### ~ 20.04.2020 - 26.04.2020 ~
After discussing our detailed design with my group, I looked up how Android Studio and databases work on the internet and watched some tutorials on YouTube. Because android development works differently than what we have seen so far, trying to learn it was quite complicated.

### ~ 27.04.2020 - 03.05.2020 ~
After deciding which parts of the project everyone will be doing, I talked with Kutay and Yağız about the architecture of our app. We decided on our required classes and their properties and methods. In order to use the MVC pattern, I looked at the Hangman lab and tried to think of a way to integrate its structure to our app. As what we are creating is relatively large, it was not easy to understand how we were going to implement the model classes. I further looked on the internet to better understand how we can use MVC in our app.

### ~ 04.05.2020 - 10.05.2020 ~
I started implementing some of the classes along with Kutay and Yağız. I created an Event class with basic functionalities such as adding and removing users. Even though we were initially going to use instances of this class for standard meetings, we decided to make it abstract and extend the MeetingEvent class. I made the Event class Observable in order to use the MVC pattern and make our app's architecture more organised, but we later realised that this didn't work with the android development pattern. After that, I created a BoardGameEvent class which extended the Event class and had pre-determined board game types. I also worked on the EventList class that allowed searching a collection of events with Kutay. We further worked on some other classes together. We tried to test the classes we implemented so far using a text based program and changed some properties and methods that did not work as they were supposed to.

### ~ 11.05.2020 - 17.05.2020 ~
I did a minor bug fix in the Event class in order to add the organizer of the event to the participants list on creation. We realized that the subclasses of the Event class did not serve a significant purpose, so we decided to delete them and use instances of Event for every category. I modified the Event class in order to be able to use the database functions and store the ID's of participants and the event. We designed a UML diagram with Kutay and Yağız and prepared our detailed design report with our group.

I unfortunately couldn't get more work done this week due to other assignments that I had to do.

### ~ 18.05.2020 - 24.05.2020 ~
I added a small fix to the MainActivity class in order to show the HomeFragment when the app is opened instead of an empty page. We tried implementing methods that would return Event and User objects from our realtime database with Kutay in order to make certain operations easier in the activities and fragments. After trying several ways of implementing these methods, we realized that it could not be done because Firebase is an asynchronized database and its listeners only accept constants inside. I added a fix to EventActivity to show the username of the organizer instead of their ID. I worked on the HomeFragment, EventAdapter and FeedEvents classes and used Android's Intent class in order to create the transitions to different activities.

As we closed to the end of our project, I did overall convention corrections to make our code consistent and suitable for the CS102 style guidelines. Then, we recorded our demo video through zoom and prepared the second version of our detailed design report. We finalized our project with some changes on our GitHub page and a few bug fixes.

****
