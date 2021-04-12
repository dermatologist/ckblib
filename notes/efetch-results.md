public class PMID {
	public int Version;
	public int text;
}

public class DateRevised {
	public int Year;
	public int Month;
	public int Day;
}

public class ISSN {
	public String IssnType;
	public String text;
}

public class PubDate {
	public String MedlineDate;
	public int Year;
	public String Month;
	public int Day;
}

public class JournalIssue {
	public int Volume;
	public int Issue;
	public PubDate PubDate;
	public String CitedMedium;
	public String text;
}

public class Journal {
	public ISSN ISSN;
	public JournalIssue JournalIssue;
	public String Title;
	public String ISOAbbreviation;
}

public class Pagination {
	public String MedlinePgn;
}

public class ELocationID {
	public String EIdType;
	public String ValidYN;
	public String text;
}

public class Abstract {
	public List<AbstractText> AbstractText;
	public String CopyrightInformation;
}

public class AffiliationInfo {
	public String Affiliation;
}

public class Author {
	public List<AffiliationInfo> AffiliationInfo;
	public String ValidYN;
	public String text;
	public String LastName;
	public String ForeName;
	public String Initials;
	public Identifier Identifier;
}

public class AuthorList {
	public List<Author> Author;
	public String CompleteYN;
	public String text;
}

public class PublicationType {
	public String UI;
	public String text;
}

public class PublicationTypeList {
	public List<PublicationType> PublicationType;
}

public class ArticleDate {
	public int Year;
	public int Month;
	public int Day;
	public String DateType;
	public int text;
}

public class Article {
	public Journal Journal;
	public String ArticleTitle;
	public Pagination Pagination;
	public ELocationID ELocationID;
	public Abstract Abstract;
	public AuthorList AuthorList;
	public String Language;
	public PublicationTypeList PublicationTypeList;
	public ArticleDate ArticleDate;
	public String PubModel;
	public String text;
}

public class MedlineJournalInfo {
	public String Country;
	public String MedlineTA;
	public int NlmUniqueID;
	public String ISSNLinking;
}

public class Keyword {
	public String MajorTopicYN;
	public String text;
}

public class KeywordList {
	public List<Keyword> Keyword;
	public String Owner;
	public String text;
}

public class MedlineCitation {
	public PMID PMID;
	public DateRevised DateRevised;
	public Article Article;
	public MedlineJournalInfo MedlineJournalInfo;
	public KeywordList KeywordList;
	public String CoiStatement;
	public String Status;
	public String Owner;
	public String text;
	public DateCompleted DateCompleted;
	public String CitationSubset;
	public MeshHeadingList MeshHeadingList;
	public String IndexingMethod;
	public int NumberOfReferences;
	public ChemicalList ChemicalList;
}

public class PubMedPubDate {
	public int Year;
	public int Month;
	public int Day;
	public String PubStatus;
	public int text;
	public int Hour;
	public int Minute;
}

public class History {
	public List<PubMedPubDate> PubMedPubDate;
}

public class ArticleId {
	public String IdType;
	public int text;
}

public class ArticleIdList {
	public List<ArticleId> ArticleId;
}

public class Reference {
	public String Citation;
	public ArticleIdList ArticleIdList;
}

public class ReferenceList {
	public List<Reference> Reference;
}

public class PubmedData {
	public History History;
	public String PublicationStatus;
	public ArticleIdList ArticleIdList;
	public ReferenceList ReferenceList;
}

public class PubmedArticle {
	public MedlineCitation MedlineCitation;
	public PubmedData PubmedData;
}

public class DateCompleted {
	public int Year;
	public int Month;
	public int Day;
}

public class DescriptorName {
	public String UI;
	public String MajorTopicYN;
	public String text;
}

public class MeshHeading {
	public List<QualifierName> QualifierName;
	public DescriptorName DescriptorName;
}

public class MeshHeadingList {
	public List<MeshHeading> MeshHeading;
}

public class AbstractText {
	public String Label;
	public String NlmCategory;
	public String text;
}

public class Identifier {
	public String Source;
	public String text;
}

public class QualifierName {
	public String UI;
	public String MajorTopicYN;
	public String text;
}

public class NameOfSubstance {
	public String UI;
	public String text;
}

public class Chemical {
	public int RegistryNumber;
	public NameOfSubstance NameOfSubstance;
}

public class ChemicalList {
	public List<Chemical> Chemical;
}

public class PubmedArticleSet {
	public List<PubmedArticle> PubmedArticle;
}

