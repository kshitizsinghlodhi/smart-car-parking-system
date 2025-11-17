🅿️ Simple Smart Parking Management System

📘 Overview
The Simple Smart Parking Management System (SSPMS) is a Java-based console application that simulates a parking lot management process.  
It allows users to view, reserve, and pay for parking slots, and start or end parking sessions — all through a simple, text-based interface.  
Admins can view registered users, check slot statuses, and see all reservations.

This project was created for the **Principles of Software Engineering** course as a simplified version of a Smart Parking System.


 Features
✅ View available parking slots  
✅ Reserve a slot  
✅ Pay for a reservation  
✅ Start and end parking sessions  
✅ Admin dashboard to view all users, slots, and reservations  
✅ Simple menu-based navigation  

🧑‍💻 Technologies Used
- Programming Language: Java  
- Environment: Console (Command-Line)  
- JDK Version: 17 or higher  
- IDE (Optional): VS Code / IntelliJ / Eclipse  

🧩 Project Flow
1. User Registration or Login 
   - New users can create an account.  
   - Old users log in using their credentials.

2. User Menu
   - View Slots  
   - Reserve a Slot  
   - Pay for Reservation  
   - Start Parking (only after payment)  
   - End Parking  
   - Logout  

3. Admin Login
   - Default username: `admin`  
   - Default password: `admin123`  
   - Admin can view users, slots, and reservations.


⚙️ How to Run the Program
1️⃣ Clone the repository:

         git clone https://github.com/<your-username>/Simple-Smart-Parking-System.git


2️⃣ Navigate into the folder:

        cd Simple-Smart-Parking-System


3️⃣ Compile the Java file:

       javac SimpleParking.java

4️⃣ Run the program:

       java SimpleParking

🗂️ File Structure
📦 Simple-Smart-Parking-System
 ┣ 📜 SimpleParking.java
 ┣ 📜 Simple_Smart_Parking_SRS.docx
 ┣ 📜 README.md
 ┗ 📁 /screenshots (optional for project images)


🧠 Example Workflow

=== SIMPLE PARKING APP ===
Are you a NEW user? (y/n): y
Choose username: test
Choose password: 1234
User created. Now you can login.

Login as (user/admin): user
Username: test
Password: 1234
✅ User Login Successful!

--- USER MENU ---
1. View available slots
2. Reserve slot
3. Pay for reservation
4. Start parking
5. End parking
6. Logout


👥 Team Members
| Name :- Kshitiz Lodhi |

📄 License
This project is developed for academic and learning purposes under an open educational license.

🌟 Future Improvements
- Add file-based storage for saving data permanently.  
- Add GUI or web interface using JavaFX or Spring Boot.  
- Implement real-time sensors and IoT integration.  
- Include online payments and QR-based access.
