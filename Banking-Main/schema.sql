CREATE TABLE customers (
                           customer_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(150) NOT NULL,
                           phone VARCHAR(15) NOT NULL,
                           pan_card VARCHAR(10) UNIQUE NOT NULL,
                           aadhaar VARCHAR(12) UNIQUE NOT NULL,
                           address VARCHAR(255) NOT NULL,
                           kyc_status ENUM('PENDING', 'VERIFIED', 'REJECTED')
    DEFAULT 'VERIFIED',
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
                          account_no VARCHAR(20) PRIMARY KEY UNIQUE,
                          customer_id BIGINT NOT NULL,
                          type ENUM('SAVINGS', 'CURRENT') NOT NULL DEFAULT 'SAVINGS',
                          balance DECIMAL(19,2) DEFAULT 0.00,
                          status ENUM('ACTIVE', 'FROZEN', 'CLOSED') DEFAULT 'ACTIVE',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          FOREIGN KEY (customer_id)
                              REFERENCES customers(customer_id)
                              ON DELETE CASCADE
);

CREATE TABLE credentials (
                             username VARCHAR(20) PRIMARY KEY,
                             password_hash VARCHAR(255) NOT NULL,
                             customer_id BIGINT UNIQUE NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                             FOREIGN KEY (customer_id)
                                 REFERENCES customers(customer_id)
                                 ON DELETE CASCADE
);

CREATE TABLE transactions(
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             transaction_id VARCHAR(30) UNIQUE NOT NULL,
                             account_no VARCHAR(20) NOT NULL,
                             transaction_type ENUM('CREDIT' , 'DEBIT' , 'TRANSFER'),
                             amount DECIMAL(19 , 2) NOT NULL,
                             balance_after DECIMAL(19 , 2) NOT NULL,

                             status ENUM('SUCCESS' , 'FAILED' , 'PENDING'),
                             remarks VARCHAR(255),
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                             CONSTRAINT fk_transaction_account FOREIGN KEY (account_no) REFERENCES accounts(account_no)
                                 ON DELETE CASCADE
);