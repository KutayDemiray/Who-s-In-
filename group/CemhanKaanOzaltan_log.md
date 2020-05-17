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

****
