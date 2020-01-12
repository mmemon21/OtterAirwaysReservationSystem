Android Otter Airways Reservation System App
--------------------------------------------------------------------------------------------------------------

This project demonstrates an airline application which consits of four activities, of which three are for customer usage (Creating an account, researving a seat & cancelling a reservation) and one for administrator usage (Manage system).

What each main activity does:

+ CreateAccount.java:
  Takes user input for username and password.
  Ensures that the username and password contains atleast one digit and three alphabets.

+ ReserveSeat.java:
  User is prompted to choose options for departure location and arrival location with a drop down menu.
  User is also promted to enter the number of tickets they want to purchase.
  If there is a flight added by the administrator that satisfies the search parameters then the flight can be selected by the user.
  
+ CancelReservaion.java:
  The user can cancel the flight they researved in this activity.
  
+ AddNewFlight.java, SystemLogin.java:
  The user is prompted to enter the admin login data. Matches the records and if it is correct, it logs the user into the activity where     they can manage the system - Add flights, remove flights, check login records.
  
  -----------------------------------------------------------------------------------------------------------------
  
Prefered platform: Android Studio.
