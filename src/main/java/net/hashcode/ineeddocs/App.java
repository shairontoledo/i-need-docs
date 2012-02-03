package net.hashcode.ineeddocs;

/**
 * @author shairon toledo
 */
public class App {

  public static void main(String[] args) {

    for (Engine engine : Environment.instance().getEngines()) {
      for (LanguageEntry languageEntry : Environment.instance().getLanguages()) {
        new DocumentDownloader(engine, languageEntry).download();
      }
    }

  }
}
