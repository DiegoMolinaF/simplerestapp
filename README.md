# Simple Rest App

The idea of the app is to have an Employee table that can be accessed via REST queries.

This project was required to have table with the following structure:

	Id     Char(3)
	Name   Char(30)
	Salary Number(12,2)
	Dept   Char(10)

Keep in mind that it's better to have an autogenerative Id with type long.

The database data was created using the script in:
	
	src\main\resources\scripts\dbInit.sql


#### How to run

If you are not behind a restrictive firewall, run the command:

```
mvnw spring-boot:run
```

If you do, you need maven installed first. Then, run the command:

```
mvn spring-boot:run
```

Then use Postman or Curl to input valid REST queries.


##### To retrieve use a GET:

```
localhost:8080/employees/
localhost:8080/employees/JRS
```

##### To input a new Employee use a POST:

```
localhost:8080/employees/

```
Using a body with the format:

```
{
    "id": "JDM",
    "name": "John Doe",
    "salary": 1000.89,
    "dept": "Sales"
}
```

##### To update a PUT:

```
localhost:8080/employees/JRS

```

Using a body with the format:

```
{
    "id": "JDM",
    "name": "John Doe",
    "salary": 1000.89,
    "dept": "Sales"
}
```

##### To delete a user use a DELETE:

```
localhost:8080/employees/JRS
```

##### To search for an employee use a GET:

```
localhost:8080/employees/search?<searchCriteria>(&<searchCriteria)
```
Where <searchCriteria> is: employee field = search string.

Checking for employees on the Sales department. 

```
localhost:8080/employees/search?dept=sales
```

Checking for employees with a name including "ki" and "HHRR" department. 

```
localhost:8080/employees/search?name=ki&dept=HH
```


#### Database access

To manually alter the included database, access this URL:

```
http://localhost:8080/h2-console
```

Under JDBC URL, the embedded database must be set:

```
jdbc:h2:file:./src/main/resources/data/database
```
