# WeathermanCGI
A simple weather application based on Angular + Spring Boot for CGI

1. Any assumptions you made that were not clearly written in the task description
- It was not really understandable what kind of functionality should we implement in 3 and 4 stages. I mean in which way it should be implemented to not make application to complex and create for example db with further configuration or should client have option to download results on his pc or have user profile and save it there. In my personal opinion first 2 stages was described pretty well and more understandable.
2. How to deploy/run your solution (Someone who knows nothing about the solution should be able to deploy it using your instructions)
- To start an application first you should star backend in (springboot-weatherman-backend) folder by just clicking run button.
- Then open (angular-weatherman-frontend) folder and write these commands to cli to install all needed modules
~~~sh
npm install @googlemaps @bootstrap font-awesome @jquery @popper @rxjs @angular @angular/google-maps tslib --save-dev 
~~~
- After that just start local server using this command
~~~sh
npm start
~~~
3. How you solved the tasks, and how much time did different parts take
- Write backend (api controllers, get data from api) and map all the objects was quite easy and didn't take too much time, I think it takes maximum 1-1.5 hours, because it was unfamiliar for me which data which api returns.
- With frontend all was different. It was my first time using angular, and I had to learn it fast by myself, although I had experienced in Vue.js and Aurelia 2. To get data, map it by interfaces, design web page and integrate map takes more than 24 hours. There were a lot of problems with map integration, then was created a few useless stuff by me and then deleted, because as I figured it out it was unnecessary. All of this thing takes a lot of thinking what I really need to implement and how. 
4. How you decided what to implement and what to skip
- I decided to implement Google maps in this task, because there were much bigger info how to implement it than with for example openstreetmaps. I decided also to implement searching by city name, although it wasn't in task description because it's much easier to find by city name then by coordinates. Although searching by coords works fine!
5. What were the biggest challenges and how did you overcome them
- For me, it was to learn something new in limited period of time, like Angular itself or more specific map integration in project. I started to read and watch tutorials.
6. If there was a problem you did not have time to overcome, describe how you think it could be solved with more time
- I think I could solve problem with savings with in memory database (like HSQL or H2) to not up db server separately. But it could take some time of thinking how should it be implemented better.
- All the same with comparing. I think if application has db and user system it will be better to have user own saving in his profile and compare with current data.
7. What did you learn from implementing the project.
- I have learned Angular.js and more JavaScript itself. I've got more practice on it! One of the learned things is also skill to solve more "real world" problems, not like in some school projects.