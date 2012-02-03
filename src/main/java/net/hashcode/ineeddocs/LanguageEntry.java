package net.hashcode.ineeddocs;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author shairon
 */
public class LanguageEntry {

  String langName;
  String engineLang;
  String[] documentTypes;
  String[] querySeeds;
  
  private static final Logger log = Logger.getLogger(LanguageEntry.class);

  
  public LanguageEntry(String langName, String googleLang, String documentTypes, String querySeeds) {
    this.langName = langName;
    this.engineLang = googleLang;
    this.documentTypes = documentTypes.split("\\s*,\\s*");
    this.querySeeds = querySeeds.split("\\s*,\\s*");

    for (int i = 0; i < this.querySeeds.length; i++) {
      try {
        this.querySeeds[i] = URLEncoder.encode(this.querySeeds[i], "UTF-8");
      } catch (UnsupportedEncodingException eenc) {
        log.warn("Encoding error for query " + this.querySeeds[i] + " skipping it");
      }
    }
  }
  
  @Override
  public String toString(){
  
    return String.format("[%s](%s)", langName, engineLang);
  }
}
