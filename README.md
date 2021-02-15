# About this project:

This is a demo project for an e-commerce website.

Used *Spring Security* for admin page, so that admin can login and upload/update products, categories and subcategories.
Used *Thymeleaf* as a template engine.
Used *MySQL* for database.
Used *Bootstrap* for making website responsive quickly.

---

## Steps to recreate:

Once you add this project in your IDE, **BEFORE** you run this project you must run some SQL scripts located in "SQL-Files" folder.

1) Open you MySQL workbench and login to your root user;

2) Open and run *create-myoutlet-user.sql*
   This will create user in your mysql workbench (username: myoutlet  password: myoutlet);

3) Click home button, add connection (you can name it anything you want), port:3306, username: myoutlet, password: myoutlet.
   This will get you access to the user created on step 2;

4) Open and run *create-myoutlet-main-db.sql*
   This will create main database for product, categories and subcategories;

5) Open and run *create-myoutlet-user-db.sql*
   This will create database for usernames and passwords for website admins and add 3 users in it:
   user: john, password: fun123, role - EMPLOYEE,
   user: mary, password: fun123, role - EMPLOYEE and MANAGER,
   user: susan, password: fun123, role - EMPLOYEE and ADMIN.

6) Now you can run this project. Right click on this project in your IDE and select "Run As Spring Boot App".
   If you dont have spring plugin installed, you can open "src/main/java/lt/moso/myoutlet/MyoutletDemoApplication.java" and run it as java application;

7) Congrats, your app should be up and running now.

8) Go to http://localhost:8080/employees to login as **mary** or **susan** and create categories, subcategories and products. 
   Go to http://localhost:8080 to visit home page where there will be you uploaded products.
test
