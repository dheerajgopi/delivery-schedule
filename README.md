# Delivery Scheduler

Contains REST API for creating delivery schedules based on the locations, vehicles and drivers provided.
* For the solution, I have ignored any sort of repository layer.
* I have hardcoded the working and break times.
* I have considered the time part only while creating delivery schedule.
* Deliveries should be done within a day. If not possible appropriate response is given.

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
  "locations": ["L1", "L2", "L3"],
  "vehicles": ["V1", "V2", "V3"],
  "drivers": ["D1", "D2", "D3"]
}
```

#### Sample response:
```
{
  "status": 200,
  "error": null,
  "data": {
    "deliverySchedules": [
      {
        "location": "L1",
        "vehicle": "V1",
        "driver": "D1",
        "deliveryStartTime": "08:00:00",
        "deliveryEndTime": "10:00:00"
      },
      {
        "location": "L2",
        "vehicle": "V2",
        "driver": "D2",
        "deliveryStartTime": "08:00:00",
        "deliveryEndTime": "10:00:00"
      },
      {
        "location": "L3",
        "vehicle": "V3",
        "driver": "D3",
        "deliveryStartTime": "08:00:00",
        "deliveryEndTime": "10:00:00"
      }
    ]
  }
}
```


