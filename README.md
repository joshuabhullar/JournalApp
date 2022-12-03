    # Journal Logging Application

### What will the application do?

The purpose of this application is to be able to add **stories/logs**
to a ***journal***. Users can add, view, search, and delete logs in order to create
a journal of which they can reminisce upon. 

### Who will use it?

- People who journal regularly
- People who want to get into journaling but don't like 
writing in a physical journal
- People who want to read up on their past habits and actions
in order to better themselves

### Why is this project of interest to you?

This project is of interest to me because I journal pretty often.
I love to journal at the end of my day, and I have filled almost 3 different physical journals
from all my years of journaling. I especially like reading through my
past journal logs, as I like to see how my life has changed since
I have written said journal log. However, when I'm trying to find a specific date
or something specific I had written about in the past, it's really difficult
to find anything by just flipping through pages. That's why I want to build a
journal logging application, so I can store my journal logs and easily read them
in the future.

### User Stories

***PHASE 1:***

- As a user, I want to be able to **add** a new log and add it to a journal
- As a user, I want to be able to **delete** logs added to a journal
- As a user, I want to be able to **search** for logs added to a journal
- As a user, I want to be able to **view** a log or all logs added to a journal

***PHASE 2:***

- As a user, I want to be able to save my journal logs to file 
- As a user, I want to be able to be able to load my journal logs from file

# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by pressing
  the "Create new log" button to add a log to the list of logs
- You can generate the second required event related to adding Xs to a Y by pressing
  the "Delete this log" button to delete a log from the list of logs
- You can locate my visual component by looking at the middle of the screen :)
- You can save the state of my application by clicking Journal on the top left
  of the application and pressing Save
- You can reload the state of my application by clicking Journal on the top left
  of the application and pressing Load

# Phase 4: Task 2

Fri Dec 02 15:11:12 PST 2022
Journal log journalentry1 was added to the journal
Fri Dec 02 15:11:17 PST 2022
Journal log journalentry1 was deleted from the journal
Fri Dec 02 15:11:24 PST 2022
Journal log journalentry2 was added to the journal
Fri Dec 02 15:11:31 PST 2022
Journal log journalentry3 was added to the journal
Fri Dec 02 15:11:32 PST 2022
Journal log journalentry2 was deleted from the journal
Fri Dec 02 15:11:34 PST 2022
Journal log journalentry3 was deleted from the journal

# Phase 4: Task 3

For the actual design and refactoring of my project's design,
I don't think there is anything glaringly obvious that I would
want to refactor. My project and especially the code are quite simple,
so there wouldn't be any major changes. 

One thing I could potentially refactor is create a new class in the
model folder called ID which could act as a better way to viewLog and
searchLog up logs. Currently, the searchLog and viewLog methods within the Journal
class use the journal log's title to execute the methods, so problems might
arise when the two journal logs have the same title. If a separate class
named ID was used, each journal log could have a unique ID attached to it
when the journal log is created, so there will never be the chance of two
journal logs having the same ID. Overall, adding the ID class would allow
for a less buggy viewLog and searchLog methods.

I would also want to spend a lot more time making a visually appealing GUI,
as my current GUI named JournalApp is quite ugly (but still fully functional!).