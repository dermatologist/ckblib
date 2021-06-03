package com.canehealth.ckblib.umls.model;

import lombok.Data;

@Data
public class AtomLite {
	private String ui;
	private String name;
	private String termType;
	private String language;
	private boolean suppressible;
	private boolean obsolete;
	private String rootSource;
	private String concept;
	private String code;
	private String sourceConcept;
	private String sourceDescriptor;
	private String parents;
	private String children;
	private String ancestors;
	private String descendants;
}
