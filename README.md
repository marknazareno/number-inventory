# number-inventory

A simple phone number inventory application.

#### Services:

1. List all available phone numbers:    
    ```
    curl -X GET http://localhost:8080/numbers
    ```
2. Reserve a phone number

    Can be used to temporarily reserve the number.
    ```
    curl -X PUT http://localhost:8080/numbers/82419271 -H 'Content-Type: application/json' -d '{ "status": "RESERVED" }'
    ```
3. Use a phone number

    To permanently mark a reserved phone number as "in use".
    
    ```
    curl -X PUT http://localhost:8080/numbers/82419271 -H 'Content-Type: application/json' -d '{ "status": "IN_USE" }'
    ```
4. Use the next available number:

    Unlike the previous service, this will just get the next available number from the inventory and mark it as "in use".
    ```
    curl -X GET http://localhost:8080/numbers/available
    ```

#### How to run the app:
Using docker:
```
docker run -p 8080:8080 number-inventory
```
Or manual build (JDK 10~ and Maven required):
```
./mvn clean package
java -jar target/number-inventory-0.0.1-SNAPSHOT.jar
```

#### How test the app:
When the application starts, it will load 1000 random phone numbers to the db. 

See `com.mnazareno.numberinventory.service.PhoneNumberServiceImpl.addNumbers`


To simulate 100 concurrent calls requesting for an available phone number. Each request will return a response with the phone number allocated.
```
./test.sh http://localhost:8080/numbers/available 100

```
