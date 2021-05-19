/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.canehealth.ckblib.graph;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class BaseRelationService {

    @Autowired
    Driver driver;

    @Autowired
    BaseRelationRepository baseRelationRepository;

    public String getRelationsByCui(String cui) {
        String externalQuery = "MATCH (d {cui: '" + cui + "'}) -[r]- (s) RETURN d, r, s";
        D3Map d3Map = new D3Map.Builder(driver).withQuery(externalQuery).build();
        return d3Map.fetch();
    }

    // Cannot use above method
    // Writing in read access mode not allowed. Attempted write to internal graph 0
    // (neo4j)
    public Mono<Object> addAttributes(String dcui, String scui, int confidence, int upvote, int downvote) {
        return baseRelationRepository.updateRelationshipAttributes(dcui, scui, confidence, upvote, downvote);

    }


    public String getLift(String cui) {
        String query = ""
		+ "MATCH (d {cui: '"+ cui + "'}) -[r]- (s)\n"
        + "RETURN (sum(r.upvote) - sum(r.downvote)) / (sum(r.upvote) + sum(r.downvote) + 1) as lift \n";
        D3Map d3Map = new D3Map.Builder(driver).withQuery(query).build();
        return d3Map.fetch();
    }

}
