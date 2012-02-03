package net.hashcode.ineeddocs;

import net.hashcode.ineeddocs.Engine;
import net.hashcode.ineeddocs.DocumentDownloader;
import net.hashcode.ineeddocs.Environment;
import net.hashcode.ineeddocs.LanguageEntry;
import org.testng.annotations.BeforeTest;

/**
 *
 * @author shairon
 */
public class AppTest {
  @BeforeTest
  public void start() {
    for (Engine engine : Environment.instance().getEngines()) {
      for (LanguageEntry languageEntry : Environment.instance().getLanguages()) {
        new DocumentDownloader(engine, languageEntry).download();
      }
    }

  }
}
