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

