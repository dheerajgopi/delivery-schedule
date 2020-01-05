# Delivery Scheduler

Contains REST API for creating delivery schedules based on the locations, vehicles and drivers provided.
* For the solution, I have ignored any sort of repository layer.
* I have hardcoded the break times.
* I have considered the time part only while creating delivery schedule.
* Deliveries should be done within a day.
* Phone number and vehicle type validations are omitted for now.

### Tool set
* Java 8
* Maven 3.3.9
* Spring Boot 2.2.0

### Run tests
* unit tests: `mvn test`

### Run the application for local development
* `mvn spring-boot:run -Dspring-boot.run.profiles=local`

### Build and run
* `mvn clean package`
* copy `config` folder to folder in which jar file is located using `cp -r config target/`
* `java -jar -Dspring.profiles.active=local ${jar-file-path}`

### API spec

#### Request path:
`localhost:8080/v1/deliverySchedules`

#### Request body:
```
{
  "locations": [{
    "name": "L1",
    "address": "address",
    "openingTime": "07:00:00",
    "closingTime": "16:00:00"
  },
  {
    "name": "L2",
    "address": "address",
    "openingTime": "07:00:00",
    "closingTime": "08:00:00"
  }
],
  "vehicles": [{
    "registrationNumber": "V1",
    "type": "Truck"
  }],
  "drivers": [{
    "name": "D1",
    "phone": "123"
  }]
}
```

#### Sample response:

If any delivery fails, the response status will be 207 (Multistatus), else 200.

```
{
  "status": 207,
  "error": null,
  "data": {
    "deliverySchedule": [
      {
        "location": {
          "name": "L1",
          "address": "address",
          "openingTime": "07:00:00",
          "closingTime": "16:00:00"
        },
        "vehicle": {
          "registrationNumber": "V1",
          "type": "Truck"
        },
        "driver": {
          "name": "D1",
          "phone": "123"
        },
        "deliveryStartTime": "07:00:00",
        "deliveryEndTime": "09:00:00"
      }
    ],
    "noDeliveryLocations": [{
      "name": "L2",
      "address": "address",
      "openingTime": "07:00:00",
      "closingTime": "08:00:00"
    }]
  }
}
```


