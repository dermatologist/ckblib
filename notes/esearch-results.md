https://howtodoinjava.com/spring-webflux/webclient-get-post-example/#get
https://medium.com/@cheron.antoine/reactor-java-1-how-to-create-mono-and-flux-471c505fa158
```
// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
public class Header{
    public String type;
    public String version;
}

public class Translationset{
    public String from;
    public String to;
}

public class EsearchResult{
    public String count;
    public String retmax;
    public String retstart;
    public String querykey;
    public String webenv;
    public List<String> idlist;
    public List<Translationset> translationset;
    public List<Object> translationstack;
    public String querytranslation;
}

public class EsearchResultRoot{
    public Header header;
    public EsearchResult esearchResult;
}

```