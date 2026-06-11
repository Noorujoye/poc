# Banking Service POC - Java Application

A comprehensive Proof of Concept for a full-stack banking application demonstrating enterprise-level Java development practices, secure authentication, and transaction management.

## Overview

This project implements a complete banking system with user registration, account management, and transaction processing. It showcases professional Java architecture, design patterns, security practices, and database management.

**Status**: POC | **Language**: Java | **Database**: MySQL

---

## Features

### Core Banking Operations

- **User Registration**: Complete KYC verification with PAN, Aadhaar, and address validation
- **Secure Authentication**: SHA-256 password hashing with credential management
- **Account Management**: Support for SAVINGS and CURRENT account types with lifecycle management
- **Transaction Processing**: Credit, Debit, and Transfer transactions with audit trail
- **Transaction History**: Paginated transaction history with filtering capabilities
- **Session Management**: Secure user session handling during application lifecycle

### Security & Validation

- SHA-256 cryptographic password hashing
- Comprehensive input validation using regex patterns
- KYC status tracking (PENDING, VERIFIED, REJECTED)
- Account status management (ACTIVE, FROZEN, CLOSED)
- Transaction status tracking (SUCCESS, FAILED, PENDING)
- Secure connection pooling with automatic resource management

---

## Architecture

### Layered Architecture Pattern

```
┌─────────────────────────────────────────┐
│  UI/Handler Layer                       │  ← User Interaction
│ (RegisterHandler, LoginHandler, LoginCard)
├─────────────────────────────────────────┤
│  Service Layer                          │  ← Business Logic
│ (CustomerService, AccountService,      │
│  TransactionService, AuthService)      │
├─────────────────────────────────────────┤
│  Repository Layer                       │  ← Data Access
│ (AccountRepositoryDB, CustomerRepositoryDB,
│  CredentialRepositoryDB, TransactionRepositoryDB)
├─────────────────────────────────────────┤
│  Database Layer                         │  ← Persistence
│ (MySQL with Connection Pooling)        │
└─────────────────────────────────────────┘
```

### Dependency Injection via AppFactory

The **AppFactory** singleton pattern provides centralized object creation and dependency injection:

```
BankMain
    ↓
AppFactory (single instance)
    ├─ Repository Layer Objects
    ├─ Service Layer Objects
    └─ Handler Layer Objects
```

All objects are created once and reused throughout the application lifecycle, ensuring efficient resource management.

---

## Project Structure

```
src/
├── BankMain.java                    # Application entry point
├── auth/                            # Authentication & Session Management
│   ├── Credentials.java
│   └── SessionContext.java
├── database/
│   └── DBConnection.java            # JDBC Connection Management
├── dto/
│   └── RegistrationRequest.java     # Data Transfer Objects
├── exception/                       # Custom Exception Hierarchy
│   ├── BankingException
│   ├── DuplicateUserException
│   ├── AccountNotFoundException
│   ├── InsufficientBalanceException
│   └── [7 other specialized exceptions]
├── handler/                         # UI & User Interaction Layer
│   ├── LoginCard.java
│   ├── LoginHandler.java
│   └── RegisterHandler.java
├── Input/
│   └── ScannerUtil.java            # Input utilities
├── model/                           # Domain Models
│   ├── Account.java
│   ├── Customer.java
│   ├── Transaction.java
│   └── enums/                       # Enum types
│       ├── AccountStatus.java
│       ├── AccountType.java
│       ├── KycStatus.java
│       ├── TransactionStatus.java
│       └── TransactionType.java
├── repository/                      # Data Access Layer
│   ├── AccountRepository.java       # Interface
│   ├── CredentialsRepository.java
│   ├── CustomerRepository.java
│   ├── TransactionRepository.java
│   └── impl/                        # Database implementations
│       ├── AccountRepositoryDB.java
│       ├── CredentialRepositoryDB.java
│       ├── CustomerRepositoryDB.java
│       └── TransactionRepositoryDB.java
├── service/                         # Business Logic Layer
│   ├── AccountService.java
│   ├── AuthService.java
│   ├── CustomerService.java
│   └── TransactionService.java
├── ui/                              # Presentation Layer
│   ├── Home.java
│   └── TransactionHistory.java
└── util/                            # Utility Classes
    ├── AccountNumberGenerator.java
    ├── AmountValidator.java
    ├── AppFactory.java              # Dependency Injection Container
    ├── PasswordUtil.java            # Cryptographic utilities
    └── Validator.java               # Input validation
```

---

## Database Schema

### Tables Structure

**customers**: Stores customer information with KYC status

- PK: customer_id (BIGINT AUTO_INCREMENT)
- Unique: pan_card, aadhaar
- Fields: name, phone, address, kyc_status

**accounts**: Manages customer accounts

- PK: account_no (VARCHAR with leading zeros support)
- FK: customer_id → customers
- Fields: type (SAVINGS/CURRENT), balance (DECIMAL), status
- Cascade: ON DELETE CASCADE

**credentials**: Authentication data (1:1 with customers)

- PK: username (VARCHAR)
- FK: customer_id → customers (UNIQUE)
- Fields: password_hash (SHA-256)

**transactions**: Transaction audit trail

- PK: id (BIGINT AUTO_INCREMENT)
- Unique: transaction_id (TXN-UUID format)
- FK: account_no → accounts
- Fields: type, amount, balance_after, status, remarks

---

## Java Skills & Concepts Demonstrated

### Core OOP Principles

✓ **Encapsulation**: Private fields with public getters/setters  
✓ **Inheritance**: Exception hierarchy (BankingException as base)  
✓ **Polymorphism**: Repository pattern with interface implementations  
✓ **Abstraction**: Abstract repository layer, service contracts

### Design Patterns

✓ **Singleton**: AppFactory for centralized object management  
✓ **Repository Pattern**: Data access abstraction  
✓ **Service Layer**: Business logic separation  
✓ **DTO Pattern**: RegistrationRequest for method parameter encapsulation  
✓ **Factory Pattern**: Object creation and dependency injection  
✓ **Strategy Pattern**: Validator predicates with functional programming

### Advanced Java Features

✓ **JDBC & Connection Management**: PreparedStatements, ResultSet mapping  
✓ **Transaction Management**: Manual commit/rollback for ACID compliance  
✓ **Exception Handling**: Custom exceptions with try-catch-finally  
✓ **Concurrency**: AtomicInteger for thread-safe operation counters  
✓ **Regex Validation**: Pattern matching for phone, PAN, Aadhaar, email  
✓ **Cryptography**: SHA-256 hashing with MessageDigest API  
✓ **Functional Programming**: Lambda expressions and Predicates  
✓ **Collections Framework**: Lists, pagination logic  
✓ **Time API**: LocalDateTime for transaction timestamps  
✓ **String Handling**: StringBuilder, String formatting, Unicode support  
✓ **Enums**: Type-safe constants for statuses and transaction types  
✓ **Manual Dependency Injection**: Constructor-based injection without frameworks

### Security & Validation

✓ Secure password storage with SHA-256  
✓ Input validation using regex patterns  
✓ SQL Injection prevention with PreparedStatements  
✓ Session context for user state management  
✓ Sensitive data handling (no plain-text passwords)

### Database Best Practices

✓ Foreign key constraints with cascading deletes  
✓ Unique constraints for critical fields  
✓ DECIMAL type for monetary values (precision)  
✓ ENUM types for limited status values  
✓ AUTO_INCREMENT for surrogate keys  
✓ Timestamp audit trails (created_at)  
✓ Pagination with offset calculation  
✓ Connection pooling concept

---

## Key Learning Insights

### Architecture Decisions

1. **Layered Architecture**: Separation of concerns enables testability and maintainability
2. **Manual Dependency Injection**: Understanding Spring's foundation by implementing DI manually
3. **Repository Pattern**: Abstraction of data access layer allows database switching
4. **Service Layer**: Centralized business logic with transaction management
5. **No Static Services**: Maintains clean architecture without global state issues

### Technical Implementations

- **Account Numbers**: Stored as VARCHAR to preserve leading zeros
- **BigDecimal for Money**: Prevents floating-point precision errors
- **AtomicInteger for Sequences**: Thread-safe operation without performance overhead
- **Transaction Rollback**: Prevents incomplete data persistence
- **DTO Pattern**: Reduces method parameter complexity from 10-15 to single object
- **SHA-256 Hashing**: Industry-standard one-way cryptographic function

### Best Practices Applied

- Repository layer throws exceptions (doesn't print) to alert service layer
- Handler layer catches exceptions and displays user-friendly messages
- Static utilities for stateless operations (Validator, PasswordUtil)
- Private final dependencies to prevent modification
- Immutable DTOs (no setters) for data contracts

---

## Application Flow

### Registration Flow

```
1. User Input (RegisterHandler)
   ↓
2. Validation (Validator)
   ↓
3. Business Logic (CustomerService)
   ├─ Save Customer (CustomerRepositoryDB)
   ├─ Generate Account Number (AccountNumberGenerator)
   ├─ Create Account (AccountRepositoryDB)
   ├─ Hash Password (PasswordUtil)
   └─ Save Credentials (CredentialRepositoryDB)
   ↓
4. Transaction Commit/Rollback (DBConnection)
   ↓
5. Account Number Returned to User
```

### Login & Transaction Flow

```
1. Credentials Input (LoginHandler)
   ↓
2. Authentication (AuthService → CredentialRepositoryDB)
   ↓
3. Session Created (SessionContext)
   ↓
4. Transaction Operations (LoginCard)
   ├─ View Balance (AccountService)
   ├─ Transfer Money (TransactionService)
   └─ View History (TransactionService)
```

---

## How to Run

### Prerequisites

- Java 8 or higher
- MySQL 5.7+
- IDE: IntelliJ IDEA or Eclipse

### Setup Instructions

1. **Create Database**

   ```sql
   mysql> source schema.sql
   ```

2. **Configure Database Connection**
   - Update `DBConnection.java` with MySQL credentials:

   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/banking_db";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
   ```

3. **Compile & Run**

   ```bash
   javac src/BankMain.java
   java -cp src BankMain
   ```

4. **Follow On-Screen Prompts**
   - Register new customer
   - Login with credentials
   - Perform transactions

---

## Testing Scenarios

### User Registration

- Valid inputs with proper PAN/Aadhaar format
- Duplicate username detection
- Invalid phone number (must start with 6-9)
- Invalid PAN format (5 letters + 4 digits + 1 letter)

### Authentication

- Correct credentials login
- Wrong password attempt
- Non-existent user handling

### Transactions

- Check balance
- Same-account transfers (validation)
- Sufficient balance checks
- Transaction history pagination

---

## Future Enhancements

- [ ] REST API layer using Spring Boot
- [ ] Unit testing with JUnit & Mockito
- [ ] Withdrawal & Deposit operations
- [ ] Interest calculation for savings accounts
- [ ] Admin dashboard for account management
- [ ] Email notifications for transactions
- [ ] Multi-currency support
- [ ] Scheduled transaction support
- [ ] Loan management module

---

## Technologies & Tools

| Technology       | Purpose                     |
| ---------------- | --------------------------- |
| **Java 8+**      | Core language               |
| **JDBC**         | Database connectivity       |
| **MySQL**        | Relational database         |
| **SHA-256**      | Password hashing            |
| **Regex**        | Input validation            |
| **Maven/Gradle** | Build automation (optional) |

---

## Code Quality Metrics

- **Exception Handling**: Comprehensive custom exception hierarchy
- **Input Validation**: Regex-based validation for all user inputs
- **Resource Management**: Try-with-resources for auto-closing connections
- **Naming Conventions**: Clear, descriptive class and method names
- **Modularity**: Single responsibility principle across all layers
- **Dependency Injection**: Constructor injection for loose coupling

---

## License

This is a POC project for educational purposes.

---

**Author**: Banking Application POC Developer  
**Last Updated**: 2026  
**Version**: 1.0
