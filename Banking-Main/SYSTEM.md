### System Flow...

AtomicInteger use kiya , its threadSafe 
BankMain
↓
CustomerHandler
↓
CustomerService.createCustomer()
↓
CustomerRepository.save()
↓
AccountService.createAccount()

↓
AuthService.createCredentials()


STEP 1
Collect customer details
↓
Save customer
↓
Customer ID generated internally

STEP 2
Ask user to create username/password
↓
Validate uniqueness
↓
Save credentials

STEP 3
Generate account
↓
Save account
↓
Show account number

STEP 4
Registration completed
↓
User can login

folder structure

src/
│
├── model/
│     ├── Customer.java
│     ├── Account.java
│     ├── Transaction.java
│     └── enums/ AccountStatus , AccountType , KycStatus , TransactionStatus , TransactionType
│                       
├── auth/
│     └── Credentials.java
│
├── service/
│     ├── AuthService.java
│     ├── CustomerService.java
│     ├── AccountService.java
│     └── TransactionService.java
│
├── repository/
│     ├── CustomerRepository.java
│     ├── AccountRepository.java
│     ├── CredentialsRepository.java
│     └── TransactionRepository.java
│
├── repository/impl/
│     ├── CustomerRepositoryImpl.java
│     ├── AccountRepositoryImpl.java
│     └── ...
│
├── handler/
│     ├── RegisterHandler.java
│     ├── LoginHandler.java
│     ├── AccountHandler.java
│
├── exception/
│
├── database/
│
├── util/
│
└── BankMain.java


### Registration
1. collect customer info
2. validate info
3. save customer
4. generate account number
5. create account
6. show account details
7. ask username/password
8. create credentials

### Login

username-password
authenticate
show user menu

### User-Card
1. profile
2. balance
3. deposit
4. withdraw
5. transfer
6. transaction history
7. logout

Good Features for POC
✔ Account Summary
name
account no
balance
status
created date
✔ Mini Statement

Last 5 transactions

✔ Profile View
✔ KYC Status
✔ Transfer Receipt
✔ Password Change


focus on accountNumberGenerator



pehle user aaaya fir usko ek menu dikhna chaiye like register and login fir wo selecct krega ki ya krna hai 


package ui;

import static Input.ScannerUtil.*;
import static util.Validator.*;

public class RegisterCard {
public static void registerCard() {
System.out.println("Do you really want to register...\n" +
"1.To proceed, Press 1\n" +
"2.To cancel, press 0"
);
boolean input = proceedOrCancel();
if (input) {
System.out.print("\nRegistration....\n\nTo Open your Account, please provide the following details:\n");
String firstName = isValidName(getStringInput("your firstName: "));
String LastName = isValidName(getStringInput("your lastName: "));
String phone = isValidPhone(getStringInput("your phone: "));
String panCard = isValidPan(getStringInput("your panCard "));
String aadhaar = isValidAadhaar(getStringInput("your aadhaar: "));
String address = isValidAddress(getStringInput("your address: "));
}
}
}














**_LAYERED ARCHITECTURE_**

                        UI Layer
                        ↓
                        Handler Layer
                        ↓
                        Service Layer
                        ↓
                        Repository Layer
                        ↓
                        JDBC + MySQL
Login: 

                        login(username,password)
                        ↓
                        system identifies customer
                        ↓
                        load user's account
                        ↓
                        user session starts
                        ↓
                        all operations happen on logged-in account

LOGIN:

    User enters:
    username
    password

↓
find username from DB

↓
hash entered password

↓
compare hashes

↓
if correct:
login success
else:
invalid credentials












