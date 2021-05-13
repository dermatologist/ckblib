/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.JournalArticle;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JournalArticleService {

    @Autowired
    JournalArticleRepository journalArticleRepository;

    @Autowired
    Driver driver;

    public Mono<JournalArticle> getSymptomByPmid(String pmid) {
        return journalArticleRepository.findOneByPmid(pmid);
    }

    @Transactional
    public Mono<JournalArticle> saveArticle(JournalArticle journalArticle) {
        return journalArticleRepository.save(journalArticle);
    }

    public Flux<JournalArticle> getSymptomByName(String name) {
        return journalArticleRepository.findAllByNameLikeIgnoreCase(name);
    }

    public Mono<JournalArticle> addEvidence(String cui, String pmid) {
        return journalArticleRepository.mergeConceptWithEvidence(cui, pmid);
    }

}
