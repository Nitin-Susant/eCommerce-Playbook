# eCommerce-Playbook 📚🛒

A full-featured **online bookstore platform** built with Java and Spring Boot. It supports catalog browsing, secure customer registration and login, advanced search and filter options, cart management, and admin inventory control.

---

## 🌟 Features

- 🔐 **User Authentication**: Secure registration and login for customers.
- 📚 **Book Catalog**: Browse books with detailed info like author, genre, rating, and price.
- 🛒 **Cart System**: Add to cart, review items, and place orders efficiently.
- 🔍 **Advanced Search & Filter**: Filter books by author, genre, language, price, and more using Criteria API.
- 📦 **Order History**: Users can track past purchases.
- 🛠 **Admin Dashboard**: Admin can manage books, update inventory, and monitor sales.
- 📊 **Reports**: Generate sales and user activity reports.

---

## 🧰 Tech Stack

- **Backend**: Java, Spring Boot, Spring Data JPA
- **Frontend**: Thymeleaf (HTML + CSS)
- **Database**: MySQL
- **Security**: Spring Security
- **Tools**: IntelliJ IDEA, Maven, MySQL Workbench, Postman

---

## 📁 Modules

- `User`: Registration, login, role-based access
- `Book`: CRUD operations for books
- `Cart`: Add/remove items, manage cart
- `Order`: Place orders, view order history
- `Admin`: Manage inventory, reports, dashboard

---

## 🚀 Getting Started

### Prerequisites
- Java 8+
- Maven
- MySQL

### Steps to Run

```bash
# Clone the repository
git clone https://github.com/Nitin-Susant/eCommerce-Playbook.git

# Navigate to the project directory
cd eCommerce-Playbook

# Configure your MySQL credentials in application.properties

# Run the application
mvn spring-boot:run
