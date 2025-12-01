# UL-Timetabling-System
Java-based University of Limerick Timetabling System using MVC architecture.


## Overview:

This is a console-based Timetable Tabling System, that can be modified by the admins and accessed by both students and lecturers.
The users are able to view their own timetable, a module timetable as well as a programme timetable.

## Features include:

**Students** can view their personal timetable, a given module timetable, and their programme timetable.

**Lecturers** are able to view their timetable for their assigned modules as well as a module/programme timetable.

**Admin** can create, delete, and edit timetable slots,modules and rooms, as well as view a module/programme schedule.


## Setup:
**1.** Clone the repository.

**2.** Check if the CSV files are formatted correctly.

**3.** Compile all of the java files.

**4.** Run the application in Main class in the controller folder.

**5.** After you run Main, the following Timetable System Menu will show up:

#### === TIMETABLE SYSTEM MENU ===

##### 1. Student Login

##### 2. Lecturer Login

##### 3. Admin Login

##### 0. Exit
###### Select an option:

**6.** After you choose one of the options, a user ID and password will be required to proceed with the timetable viewing.

**7.** In the case of an unknown user login, the programme will outpus an "Invalid ____ login." message.

**8.** Following a successful login, the lecturer and student timetable follow the layout as follows:

#### ===  TIMETABLE MENU ===

##### 1. View Timetable

##### 2. View Programme Schedule

##### 3. View Module Schedule

##### 0. Logout

######    Select an option:


**8.** After selecting one of the options, **1.** leads to an individually assigned timetable, option  **2.** requires a programme code to access the desired timetable and option **3.** requires a module code input.

**9.** The output for the Admin Menu shows more options:


###### **Successfully logged in as admin!**

#### === ADMIN MENU ===

##### 1. View Programme Schedule

##### 2. View Module Schedule

##### 3. Edit a Timetable Slot

##### 4. Delete a Timetable Slot

##### 5. Create a Timetable Slot

##### 6. Create Module

##### 7. Delete Module

##### 8. Create Room

##### 9. Delete Room

##### 0. Logout

###### Select an option:

**10.** The Admin is able to create new data, as well as edit and remove existing data in the CSV by the use of the System.

**11.** The user ( student/lecturer/admin) is allowed to exit the session at any time by logging out.

### Additional Information:
All of the login information for students, lecturers and admins are stored in the CSV files attached.
