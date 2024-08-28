# Notes and Thoughts

## Task 1
I outlined a bit of my thoughts in the various comments throughout the code but figured it might be beneficial to have
everything all in one central place for reference.

This problem is definitely one that I wish I would be able to talk with a product owner on to solve, as the current
implementation is built around recursion, which is fine and elegant even for navigating tree structures such as this.
But I would have definitely liked to iron out expectations with a product owner beforehand to see if I could implement
this in a better way to suit the needs of the end user.

While a recursive solution is the easiest to implement, in my comments I noted how I would have liked to talk with end
consumers about how much this sort of thing might be called and how deep the tree might go. If the requirements were not
to have this computed on the fly - I would like to offer another solution that I think is equally valid:

Add a number of reports field directly onto the Employee.
Update this on an ad hoc basis when either a new employee gets added, or a current employee is moved around.

This way we are updating the value only when needed, and it is on the employee object directly improving read speed and
decreasing the stack intensity.

To allow for the functionality of the structure to remain it could be turned into a static class, as it no longer needs
to store data, but rather return the full json structure for other services to use.

It is an alternative approach, but I do think there is some value in considering alternatives.

## Task 2
And this task is why it took a bit longer than I would have wanted.
The read request was easy, but my first implementation wanted to update with two inputs being passed in. An ID and the
Compensation object to add to the Employee ID.

I know this is possible by adding both to headers of the request and I believe I had most of it sorted out, but the main
issue I ran into this implementation was during the testing phase.

I went on an API deep dive into the TestRestTemplate and how to properly build a URI for the .exchange() method. It ate
up a lot of the time that I spent and is for sure one of those situations that I miss being able to tap another developer
on the shoulder to ask a quick question and continue on my way. That is one of those situations I also miss as I know my
skillset leans towards algorithms, optimization, and accessibility - and I was sorely missing being able to collab or
pair program with another developer to quickly fix those knowledge gaps, providing a better and faster solution for
everyone.

Regardless, I ended up passing a whole Employee with the ID and Compensation object to solve such a problem, modelled
after the employee update implementation. I would like to talk more about the other solution through if you would have
the time to do so - just to fill in my knowledge gap a bit for the future.
 
# ------------------- BELOW IS THE ORIGINAL README CONTENTS FOR RECORD KEEPING --------------------

# Coding Challenge
## What's Provided
A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped with data. The application contains 
information about all employees at a company. On application start-up, an in-memory Mongo database is bootstrapped with 
a serialized snapshot of the database. While the application runs, the data may be accessed and mutated in the database 
without impacting the snapshot.

### How to Run
The application may be executed by running `gradlew bootRun`.

*Spring Boot 3 requires Java 17 or higher. This project targets Java 17. If you want to change the targeted Java 
version, you can modify the `sourceCompatibility` variable in the `build.gradle` file.*

### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```

The Employee has a JSON schema of:
```json
{
  "title": "Employee",
  "type": "object",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
      "type": "string"
    },
    "position": {
      "type": "string"
    },
    "department": {
      "type": "string"
    },
    "directReports": {
      "type": "array",
      "items": {
        "anyOf": [
          {
            "type": "string"
          },
          {
            "type": "object"
          }
        ]
      }
    }
  }
}
```
For all endpoints that require an `id` in the URL, this is the `employeeId` field.

## What to Implement
This coding challenge was designed to allow for flexibility in the approaches you take. While the requirements are 
minimal, we encourage you to explore various design and implementation strategies to create functional features. Keep in
mind that there are multiple valid ways to solve these tasks. What's important is your ability to justify and articulate
the reasoning behind your design choices. We value your thought process and decision-making skills. Also, If you 
identify any areas in the existing codebase that you believe can be enhanced, feel free to make those improvements.

### Task 1
Create a new type called `ReportingStructure` that has two fields: `employee` and `numberOfReports`.

The field `numberOfReports` should equal the total number of reports under a given employee. The number of reports is 
determined by the number of `directReports` for an employee, all of their distinct reports, and so on. For example,
given the following employee structure:
```
                   John Lennon
                 /             \
         Paul McCartney     Ringo Starr
                            /         \
                       Pete Best    George Harrison
```
The `numberOfReports` for employee John Lennon (`employeeId`: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4.

This new type should have a new REST endpoint created for it. This new endpoint should accept an `employeeId` and return
the fully filled out `ReportingStructure` for the specified `employeeId`. The values should be computed on the fly and 
will not be persisted.

### Task 2
Create a new type called `Compensation` to represent an employee's compensation details. A `Compensation` should have at 
minimum these two fields: `salary` and `effectiveDate`. Each `Compensation` should be associated with a specific 
`Employee`. How that association is implemented is up to you.

Create two new REST endpoints to create and read `Compensation` information from the database. These endpoints should 
persist and fetch `Compensation` data for a specific `Employee` using the persistence layer.

## Delivery
Please upload your results to a publicly accessible Git repo. Free ones are provided by GitHub and Bitbucket.
