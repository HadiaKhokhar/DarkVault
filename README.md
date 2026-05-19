# 🔐 DarkVault — Secure Password Manager

> A modular, encrypted desktop password management system built with Java Swing, MySQL, and AES-128 encryption.

![Java](https://img.shields.io/badge/Java-SE-red?style=flat&logo=java)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue?style=flat&logo=mysql)
![AES](https://img.shields.io/badge/Encryption-AES--128-darkred?style=flat)
![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=flat)

---

## 📌 About the Project

DarkVault is a Java-based desktop application that provides users with a secure, 
centralized vault for storing and managing sensitive credentials. All passwords are 
encrypted with AES-128 before being stored in a MySQL database. The master password 
is hashed with SHA-256 and never stored in plain text.

Built as a semester project to demonstrate Object-Oriented Programming principles 
combined with real-world cybersecurity concepts.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 Authentication | Master password login with SHA-256 hashing |
| 🗃️ Credential Vault | Add, view, search, delete encrypted credentials |
| 🛡️ AES Encryption | AES-128/ECB/PKCS5 encryption on all stored passwords |
| 🔑 Password Generator | Configurable secure password generation with SecureRandom |
| 📊 Strength Analyzer | Real-time WEAK / MEDIUM / STRONG analysis with feedback |
| 🔍 Search & Filter | SQL LIKE search across site names and usernames |
| 🎨 Custom Dark UI | Fully themed Java Swing interface with no external libraries |

---

## 🏗️ Project Architecture
src/darkvault/
├── Main.java               # Entry point, DB connection check
├── model/                  # Encapsulated data objects
│   ├── User.java           # User entity and details
│   └── Credential.java     # Credential entity and details
├── service/                # Business logic layer
│   ├── AuthService.java    # Login and registration logic
│   └── VaultService.java   # CRUD operations for credentials
├── database/               # Database connectivity
│   └── DatabaseManager.java# JDBC singleton connection manager
├── util/                   # Helper classes and utilities
│   ├── EncryptionService.java # AES encrypt/decrypt + SHA-256 hash
│   ├── PasswordGenerator.java # SecureRandom password generation
│   └── StrengthAnalyzer.java  # Password strength scoring
└── gui/                    # Graphical User Interface components
    ├── LoginFrame.java     # Authentication screen
    ├── DashboardFrame.java # Main application window
    ├── VaultPanel.java     # Credential management panel
    └── GeneratorPanel.java # Password tools panel

## 🛠️ Technologies Used

- **Java SE** — Core language, OOP architecture
- **Java Swing** — Desktop GUI framework
- **MySQL** — Relational database for credential storage
- **JDBC** — Java Database Connectivity
- **AES-128** — Password encryption (Java Cryptography API)
- **SHA-256** — Master password hashing (MessageDigest)
- **NetBeans IDE** — Development environment with GUI Builder

---

## ⚙️ Setup & Installation

### Prerequisites

- Java JDK 11 or higher
- MySQL Server 8.x
- NetBeans IDE 17+ (recommended)
- MySQL Connector/J JAR

### Step 1 — Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/DarkVault.git
```

### Step 2 — Set up the database

Open MySQL Workbench or any MySQL client and run:

```sql
CREATE DATABASE darkvault_db;
USE darkvault_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    master_password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE credentials (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    site_name VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    encrypted_password TEXT NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

### Step 3 — Configure database connection

Open `src/darkvault/database/DatabaseManager.java` and update:

```java
private static final String URL  = "jdbc:mysql://localhost:3306/darkvault_db";
private static final String USER = "root";          // your MySQL username
private static final String PASS = "yourpassword";  // your MySQL password
```

### Step 4 — Add MySQL JDBC Driver

1. Download `mysql-connector-j-8.x.x.jar` from [mysql.com](https://dev.mysql.com/downloads/connector/j/)
2. In NetBeans: Right-click project → Properties → Libraries → Add JAR/Folder
3. Select the downloaded JAR

### Step 5 — Run the project

- Open the project in NetBeans
- Right-click `Main.java` → Run File
- Register a new account and log in

---

## 🗄️ Database Schema

### users table
| Column | Type | Description |
|---|---|---|
| id | INT AUTO_INCREMENT | Primary key |
| username | VARCHAR(50) UNIQUE | Unique username |
| master_password_hash | VARCHAR(255) | SHA-256 hashed password |
| created_at | TIMESTAMP | Account creation time |

### credentials table
| Column | Type | Description |
|---|---|---|
| id | INT AUTO_INCREMENT | Primary key |
| user_id | INT FK | References users(id) |
| site_name | VARCHAR(100) | Website or app name |
| username | VARCHAR(100) | Login username/email |
| encrypted_password | TEXT | AES-128 encrypted password |
| notes | TEXT | Optional notes |
| created_at | TIMESTAMP | Entry creation time |

---


## 🔒 Security Notes

- Master passwords are **never stored in plain text** — SHA-256 hashed
- Vault passwords are **always encrypted** before database write — AES-128
- All SQL queries use **PreparedStatement** — SQL injection prevention
- Passwords are **only decrypted on explicit user request** — not displayed by default

---

## 🚀 Future Enhancements

- [ ] Cloud synchronization
- [ ] Two-factor authentication (TOTP)
- [ ] AES-256 with CBC mode and random IV
- [ ] Mobile companion app
- [ ] Browser extension for autofill
- [ ] Encrypted vault export/import

---

## 📄 License

This project was created for academic purposes.
© 2025 DarkVault Team — All Rights Reserved
