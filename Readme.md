# Real-Time Event Ticketing System with Advanced Producer-Consumer Implementation

## **Introduction**

This Real-Time Event Ticketing System is designed to handle ticket sales for events with high demand, ensuring smooth and concurrent operations for multiple vendors and customers. The system employs the **Producer-Consumer Pattern** with advanced multi-threading techniques, offering a robust and scalable solution for managing ticket pools.

## **Features**

- **Concurrent Ticket Operations:** Multiple vendors can add tickets while customers purchase them simultaneously.
- **Thread Safety:** Ensures data integrity using synchronization mechanisms such as `ReentrantLock`, `synchronized` and thread-safe collections like `ConcurrentLinkedQueue`.
- **Dynamic Configuration:** Allows users to configure ticket pool size, ticket release rates, and customer retrieval rates.
- **User Authentication:** Separate login functionality for vendors (to add tickets) and customers (to purchase tickets).
- **Comprehensive Logging:** Logs all activities, including ticket additions, purchases, and pool status.
- **CLI and Frontend Interface:** Supports both command-line operations and a user-friendly Angular-based web interface.

## **System Architecture**

The system integrates the following components:

1. **Backend:** Built with **Java Spring Boot**, providing RESTful APIs for ticket operations.
2. **Frontend:** Developed using **Angular 6**, offering a responsive UI for vendors and customers.
3. **Database:** MySQL for persistent storage of user and ticket data, with DBeaver for database management.
4. **Concurrency Control:** Implements the Producer-Consumer Pattern to manage ticket pool operations efficiently.

## **Requirements**

### **Software**

- **Java:** Version 1.8
- **Spring Boot:** Latest compatible version
- **Node.js:** Version 14.21.1
- **Angular:** Version 6.1.0
- **Bootstrap:** Version 4
- **MySQL:** Latest stable version

### **Hardware**

- Minimum **4 GB RAM**
- Minimum **2 GHz Processor**
- **500 MB** free disk space

## **Installation and Setup**

### **Backend**

1. Clone the backend repository from GitHub.
2. Open the project in an IDE (e.g., IntelliJ IDEA or Eclipse).
3. Configure `application.properties` to connect to your MySQL database.
4. Add `schema.sql` and `data.sql` files for database initialization.
5. Build and run the Spring Boot application.

### **Frontend**

1. Clone the frontend repository from GitHub.
2. Install dependencies using `npm install`.
3. Start the Angular development server with `ng serve`.

### **Database**

1. Create a new MySQL database.
2. Use DBeaver to import the schema and initial data using the provided SQL files.

## **Usage**

### **Configuration**

- The system allows dynamic configuration through a JSON file. Update parameters like `totalTickets`, `ticketReleaseRate`, , `customerRetrivalRate`, and `maxTicketCapacity` before starting the application.

### **Running the System**

1. **Vendor Operations:** Vendors log in to add tickets to the pool.
2. **Customer Operations:** Customers log in to purchase tickets.
3. **Monitoring:** Use the CLI to monitor ticket pool status in real-time in CLI Project.

## **Demonstration Video**

A demonstration video is available to showcase the system’s capabilities:

1. **Introduction:** Overview of the system’s purpose and features.
2. **Configuration:** How to set up the system using configuration files.
3. **Live Operations:** Demonstration of concurrent ticket addition and purchase.
4. **Observations:** Key takeaways and system behavior under load.

## **Conclusion**

The Real-Time Event Ticketing System effectively demonstrates the use of advanced concurrency techniques to handle high-demand scenarios. It is a scalable, user-friendly, and robust solution for real-time ticketing needs.

## **Future Enhancements**

- Implementing additional authentication methods (e.g., OAuth).
- Enhancing the UI with more advanced features.
- Expanding to support multiple event management.
- Introducing analytics and reporting tools for administrators.
