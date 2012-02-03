package net.hashcode.ineeddocs;

import org.testng.annotations.Test;

/**
 *
 * @author shairon
 */
public class AppTest {
  
  @Test(enabled=false)
  public void start() {
    for (Engine engine : Environment.instance().getEngines()) {
      for (LanguageEntry languageEntry : Environment.instance().getLanguages()) {
        new DocumentDownloader(engine, languageEntry).download();
      }
    }

  }
}
