# loan-repayment-plan-generator

Coding assignment for Lindeco loan-repayment-plan-generator

I have implemented the following features for the back end challenge.

 - Java 8 features 
 - Swagger UI 
 - CI,CD implementation
 - Docker the  application 
 - Custom exception handling 
 - Logging 
 - Junit and integration  test cases   
 - Actuator for health check Deployed in 
 - AWS using CI & CD script in  Jenkins file

# Prerequisites

•	Java 8 

•	Maven 3.5.3

•	Windows/Linux

•	Postman 5.5.4

•	Git

•	Docker (optional)

•	Jenkins (optional)

•   Cloud AWS  (optional)


# Server Details
•	Embedded Apache Tomcat

# Technologies

 - Java 8 
 - Spring Boot 2.2 
 - log back  
 - Docker  
 - CI/CD Git,Jenkins 
 - Junit, Mockito 
 - Spring Boot 2.2 Spring accutor

# Installation

Step 1: Checkout project

git clone  https://github.com/prasadlpm/loan-repayment-plan-generator.git

Step 2: Once checkout is done please  Run below command 
    
  mvn clean install  
            

Jar file will generate in target location in project with name lendico.jar

Step 3: After successfully generated the Please run below command 
           java -jar lendico.jar 
		   
Validation steps:- 

1. Open Swagger UI using below URL
http://localhost:8080/lendico/swagger-ui.html

Past all below request in swagger UI  and test all below scenario.
Request:-
{
 "loanAmount": "5000",
 "nominalRate": "5.0",
 "duration":24,
 "startDate": "2018-01-01T00:00:01Z"
}


Second approach :- 

Use below post request to test the service using postman .

http://localhost:8080/lendico/api/v1/generate-plan


Request:-

{
 "loanAmount": "5000",
 "nominalRate": "5.0",
 "duration":24,
 "startDate": "2018-01-01T00:00:01Z"
}
    
	
if you are facing any difficulties to run and validate the application please reach out me 
prasadlpm1988@gmail.com 
+4915129001838