package com.canehealth.ckblib.application;

import com.canehealth.ckblib.library.service.MyService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.canehealth.ckblib.graph.JournalArticleService;
import com.canehealth.ckblib.graph.model.JournalArticle;
import com.canehealth.ckblib.library.model.BaseQuery;
import com.canehealth.ckblib.library.service.CkbEfetch;
import com.canehealth.ckblib.qtakes.service.QtakesService;
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
	private CkbEfetch ckbEfetch;

	@Autowired
	private QtakesService qtakesService;

	@Autowired
	JournalArticleService journalArticleService;

	@Autowired
	private JournalArticle journalArticle;

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
		try {
			if (args[0].toLowerCase().equals("help")) {
				System.out.println(myService.message());
			} else {
				for (int i = 0; i < args.length; ++i) {
					LOG.debug("args[{}]: {}", i, args[i]);
					_term = args[0];
				}
			}
			baseQuery.setTerm(_term);
			baseQuery.setRetmax(3);
			ckbEfetch.setBaseQuery(baseQuery);
			TimeUnit.SECONDS.sleep(3);
			ckbEfetch.get();
			TimeUnit.SECONDS.sleep(5);

			List<String> abstracts = ckbEfetch.getPath("//Abstract");
			List<String> pmids = ckbEfetch.getPath("//PMID");
			List<String> titles = ckbEfetch.getPath("//ArticleTitle");

			qtakesService.setQtakesUrl("http://127.0.0.1:8093");

			for (int i = 0; i < abstracts.size(); i++) {
				System.out.println(pmids.get(i) + " : " + titles.get(i));
				journalArticle.setPmid(pmids.get(i));
				journalArticle.setName(titles.get(i));
				qtakesService.post(abstracts.get(i));
				try {
					// Should fail if already created
					journalArticleService.saveArticle(journalArticle).block();

				} catch (Exception e) {

				}
				TimeUnit.SECONDS.sleep(3);
				QtakesRoot r = qtakesService.getQtakesResults();
				System.out.println(r);
			}

		} catch (Exception e) {
			System.out.println(myService.message());
		}
		// String myPublications = ckbEfetch.getPath("//Abstract").get(0);
		// String myPMID = ckbEfetch.getPath("//PMID").get(0);
		// System.out.print(myPublications);
		// System.out.print(myPMID);

		// qtakesService.setQtakesUrl("http://127.0.0.1:8093");
		// qtakesService.post(myPublications);
		// TimeUnit.SECONDS.sleep(3);
		// QtakesRoot r = qtakesService.getQtakesResults();
		// System.out.print(r.toString());
		// String s = ckbEfetch.getChain(baseQuery).block();
		// List<String> abstracts = ckbEfetch.getPathFromString("//Abstract", s);
		// for (String abs : abstracts){
		// 	System.out.println(abs);
		// }

	}

}
