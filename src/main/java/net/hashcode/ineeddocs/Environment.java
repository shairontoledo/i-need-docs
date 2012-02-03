package net.hashcode.ineeddocs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Environment {

  private static final Logger log = Logger.getLogger(Environment.class);
  private static Environment current;
  private List<Engine> engines;
  private List<LanguageEntry> languages;
  private String documentOutput;
  private String userAgent;

  public List<Engine> getEngines() {
    return engines;
  }

  public static Environment instance() {
    if (current == null) {
      current = new Environment();
    }
    return current;
  }

  private static File[] fetchFiles(String dirName) {
    try {
      File files[] = new File(".",dirName).listFiles();
      if (files == null){
      log.warn("Directory "+dirName+" is empty");
      return new File[0];
      }
     return files;
      
    } catch (Exception e) {
      log.error("File or Directory "+dirName+" is required in the class path but it wasn't found.");
      System.exit(1);
      return null;
    }
  }

  private static Properties getProperty(String propFile) throws IOException {

    InputStream in = new FileInputStream(new File(".",propFile));
    Properties prop = new Properties();
    prop.load(in);
    return prop;
  }

  private Environment() {
    startUp();
  }

  public static LanguageEntry languageFromProperty(String propFile) {

    try {

      Properties prop = getProperty(propFile);
      LanguageEntry lang = new LanguageEntry(
              prop.getProperty("langName"),
              prop.getProperty("engineLang"),
              prop.getProperty("documentTypes"),
              prop.getProperty("querySeeds"));

      //log.info(String.format("Loaded language %s", lang));
      return lang;

    } catch (Exception e) {
      log.error(String.format("Loading language file %s error: %s", propFile, e.getMessage()));
      return null;
    }
  }

  public static Engine engineFromProperty(String propFile) {

    try {
      Properties prop = getProperty(propFile);
      Engine engine = new Engine(
              prop.getProperty("name"),
              prop.getProperty("template"));

      log.info(String.format("Loaded engine %s", engine));
      return engine;

    } catch (Exception e) {
      log.error(String.format("Loading engine file %s error: %s", propFile, e.getMessage()));
      return null;
    }
  }

  private void loadAllEngines() {
    engines = new ArrayList<Engine>();
    Engine engine;
    String path = "/config/engines/enabled";
    for (File file : fetchFiles(path)) {
      try {
        engine = engineFromProperty(path + "/" + file.getName());
        engines.add(engine);
        log.info("Loaded engine " + engine);

      } catch (Exception e) {
        log.error("Loading engine file " + file + ", skipping it");
      }
    }
  }

  private void loadAllLanguages() {
    languages = new ArrayList<LanguageEntry>();
    LanguageEntry lang;
    String path = "/config/langs/enabled";
    for (File file : fetchFiles(path)) {
      try {
        lang = languageFromProperty(path + "/" + file.getName());
        languages.add(lang);
        log.info("Loaded language " + lang);

      } catch (Exception e) {
        log.error("Loading language file " + file + ", skipping it");
      }
    }
  }

  private void startUp() {
    log.info("Loading Application");
    String confFile = "./config/ineeddocs.properties";
    try {
      Properties prop = getProperty(confFile);
      this.documentOutput = prop.getProperty("documentOutput");
      this.userAgent = prop.getProperty("userAgent");
      new File(this.documentOutput).mkdirs();

    } catch (Exception e) {
      log.error("Loading Application error: " + e.getMessage() + " configFile:" + confFile + ", aborting");
      System.exit(1);
    }
    loadAllEngines();
    loadAllLanguages();


  }

  public File getDocumentDestination(LanguageEntry le, URL docUrl) {
    int i = docUrl.toString().lastIndexOf('/');
    String fileName = docUrl.toString().substring(i + 1);
    File dir = new File(documentOutput, le.langName);
    dir.mkdirs();
    //String file = String.format("%s/%s/%s", documentOutput, le.langName,fileName);
    return new File(dir,fileName);
    
  }

  public List<LanguageEntry> getLanguages() {
    return languages;
  }

  public String getDocumentOutput() {
    return documentOutput;
  }

  public String getUserAgent() {
    return userAgent;
  }
  
}
