package com.canehealth.ckblib.application;

import com.canehealth.ckblib.library.service.MyService;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.canehealth.ckblib.graph.*;
import com.canehealth.ckblib.graph.model.*;
import com.canehealth.ckblib.library.model.BaseQuery;
import com.canehealth.ckblib.library.model.EsearchResultRoot;
import com.canehealth.ckblib.library.service.CkbEfetch;
import com.canehealth.ckblib.library.service.CkbEsearch;
import com.canehealth.ckblib.qtakes.service.QtakesService;
import com.canehealth.ckblib.qtakes.model.ConceptMention;
import com.canehealth.ckblib.qtakes.model.QtakesRoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = "com.canehealth.ckblib")
@EnableReactiveNeo4jRepositories(basePackages = "com.canehealth.ckblib.graph")
public class DemoApplication implements CommandLineRunner {

	private final MyService myService;
	private static Logger LOG = LoggerFactory.getLogger(DemoApplication.class);
	@Autowired
	private BaseQuery baseQuery;

	@Autowired
	private CkbEsearch ckbEsearch;

	@Autowired
	private CkbEfetch ckbEfetch;

	@Autowired
	private QtakesService qtakesService;

	@Autowired
	JournalArticleService journalArticleService;

	@Autowired
	private JournalArticle journalArticle;

	@Autowired
	DiseaseDisorderService diseaseDisorderService;

	@Autowired
	private DiseaseDisorderMention diseaseDisorderMention;

	@Autowired
	SignSymptomService signSymptomService;

	@Autowired
	private SignSymptomMention signSymptomMention;

	@Autowired
	AnatomicalSiteService anatomicalSiteService;

	@Autowired
	private AnatomicalSiteMention anatomicalSiteMention;

	@Autowired
	MedicationService medicationService;

	@Autowired
	private MedicationMention medicationMention;

	@Autowired
	ProcedureService procedureService;

	@Autowired
	private ProcedureMention procedureMention;

	public DemoApplication (MyService myService) {
		this.myService = myService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws InterruptedException {
		LOG.info("EXECUTING : " + myService.message());

		String _term = "";
		String mainCui = "";

		try {
			if (args[0].toLowerCase().equals("help")) {
				LOG.info(myService.message());
			} else {
				for (int i = 0; i < args.length; ++i) {
					LOG.debug("args[{}]: {}", i, args[i]);
					_term = args[0];
				}
			}
			baseQuery.setTerm(_term);
			baseQuery.setRetmax(3);

			// Check and adjust for total count of articles
			ckbEsearch.setBaseQuery(baseQuery);
			ckbEsearch.get().block();
			EsearchResultRoot esearchResultRoot = ckbEsearch.getResults().get(0);
			LOG.info("Count: " + esearchResultRoot.esearchresult.count);
			int ct = Integer.parseInt(esearchResultRoot.esearchresult.count);
			Random random = new Random();
			if(ct>3){
				baseQuery.setRetstart(random.nextInt(ct));
			}


			ckbEfetch.setBaseQuery(baseQuery);
			TimeUnit.SECONDS.sleep(3);
			ckbEfetch.get().block();

			// Fails if disease not known, Try again after adding concepts
			try {
				mainCui = diseaseDisorderService.getDiseasesByName(_term).block().getCui();
			} catch (Exception e) {
			}

			LOG.info("MainCUI: " + mainCui + "\n");

			List<String> abstracts = ckbEfetch.getPath("//Abstract");
			List<String> pmids = ckbEfetch.getPath("//PMID");
			List<String> titles = ckbEfetch.getPath("//ArticleTitle");

			// Visit https://github.com/dermatologist/quick-ctakes for details on using QTakes
			qtakesService.setQtakesUrl("http://127.0.0.1:8093");

			for (int i = 0; i < abstracts.size(); i++) {
				LOG.info(pmids.get(i) + " : " + titles.get(i));
				String pmid = pmids.get(i);

				// Chech if pmid processed
				try{
					JournalArticle processed = journalArticleService.getSymptomByPmid(pmid).block();
					if(processed.getPmid().equals(pmid)){
						LOG.info(pmid + " known.");
						break;
					}
				}catch (Exception e) {
					LOG.info("Processing: " + pmid);
				}

				journalArticle.setPmid(pmid);
				journalArticle.setName(titles.get(i));
				qtakesService.post(abstracts.get(i));
				try {
					// Should fail if already created
					journalArticleService.saveArticle(journalArticle).block();

				} catch (Exception e) {

				}
				TimeUnit.SECONDS.sleep(3);
				QtakesRoot r = qtakesService.getQtakesResults();

				// Diseases
				List<ConceptMention>concepts = r.getDiseaseDisorderMention();
				for(ConceptMention concept : concepts){
					String name = concept.getText();
					int polarity = 1;
					if(concept.getPolarity()<0) polarity = concept.getPolarity();
					String cui = concept.getConceptAttributes().get(0).cui;
					diseaseDisorderMention.setCui(cui);
					diseaseDisorderMention.setName(name);
					try {
						diseaseDisorderService.saveDisease(diseaseDisorderMention).block();
					} catch (Exception e) {
						LOG.info(name + " known.");
					}
					journalArticleService.addEvidence(cui, pmid).block();

					if (!"".equals(mainCui) && !mainCui.equals(cui)) {
						LOG.info("Adding DDs");
						diseaseDisorderService.addDifferential(mainCui, cui, polarity, 0, 0);
					}
				}
				// Disease should exist now
				if("".equals(mainCui)){
					try {
						mainCui = diseaseDisorderService.getDiseasesByName(_term).block().getCui();
					} catch (Exception e) {
						LOG.error(mainCui + " unknown.");
					}
				}

				// Signs and symptoms
				concepts = r.getSignSymptomMention();
				for (ConceptMention concept : concepts) {
					String name = concept.getText();
					int polarity = 1;
					if (concept.getPolarity() < 0)
						polarity = concept.getPolarity();
					String cui = concept.getConceptAttributes().get(0).cui;
					signSymptomMention.setCui(cui);
					signSymptomMention.setName(name);
					try {
						signSymptomService.saveSymptom(signSymptomMention).block();
					} catch (Exception e) {
						LOG.info(name + " known.");
					}
					LOG.info("Disease: " + mainCui + " Contept: " + cui + "\n");
					signSymptomService.addRelation(mainCui, cui, polarity, 0, 0).block();
					journalArticleService.addEvidence(cui, pmid).block();
				}

				// Anatomy
				concepts = r.getAnatomicalSiteMention();
				for (ConceptMention concept : concepts) {
					String name = concept.getText();
					int polarity = 1;
					if (concept.getPolarity() < 0)
						polarity = concept.getPolarity();
					String cui = concept.getConceptAttributes().get(0).cui;
					anatomicalSiteMention.setCui(cui);
					anatomicalSiteMention.setName(name);
					try {
						anatomicalSiteService.saveSymptom(anatomicalSiteMention).block();
					} catch (Exception e) {
						LOG.info(name + " known.");
					}
					LOG.info("Disease: " + mainCui + " Contept: " + cui + "\n");
					anatomicalSiteService.addRelation(mainCui, cui, polarity, 0, 0).block();
					journalArticleService.addEvidence(cui, pmid).block();
				}

				// Medication
				concepts = r.getMedicationMention();
				for (ConceptMention concept : concepts) {
					String name = concept.getText();
					int polarity = 1;
					if (concept.getPolarity() < 0)
						polarity = concept.getPolarity();
					String cui = concept.getConceptAttributes().get(0).cui;
					medicationMention.setCui(cui);
					medicationMention.setName(name);
					try {
						medicationService.saveSymptom(medicationMention).block();
					} catch (Exception e) {
						LOG.info(name + " known.");
					}
					LOG.info("Disease: " + mainCui + " Contept: " + cui + "\n");
					medicationService.addRelation(mainCui, cui, polarity, 0, 0).block();
					journalArticleService.addEvidence(cui, pmid).block();
				}

				// Procedure
				concepts = r.getProcedureMention();
				for (ConceptMention concept : concepts) {
					String name = concept.getText();
					int polarity = 1;
					if (concept.getPolarity() < 0)
						polarity = concept.getPolarity();
					String cui = concept.getConceptAttributes().get(0).cui;
					procedureMention.setCui(cui);
					procedureMention.setName(name);
					try {
						procedureService.saveSymptom(procedureMention).block();
					} catch (Exception e) {
						LOG.info(name + " known.");
					}
					LOG.info("Disease: " + mainCui + " Contept: " + cui + "\n");
					procedureService.addRelation(mainCui, cui, polarity, 0, 0).block();
					journalArticleService.addEvidence(cui, pmid).block();
				}
			}

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
		}
	}

}
