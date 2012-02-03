package net.hashcode.ineeddocs;

import net.hashcode.ineeddocs.Engine;
import net.hashcode.ineeddocs.UrlMaker;
import net.hashcode.ineeddocs.Environment;
import net.hashcode.ineeddocs.LanguageEntry;
import java.net.MalformedURLException;
import java.net.URL;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 *
 * @author shairon
 */
public class UrlMakerTest {

  @Test
  public void makeUrls() throws MalformedURLException {
    
    Engine engine = Environment.instance().getEngines().get(0);
    assertThat(engine.name, equalTo("Google"));
    
    LanguageEntry le = Environment.instance().getLanguages().get(0);
    assertThat(le.langName, equalTo("English"));
    assertThat(le.engineLang, equalTo("en"));
    
    
    //for pdfs
    URL url = UrlMaker.make(engine, le.engineLang, "pdf", le.querySeeds[0]);
    assertThat(url.toString(), equalTo("https://www.google.com/search?hl=en&q=new+york+filetype:pdf"));
    
    url = UrlMaker.make(engine, le.engineLang, "pdf", le.querySeeds[1]);
    assertThat(url.toString(), equalTo("https://www.google.com/search?hl=en&q=boston+filetype:pdf"));

    
    url = UrlMaker.make(engine, le.engineLang, "pdf", le.querySeeds[2]);
    assertThat(url.toString(), equalTo("https://www.google.com/search?hl=en&q=miami+filetype:pdf"));
    
    //for docx
    url = UrlMaker.make(engine, le.engineLang, "docx", le.querySeeds[0]);
    assertThat(url.toString(), equalTo("https://www.google.com/search?hl=en&q=new+york+filetype:docx"));
    
    
    url = UrlMaker.make(engine, le.engineLang, "docx", le.querySeeds[1]);
    assertThat(url.toString(), equalTo("https://www.google.com/search?hl=en&q=boston+filetype:docx"));

    
    url = UrlMaker.make(engine, le.engineLang, "docx", le.querySeeds[2]);
    assertThat(url.toString(), equalTo("https://www.google.com/search?hl=en&q=miami+filetype:docx"));
    
  }
}
