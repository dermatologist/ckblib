package com.canehealth.ckblib.library.service;

import java.util.ArrayList;
import java.util.List;

import com.canehealth.ckblib.library.model.BaseQuery;
import com.canehealth.ckblib.library.model.EsearchResultRoot;
import com.canehealth.ckblib.library.util.CkblibConstants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import lombok.Getter;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class CkbEfetch {

    @Autowired
    private BaseQuery baseQuery;

    @Autowired
    private CkbEsearch ckbEsearch;

    private WebClient webClient;

    List<EsearchResultRoot> esearch_results = new ArrayList<EsearchResultRoot>();


    @Getter
    List<String> results = new ArrayList<String>();

    public CkbEfetch() {
        this.webClient = WebClient.create(CkblibConstants.EFETCH_URL);

    }

    public void setBaseQuery(BaseQuery baseQuery) {
        this.baseQuery = baseQuery;
        ckbEsearch.setBaseQuery(baseQuery);
        ckbEsearch.get().subscribe(esearch_results::add);
    }

    public Mono<String> get() {
        String query = baseQuery.getFetchQuery(esearch_results.get(0).esearchresult.ids());
        Mono<String> pubmedArticleSet = webClient.get().uri(query).retrieve()
                /*
                 * .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                 * clientResponse -> Mono.empty())
                 */
                .bodyToMono(String.class);
        pubmedArticleSet.subscribe(results::add);
        return pubmedArticleSet;
    }

    private static Document convertStringToXMLDocument(String xmlString) {
        // Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            // Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            // Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAbstractsAsString(int articleNum) {
        String abstractString = "";
        if (!results.isEmpty()) {
            XPath xPath = XPathFactory.newInstance().newXPath();
            Document doc = convertStringToXMLDocument(results.get(0));
            try {
                NodeList nodeList = (NodeList) xPath.compile("//Abstract").evaluate(doc, XPathConstants.NODESET);
                for (int index = 0; index < nodeList.getLength(); index++) {
                    Node node = nodeList.item(index);
                    abstractString += node.getTextContent();
                }

            } catch (XPathExpressionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return abstractString;
    }
}
