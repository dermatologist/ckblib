## Compile and run

```
mvn clean install -pl library
mvn clean spring-boot:run -pl application

```

https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=33344334,30741177,29785181,28453238,25877226,20061746,19584464,19009985,18797088,18789058


#IMPORTANT
## Websocket connection failure
forward both 7474 and 7687
use bolt://localhost:7687


https://github.com/neo4j/neo4j-java-driver-spring-boot-starter/tree/master/examples/testing-with-neo4j-harness/src/test/java/org/neo4j/doc/driver/springframework/boot/simple

MERGE (nid:Incremental{type:ChildSession})
ON CREATE SET nid.count = 1
ON MATCH SET nid.count = nid.count + 1
RETURN nid.count