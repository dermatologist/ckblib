package com.canehealth.ckblib.graph.service;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;
import com.canehealth.ckblib.graph.repository.DiseaseDisorderMentionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DiseaseDisorderService {


    @Autowired
    DiseaseDisorderMentionRepository diseaseDisorderMentionRepository;


    public Mono<DiseaseDisorderMention> getDiseaseByCui(String cui) {
        return diseaseDisorderMentionRepository.findOneByCui(cui);
    }




}
