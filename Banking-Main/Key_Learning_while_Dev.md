1.) don't make static-service architecture, leads to architecture damage.
2.) private String accountNo;  strings because accountNumber can have leading zeroes
3.) manual dependency injection
4.) Statement.RETURN_GENERATED_KEYS, I'm going to insert a customer, and I'll need the new ID back
5.) SHa-256- SHA-256 is the industry standard for Hashing. It is a one-way cryptographic function that turns any input (like a password) into a fixed 256-bit string of characters.
6.) AtomicInteger, it is thread safe , if INT is used then it takes more time , if two threads work at the same time , they might both read and write, can cause to duplicate number
7.) In repo layer always use throw otherwise print statement will not alert service layer and next line will execute in service layer
8.) use rollback so that half or incomplete data not save to db.
9.) In handler, we must use try catch to show proper error to user
