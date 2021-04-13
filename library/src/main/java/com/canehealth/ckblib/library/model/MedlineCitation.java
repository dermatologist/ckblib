package com.canehealth.ckblib.library.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class MedlineCitation {
    @JacksonXmlElementWrapper(useWrapping = false)
    PMID PMID;
    @JacksonXmlElementWrapper(useWrapping = false)
    DateRevised DateRevised;
    @JacksonXmlElementWrapper(useWrapping = false)
    Article Article;
    @JacksonXmlElementWrapper(useWrapping = false)
    MedlineJournalInfo MedlineJournalInfo;
    @JacksonXmlElementWrapper(useWrapping = false)
    KeywordList KeywordList;
    String CoiStatement;
    String Status;
    String Owner;
    String text;
    DateCompleted DateCompleted;
    String CitationSubset;
    @JacksonXmlElementWrapper(useWrapping = false)
    MeshHeadingList MeshHeadingList;
    String IndexingMethod;
    int NumberOfReferences;
    ChemicalList ChemicalList;
}

class PMID {
    int Version;
    int text;
}

class DateRevised {
    int Year;
    int Month;
    int Day;
}

class ISSN {
    String IssnType;
    String text;
}

class PubDate {
    String MedlineDate;
    int Year;
    String Month;
    int Day;
}

class JournalIssue {
    @JacksonXmlElementWrapper(useWrapping = false)
    int Volume;
    @JacksonXmlElementWrapper(useWrapping = false)
    int Issue;
    @JacksonXmlElementWrapper(useWrapping = false)
    PubDate PubDate;
    String CitedMedium;
    @JacksonXmlElementWrapper(useWrapping = false)
    String text;
}

class Journal {
    ISSN ISSN;
    JournalIssue JournalIssue;
    @JacksonXmlElementWrapper(useWrapping = false)
    String Title;
    String ISOAbbreviation;
}

class Pagination {
    String MedlinePgn;
}

class ELocationID {
    String EIdType;
    String ValidYN;
    String text;
}

class Abstract {
    @JacksonXmlElementWrapper(useWrapping = false)
    List<String> AbstractText;
    String CopyrightInformation;
}

class AffiliationInfo {
    String Affiliation;
}

class Author {
    List<AffiliationInfo> AffiliationInfo;
    String ValidYN;
    String text;
    String LastName;
    String ForeName;
    String Initials;
    Identifier Identifier;
}

class AuthorList {
    List<Author> Author;
    String CompleteYN;
    String text;
}

class PublicationType {
    String UI;
    String text;
}

class PublicationTypeList {
    List<PublicationType> PublicationType;
}

class ArticleDate {
    int Year;
    int Month;
    int Day;
    String DateType;
    int text;
}

class Article {
    @JacksonXmlElementWrapper(useWrapping = false)
    Journal Journal;
    @JacksonXmlElementWrapper(useWrapping = false)
    String ArticleTitle;
    Pagination Pagination;
    ELocationID ELocationID;
    @JacksonXmlElementWrapper(useWrapping = false)
    Abstract Abstract;
    @JacksonXmlElementWrapper(useWrapping = false)
    AuthorList AuthorList;
    String Language;
    @JacksonXmlElementWrapper(useWrapping = false)
    PublicationTypeList PublicationTypeList;
    @JacksonXmlElementWrapper(useWrapping = false)
    ArticleDate ArticleDate;
    String PubModel;
    String text;
}

class MedlineJournalInfo {
    String Country;
    String MedlineTA;
    int NlmUniqueID;
    String ISSNLinking;
}

class Keyword {
    String MajorTopicYN;
    String text;
}

class KeywordList {
    @JacksonXmlElementWrapper(useWrapping = false)
    List<Keyword> Keyword;
    String Owner;
    String text;
}

class PubMedPubDate {
    int Year;
    int Month;
    int Day;
    String PubStatus;
    int text;
    int Hour;
    int Minute;
}

class History {
    List<PubMedPubDate> PubMedPubDate;
}

class ArticleId {
    @JacksonXmlElementWrapper(useWrapping = false)
    String IdType;
    @JacksonXmlElementWrapper(useWrapping = false)
    int text;
}

class ArticleIdList {
    @JacksonXmlElementWrapper(useWrapping = false)
    List<ArticleId> ArticleId;
}

class Reference {
    @JacksonXmlElementWrapper(useWrapping = false)
    String Citation;
    @JacksonXmlElementWrapper(useWrapping = false)
    ArticleIdList ArticleIdList;
}

class ReferenceList {
    List<Reference> Reference;
}

class PubmedData {
    History History;
    String PublicationStatus;
    @JacksonXmlElementWrapper(useWrapping = false)
    ArticleIdList ArticleIdList;
    ReferenceList ReferenceList;
}

class DateCompleted {
    int Year;
    int Month;
    int Day;
}

class DescriptorName {
    String UI;
    String MajorTopicYN;
    String text;
}

class MeshHeading {
    List<QualifierName> QualifierName;
    DescriptorName DescriptorName;
}

class MeshHeadingList {
    List<MeshHeading> MeshHeading;
}

// class AbstractText {
// String Label;
// String NlmCategory;
// @JacksonXmlElementWrapper(useWrapping = false)
// String text;
// }

class Identifier {
    String Source;
    String text;
}

class QualifierName {
    String UI;
    String MajorTopicYN;
    String text;
}

class NameOfSubstance {
    String UI;
    String text;
}

class Chemical {
    int RegistryNumber;
    NameOfSubstance NameOfSubstance;
}

class ChemicalList {
    List<Chemical> Chemical;
}
