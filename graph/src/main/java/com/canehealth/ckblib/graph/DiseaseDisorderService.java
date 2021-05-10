package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DiseaseDisorderService {

    @Autowired
    DiseaseDisorderRepository diseaseDisorderMentionRepository;

    @Autowired
    Driver driver;

    /**
     * Returns all diseases in the graph
     *
     * @see com.canehealth.ckblib.graph.DiseaseDisorderRepository
     * @author beapen
     * @since 0.14.0
     * @return All disease nodes in the graph
     */
    public Flux<DiseaseDisorderMention> getAllDiseases(){
        return diseaseDisorderMentionRepository.findAll();
    }

    /**
     * Finds a disease by CUI to which the
     * {@link #deleteDisease(DiseaseDisorderMention diseaseDisorderMention)) subscribes}
     *
     * @since 0.14.0
     * @param cui CUI
     */
    public void deleteDiseaseByCui(String cui) {
        diseaseDisorderMentionRepository.findOneByCui(cui).subscribe(this::deleteDisease);
    }

    /**
     * Deletes a disease node
     * @since 0.14.0
     * @param diseaseDisorderMention Disease NodeEntity to delete
     */
    @Transactional
    public void deleteDisease(DiseaseDisorderMention diseaseDisorderMention){
        diseaseDisorderMentionRepository.delete(diseaseDisorderMention);
    }

    public Mono<DiseaseDisorderMention> getDiseaseByCui(String cui) {
        return diseaseDisorderMentionRepository.findOneByCui(cui);
    }

    @Transactional
    public Mono<DiseaseDisorderMention> saveDisease(DiseaseDisorderMention diseaseDisorderMention){
        return diseaseDisorderMentionRepository.save(diseaseDisorderMention);
    }

    public Mono<DiseaseDisorderMention> getDiseasesByName(String name){
        return diseaseDisorderMentionRepository.findOneByNameLikeIgnoreCase(name);
    }

    public Mono<Object> addDifferential(String dcui, String ddcui, int confidence, int upvote, int downvote) {
        return diseaseDisorderMentionRepository.mergeDiseaseWithDisease(dcui, ddcui, confidence, upvote, downvote);
    }

    public String forD3(String cui) {

        D3Map d3Map = new D3Map.Builder(driver).withCui(cui).build();
        return d3Map.query();

    }
}
