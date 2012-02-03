package net.hashcode.ineeddocs;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

/**
 *
 * @author shairon
 */
public class ScrapLinks {

  private static final Logger log = Logger.getLogger(ScrapLinks.class);

  public static ArrayList<URL> getUrls(String html, String fileExtension) throws Exception {
    List<String> strUrls = byString(html, fileExtension);

    ArrayList<URL> urls = new ArrayList<URL>(strUrls.size());
    for (String strUrl : strUrls) {
      try {
        urls.add(new URL(strUrl));
      } catch (Exception e) {
        log.error(String.format("Error %s creating url %s", e.getMessage(), strUrl));
      }
    }
    return urls;
  }

  public static ArrayList<String> byString(String html, String fileExtension) throws Exception {

    Parser parser = new Parser();
    parser.setInputHTML(html);
    NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
    ArrayList<String> links = new ArrayList<String>(list.size());
    for (int i = 0; i < list.size(); i++) {
      LinkTag extracted = (LinkTag) list.elementAt(i);
      String extractedLink = extracted.getLink();
      if (extractedLink.toLowerCase().endsWith("." + fileExtension)) {
        links.add(extractedLink);
      }
    }
    return links;

  }

  public static ArrayList<String> byFile(File htmlFile, String fileExtension) throws Exception {

    return byString(IOUtils.toString(new FileInputStream(htmlFile)), fileExtension);

  }
}
