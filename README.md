# CIS183_FinalProject_GamingBacklog_MarcusRollins


-------------
Description
-------------
This app is a full CRUD application using SQL for 4 tables in a database:
-User
-Games
-Systems
-UserGames

3 out of the 4 tables have CRUD abilities, minus UserGames (The Community Tab).

This program is a Backlog program. It allows a user to create an account or Login with credintials, and add games to thier profile. On thier profile, they also have the ability to:
-Add Games to the Database if they don't exist
-Add Systems to the Database if they don't exist
-Add a Game to thier profile
-Remove a system and all games on that from thier profile
-Delete thier account
-Logout

On the Profile, the user can see what systems they play on, and total amount of XP. (Marking a game as completed will give the user 100 XP.)

On the bottom of the screen are 3 tabs.
-Profile
-Game Viewer
-Community

On the GameViewer tab, the top of the screen shows 3 more tabs for the status' of games (filtered by the UserGames table):
-Main/Rotation
-Backlogged
-Completed

The user is also allowed to delete specific games from thier profile incase they don't want to delete a system with ALL games tied to it.

In the community tab is where my 5 queries exist:
-Users
-Systems
-Games Logged by specific users
-Total XP across All Users
and -Popular games (showing how many times a game has been played/logged)

This tab pulls all information from the database, even systems and games that the current logged in user doesn't have tied to thier account.

-------------
Problems
-------------
I ran into a few things with this project that took some time to figure out how to do; one being for deleting accounts, games and systems. I had to do some research to find out how to get a display alert to appear warning the user before something is deleted. I already knew from HW03 (Student Reg) how to make alerts appear at the bottom of the screen, but those don't have any confirmation messages. The community tab and the 5 queries were not easy either, I had to look back on my Student Reg assignment for assistance and look into more SQL statements. Not making status' into a table and having them filtered through UserGames was also weird at first; but I realized after trying a couple things that having them in a switch statement was the best move for them. Other than those things, I used past assignments as guidance for this final, and I had a lot of fun making this one.

