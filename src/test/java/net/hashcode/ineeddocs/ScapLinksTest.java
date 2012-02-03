package net.hashcode.ineeddocs;

import net.hashcode.ineeddocs.ScrapLinks;
import java.util.ArrayList;
import java.io.File;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ScapLinksTest {

  @Test
  public void getLinksTest() throws Exception {
    File htmlFile = TestHelper.getFile("search_result.html");
    assertThat(htmlFile, notNullValue());
    ArrayList<String> links = ScrapLinks.byFile(htmlFile, "pdf");
    
    assertThat(links.size(), is(10));
    assertThat(links, hasItem("http://www.governor.ny.gov/assets/Secure%20Communities.pdf"));
    assertThat(links, hasItem("http://newyorkinvasivespecies.info/PlantAssessments/Ligustrum.sinense.NYS.pdf"));
    assertThat(links, hasItem("http://www.oag.state.ny.us/bureaus/real_estate_finance/pdfs/tenants_rights_guide.pdf"));
    assertThat(links, hasItem("http://www.governor.ny.gov/assets/Secure%20Communities.pdf"));
    


  }


}
