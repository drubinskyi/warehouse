# Technologies and design

* Warehouse is RESTful web service
* Hexagonal Architecture is used to decouple business logic from implementation details
* Service uses PostgreSQL as main data storage
* It's the intention to make each "adapter" package independent from each other therefore Almost all classes there are
  internal.
* Required "price" field in "product" is missing in the file, field "id" in "product" is missing too. This can be
  explained with 2 hypotheses - service sending files is not responsible for the price or lack of design integrity.
  Assumption was made that service sending this file is not responsible for "id's" so they are generated must be changed
  and the price must be included in the file.
* UUID is used as an identifier for "products" and "articles"
* All corresponding changes are applied to the files, stored under /resources directory
* Files can be loaded multiple times, each file is treated as an update 

# Local run

1. Start PostgreSQL as docker-compose service

Run the following command from the root of the project:

```
docker-compose up
```

2. Stat warehouse service, all Flyway migration scripts will be loaded on startup

```
./gradlew bootRun
```

# TODO

There are many points for improvements in this service to make it production ready:

1. Add unit and integration tests
2. Improve configuration of application, get rid of hardcoded value
3. Consider using Google PUB/SUB interface as an API instead of RESTful
4. Add Prometheus metrics
5. Add load tests and research how does service perform in highly loaded environment
