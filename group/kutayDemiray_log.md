# CS102 ~ Personal Log page ~
****
## Kutay Demiray 
****

On this page I will keep a weekly record of what I have done for the CS102 group project. This page will be submitted together with the rest of the repository, in partial fulfillment of the CS102 course requirements.

### ~ 10.02.2020 - 16.02.2020 ~
Yağız and I had some free hours and we met in the library to brainstorm some project ideas. We asked others to make this an official meeting, but they had classes and couldn't come.

### ~ 20.04.2020 - 26.04.2020 ~
I met unofficially with Yağız on thursday afternoon before our official meeting with rest of the group. We looked at some other mobile app's Github to get a rough idea of how things work, but most of the project was
"activities" and "fragments," which we didn't know anything about. After we noticed that we know nothing about Android design, Yağız called some of his friends that had developed a mobile app before. They talked briefly
about how classes and databases work in a mobile app. After that, Yağız and I bought Android Studio courses form Udemy to learn more about how it works.

### ~ 27.04.2020 - 03.05.2020 ~
After we shared the work, I read the "Object-Oriented Design" part on the book to have a better idea about the design. Then I met with Cemhan and Yağız to talk about the detailed design.
We talked about which classes would be included and their contents. We designed some of the classes together in a text file, but it was difficult. I watched some Youtube videos about MVC and Observer design pattern before the
lab meeting with our TA on next week so I could have a better idea about the subject before asking questions.

### ~ 04.05.2020 - 10.05.2020 ~
We gave our TA the design file we had prepared last week, he said he'd take a look and give feedback in a few days. Meanwhile, we continued working on the classes. I implemented a simple SportsEvent on my own, Cemhan and Yağız
did the same for other few classes as well. I worked with Cemhan on the "EventList" class because it had most of the functionality. We discussed and wrote some of the class together, but I made some changes on my own after that.
I found the "Comparator" interface which seemed like the best candidate for sorting by title, date etc. I also thought about making EventList have a linked list so only a fixed number of events could be displayed on screen and
after that the user could go to the next page (node in the linked list), though I have not discussed this with others yet.

At the weekend, I started learning more about Firebase's Realtime Database, and made a simple app that can read and write data in the database. Even though this was a very simple app, it was still somewhat difficult and
took some time to get it right, so I suppose implementing these functions in the actual app will likely be way harder, but at least we now know how to do it. I uploaded this app into our GitHub repo as well, so that others
can learn how it works without watching tons of low quality Youtube tutorials as I did myself.

This week was busy in general and I had multiple assignments in other courses, so development was slower.

### ~ 11.05.2020 - 17.05.2020 ~
Development is still slow due to other assignments, but at least I've made some progress. I implemented the create event feature I had designed earlier into the actual app, and I'll get around to implementing the event list sometime this week as well. 

### ~ 18.05.2020 - 22.05.2020 ~
I implemented event list and fixed a few bugs on EventDate class and database operations throughout the app. We shot the demo video and uploaded it, although we had to speed it up and
cut some parts to fit everything we showed in 10 minutes. After we uploaded the video, I changed the FeedEvents class so that past events aren't shown (it was pretty simple actually,
I should have done it earlier but I suppose I forgot). I also noticed that users were sent to homepage even when they weren't logged in, so I notified Gökberk about the issue and he
fixed it. I also improved our GitHub README page by adding screenshots to show features.


****