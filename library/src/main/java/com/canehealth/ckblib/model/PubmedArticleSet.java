package com.canehealth.ckblib.model;

import java.util.List;

public class PubmedArticleSet {
    List<PubmedArticle> PubmedArticle;
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
    int Volume;
    int Issue;
    PubDate PubDate;
    String CitedMedium;
    String text;
}

class Journal {
    ISSN ISSN;
    JournalIssue JournalIssue;
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
    List<AbstractText> AbstractText;
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
    Journal Journal;
    String ArticleTitle;
    Pagination Pagination;
    ELocationID ELocationID;
    Abstract Abstract;
    AuthorList AuthorList;
    String Language;
    PublicationTypeList PublicationTypeList;
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
    List<Keyword> Keyword;
    String Owner;
    String text;
}

class MedlineCitation {
    PMID PMID;
    DateRevised DateRevised;
    Article Article;
    MedlineJournalInfo MedlineJournalInfo;
    KeywordList KeywordList;
    String CoiStatement;
    String Status;
    String Owner;
    String text;
    DateCompleted DateCompleted;
    String CitationSubset;
    MeshHeadingList MeshHeadingList;
    String IndexingMethod;
    int NumberOfReferences;
    ChemicalList ChemicalList;
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
    String IdType;
    int text;
}

class ArticleIdList {
    List<ArticleId> ArticleId;
}

class Reference {
    String Citation;
    ArticleIdList ArticleIdList;
}

class ReferenceList {
    List<Reference> Reference;
}

class PubmedData {
    History History;
    String PublicationStatus;
    ArticleIdList ArticleIdList;
    ReferenceList ReferenceList;
}

class PubmedArticle {
    MedlineCitation MedlineCitation;
    PubmedData PubmedData;
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

class AbstractText {
    String Label;
    String NlmCategory;
    String text;
}

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
