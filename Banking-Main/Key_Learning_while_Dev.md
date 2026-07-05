# Banking POC Learning Notes (My Personal Notes)

These are the notes I made while building the Banking POC project. They are not official definitions, but things I understood during development, mistakes I made, and the concepts I learned. I wrote them in a simple way so I can revise them later.

---

# 1. Why We Used Layered Architecture

Initially I thought making everything static would be easier.

But later I understood that static objects become global objects and every class starts depending on them directly.

That makes the project difficult to maintain and test.

Instead we divided the project into layers.

```
Handler
   ↓
Service
   ↓
Repository
   ↓
Database
```

Each layer has only one responsibility.

- Handler → User interaction
- Service → Business logic
- Repository → Database work
- Database → Data storage

This makes the project clean.

---

# 2. AppFactory and Dependency Injection

At first I was creating objects everywhere using `new`.

Example

```java
CustomerService service = new CustomerService();
```

Again inside another class

```java
CustomerService service = new CustomerService();
```

Again somewhere else...

This creates multiple unnecessary objects.

Then I learned AppFactory.

Only one AppFactory object is created when the application starts.

It creates every Repository.

Then every Service.

Then every Handler.

After that the same objects are reused.

So object creation happens only once.

Later I realized this is almost how Spring Boot works internally using Dependency Injection.

---

# 3. Why Account Number Should Be String

Initially I wanted

```java
Long accountNo;
```

Then I learned account numbers are identifiers, not numbers used for calculations.

If account number is

```
00123456789
```

Long converts it into

```
123456789
```

Leading zeros disappear.

That is wrong.

So account numbers should always be stored as String.

---

# 4. Why Money Uses BigDecimal

Initially I thought double is enough.

Later I learned floating point numbers are not accurate.

Example

```java
0.1 + 0.2
```

doesn't always become

```
0.3
```

It may become

```
0.30000000000000004
```

In banking this is unacceptable.

So financial applications always use

```java
BigDecimal
```

because calculations remain exact.

---

# 5. Why AtomicInteger Exists

Suppose two threads increase the same counter.

Both threads read

```
100
```

Both increase it to

```
101
```

Now one increment is lost.

This is called Race Condition.

AtomicInteger solves this by making increment operation atomic.

```java
counter.incrementAndGet();
```

No duplicate values.

---

# 6. Repository Should Throw Exceptions

Initially I was writing

```java
catch(SQLException e){
    System.out.println("Database Error");
}
```

Then I learned this is wrong.

Repository should not decide what message the user sees.

Instead Repository should throw exception.

```java
throw new DatabaseException(...);
```

Service decides what to do.

Handler shows message to user.

This keeps responsibilities separate.

---

# 7. Why Transactions Need Rollback

Money transfer has multiple steps.

- Debit sender
- Credit receiver
- Save transaction

Suppose debit succeeds.

Credit fails.

Now sender loses money.

Receiver never receives it.

Database becomes inconsistent.

That's why transaction is written like

```java
commit()
```

if everything succeeds.

Otherwise

```java
rollback()
```

Rollback returns database to previous state.

---

# 8. Handler Should Catch Exceptions

Service throws business exceptions.

Handler catches them.

Instead of showing technical errors

```
SQLException...
```

Handler displays

```
Invalid username
```

or

```
Insufficient Balance
```

Users should never see internal technical details.

---

# 9. Password Hashing

Passwords should never be stored directly.

Instead they are converted into SHA-256 hash.

Example

```
mypassword
```

becomes something like

```
a4d8b...
```

Whenever user logs in,

entered password is hashed again.

If both hashes match,

login succeeds.

Actual password is never stored.

---

# 10. DTO (Data Transfer Object)

Initially methods looked like this

```java
register(
name,
phone,
pan,
aadhaar,
address,
username,
password
)
```

Too many parameters.

Easy to forget their order.

Instead create one DTO.

```java
RegistrationRequest
```

Now method becomes

```java
register(request)
```

Much cleaner.

DTO only carries data.

No business logic.

---

# 11. RETURN_GENERATED_KEYS

When customer is inserted,

database generates Customer ID automatically.

Instead of running another query,

we can directly fetch generated ID.

```java
Statement.RETURN_GENERATED_KEYS
```

This saves one database call.

---

# 12. Pagination Formula

Suppose

Page Size = 10

Formula

```
Offset = (PageNumber - 1) × PageSize
```

Examples

Page 1

```
Offset = 0
```

Page 2

```
Offset = 10
```

Page 3

```
Offset = 20
```

Only required records are loaded.

This improves performance.

---

# 13. SQL Comma Mistake

One small comma can break query.

Wrong

```sql
(balance,)
```

Correct

```sql
(balance)
```

Need to check SQL syntax carefully.

---

# 14. When Static Should Be Used

I learned static is useful only when object state is not required.

Examples

- Utility methods
- Constants

Example

```java
Math.max()
```

or

```java
Validator.isValidPhone()
```

Service classes should never be static.

---

# 15. Why Dependencies Are Final

Repository objects inside Service are marked final.

```java
private final AccountRepository repository;
```

Reason

Dependency never changes.

Makes code safer.

Prevents accidental reassignment.

---

# 16. Regex Validation

Instead of checking input manually,

Regex makes validation easier.

Examples

Phone

Must start with

```
6
7
8
9
```

PAN

```
ABCDE1234F
```

Aadhaar

Exactly

```
12 digits
```

Validation prevents wrong data before saving.

---

# 17. Functional Validator

Instead of writing validation loop many times,

one generic method accepts Predicate.

Example

```java
Validator.getValidatedField(
"Phone",
Validator::isValidPhone
);
```

Same function can validate phone,

PAN,

Name,

Address,

etc.

Reusable code.

---

# 18. equals() Method

Initially I thought

```java
object1.equals(object2)
```

compares values.

Actually default Object.equals()

compares memory addresses.

If value comparison is needed,

override equals().

---

# 19. Uppercase Display

Sometimes data is displayed in uppercase for consistency.

But database stores original value.

Display formatting and storage are different things.

---

# 20. ANSI Colors

Console output becomes easier to read.

Red

```
Error
```

Green

```
Success
```

Yellow

```
Warning
```

Improves user experience.

---

# 21. Same Account Transfer

Need to check

```java
sender == receiver
```

Otherwise user may transfer money to same account.

This should be blocked.

---

# 22. try-with-resources

Instead of manually closing Connection,

write

```java
try(Connection conn = ...)
```

Connection closes automatically.

Avoids resource leaks.

---

# 23. Transactions Only for Updates

Read operations

```
SELECT
```

don't need commit or rollback.

Transactions are mainly for

- INSERT
- UPDATE
- DELETE

---

# 24. UUID

Auto Increment works only inside one database.

Distributed systems need globally unique IDs.

UUID solves that.

Example

```
TXN-A34BF89C12
```

Collision chances are almost zero.

---

# 25. Cascade Delete

If Customer is deleted,

related Account,

Credentials,

Transactions

can also be deleted automatically.

Database handles this using

```
ON DELETE CASCADE
```

Very useful.

---

# 26. StringBuilder

Never concatenate strings repeatedly inside loops.

Wrong

```java
result += value;
```

Every iteration creates new String object.

Better

```java
StringBuilder builder = new StringBuilder();
builder.append(value);
```

More efficient.

---

# Things I Need to Test

- Registration
- Duplicate username
- Wrong phone
- Wrong PAN
- Login
- Wrong password
- Account balance
- Transaction history
- Pagination
- Money transfer
- Insufficient balance
- Same account transfer

---

# Biggest Learning

This project helped me understand how enterprise applications are actually built.

Earlier I thought everything happens inside one class.

Now I understand why companies separate projects into layers.

I also understood that Spring Boot follows almost the same ideas.

- Dependency Injection
- Singleton Objects
- Service Layer
- Repository Layer
- Configuration

The only difference is that Spring manages everything automatically.

Because of this Banking POC, learning Spring Boot will now be much easier.

---

# My Overall Takeaway

While building this Banking POC, I learned that writing working code is only one part of software development. Writing clean, maintainable, and scalable code is equally important.

Some of the biggest lessons for me were:

- Always separate responsibilities using layers.
- Never use static objects for business services.
- Store account numbers as String and money as BigDecimal.
- Use transactions to keep data consistent.
- Throw exceptions from lower layers instead of handling everything there.
- Use DTOs to keep method signatures clean.
- Validate user input before saving it.
- Never store plain text passwords.
- Reuse objects through dependency injection.
- Small design decisions today make large projects easier to maintain tomorrow.

This Banking POC gave me a practical understanding of how real backend applications are designed, and it also built a strong foundation for learning Spring Boot and enterprise Java development.