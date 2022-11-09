package database;

import java.util.List;

public class Tags {

    private String term;
    private String AAT_URL;
    private String Wikidata_URL;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getAAT_URL() {
        return AAT_URL;
    }

    public void setAAT_URL(String AAT_URL) {
        this.AAT_URL = AAT_URL;
    }

    public String getWikidata_URL() {
        return Wikidata_URL;
    }

    public void setWikidata_URL(String wikidata_URL) {
        Wikidata_URL = wikidata_URL;
    }
}
