# dropwizard-microservice-archetype
Most plumbing in place for building a stand-alone microservice. Included are Docker, Health, Metrics and Swagger.

# Usage
```
mvn archetype:generate -DarchetypeCatalog=http://mvnrepo.cantara.no/content/repositories/releases
```

* Select dropwizard-microservice-archetype
* Provide applcationName in _CamelCase_


# Development

``` 
mvn install archetype:update-local-catalog
```
cd to new directory
```
mvn -U archetyarchetypeCatalog=local

```