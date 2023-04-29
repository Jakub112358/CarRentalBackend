## Car Rental - Backend

Frontend part of the application: https://github.com/Jakub112358/CarRentalFrontend

### Developer:
- Jakub Szymański, 

### General app description:
The main purpose of the application is to solve the problem of managing car rental company that has branch offices in various cities. 
The solution will be to create a personalized web application.

### Technical description: 

**Backend:**
Spring Boot REST application. The application meets all the requirements of level 2 of the Richardson Maturity Model. 
I use tools such as e.g. Lombok. I focus on creating clean code and using appropriate design patters.

**Frontend:**
Angular application with PrimeNG library.

### Tech Stack:
**Backend:**
- Java 17
- Spring Boot 3
- JUnit 5

**DataBase:**
- H2 for development

**Frontend:**
- Angular 15

### App features: 

**As Admin:**

- configuring company details
- crud operations on branch offices
- managing employees
- managing cars
- managing company finances (system is auto-updated with each reservation, cancellation or extra fee)
- browsing statistics about cars, employees, incomes, cancellations, popular routes


 **As Client:**
 
- making reservations
  - selecting available cars,
  - selecting pick-up and return dates 
  - selecting pick-up and return branch offices,
  - price calculation
- cancelling reservations

**As Employee**

- confirming pick-ups and returns
- charging additional fees

### Version control: 
- system: GIT
- main branches:
  - main – release app version
  - dev – stable and tested version of application
