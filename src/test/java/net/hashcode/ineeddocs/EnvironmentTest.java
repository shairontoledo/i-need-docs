package net.hashcode.ineeddocs;

import java.io.File;
import java.util.List;
import java.io.IOException;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author shairon
 */
public class EnvironmentTest {

  @Test(enabled = true)
  public void config() throws IOException {
    Environment env = Environment.instance();
    assertThat(env.getDocumentOutput(), equalTo("/tmp/needdocs/docs"));
    
    assertThat(env.getUserAgent(), notNullValue());
  }
  @Test
  public void setupOutputDirectories() throws IOException {
    Environment env = Environment.instance();
    assertThat(env.getDocumentOutput(), equalTo("/tmp/needdocs/docs"));
    
    
    //Disabled directory deletion
    File docOutput = new File(env.getDocumentOutput());
    assertThat(docOutput.exists(), is(true));
    assertThat(docOutput.isDirectory(), is(true));
    
    
    
  }

  @Test(enabled = true)
  public void loadLanguage1() throws IOException {


    LanguageEntry le = Environment.languageFromProperty("/config/langs/enabled/en.properties");
    assertThat(le.documentTypes, notNullValue());

    assertThat(le.documentTypes[0], equalTo("pdf"));
    assertThat(le.documentTypes[1], equalTo("docx"));


    assertThat(le.langName, equalTo("English"));
    assertThat(le.engineLang, equalTo("en"));
    assertThat(le.querySeeds, notNullValue());

    assertThat(le.querySeeds[0], equalTo("new+york"));
    assertThat(le.querySeeds[1], equalTo("boston"));
    assertThat(le.querySeeds[2], equalTo("miami"));

  }

  @Test(enabled = true)
  public void loadLanguage2() throws IOException {


    LanguageEntry le = Environment.languageFromProperty("/config/langs/enabled/pt.properties");
    assertThat(le.documentTypes, notNullValue());

    assertThat(le.documentTypes[0], equalTo("pdf"));
    assertThat(le.documentTypes[1], equalTo("docx"));


    assertThat(le.langName, equalTo("Portuguese"));
    assertThat(le.engineLang, equalTo("pt"));
    assertThat(le.querySeeds, notNullValue());

    assertThat(le.querySeeds[0], equalTo("goiania"));
    assertThat(le.querySeeds[1], equalTo("rio+de+janeiro"));
    assertThat(le.querySeeds[2], equalTo("sao+paulo"));

  }

  @Test(enabled = true)
  public void loadAllLanguages() throws IOException {


    LanguageEntry le = Environment.languageFromProperty("/config/langs/enabled/pt.properties");
    assertThat(le.documentTypes, notNullValue());

    assertThat(le.documentTypes[0], equalTo("pdf"));
    assertThat(le.documentTypes[1], equalTo("docx"));


    assertThat(le.engineLang, equalTo("pt"));
    assertThat(le.querySeeds, notNullValue());

    assertThat(le.querySeeds[0], equalTo("goiania"));
    assertThat(le.querySeeds[1], equalTo("rio+de+janeiro"));
    assertThat(le.querySeeds[2], equalTo("sao+paulo"));

  }

  @Test
  public void loadAll() throws IOException {
  }

  @Test(enabled = true)
  public void loadEngine() throws IOException {

    Engine e = Environment.engineFromProperty("/config/engines/enabled/google.properties");
    assertThat(e.name, equalTo("Google"));
    assertThat(e.template, notNullValue());

  }

  @Test(enabled = true)
  public void boot() throws IOException {

    List<Engine> engines = Environment.instance().getEngines();
    List<LanguageEntry> languages = Environment.instance().getLanguages();
    assertThat(languages.get(0).langName, equalTo("English"));
    assertThat(languages.get(1).langName, equalTo("Portuguese"));

  }
}
