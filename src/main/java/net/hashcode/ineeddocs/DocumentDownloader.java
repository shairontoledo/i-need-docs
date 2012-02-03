package net.hashcode.ineeddocs;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 *
 * @author shairon
 */
public class DocumentDownloader {

  private final LanguageEntry langEntry;
  private final Engine engine;
  private static final Logger log = Logger.getLogger(DocumentDownloader.class);

  public DocumentDownloader(Engine engine, LanguageEntry langEntry) {
    this.langEntry = langEntry;
    this.engine = engine;
  }

  public void download() {

    HttpClient httpclient = new DefaultHttpClient();
    ExecutorService executor = Executors.newFixedThreadPool(6);
    List<Callable<Object>> downloadList = new ArrayList<Callable<Object>>(langEntry.documentTypes.length * langEntry.querySeeds.length*langEntry.documentTypes.length);
    for (String docType : langEntry.documentTypes) {
      for (String seed : langEntry.querySeeds) {

        try {
          URL url = UrlMaker.make(engine, langEntry.engineLang, docType, seed);
          String scope = String.format("[%s](%s .%s)", engine.name, langEntry.engineLang, docType);

          log.info(String.format("%s Searching for '%s'", scope, seed));

          HttpGet httpget = new HttpGet(url.toURI());
          httpget.addHeader("User-Agent", Environment.instance().getUserAgent());
          String responseBody = httpclient.execute(httpget, new BasicResponseHandler());
          httpclient.getConnectionManager().closeExpiredConnections();
          
          for (final URL docUrl : ScrapLinks.getUrls(responseBody, docType)) {

            final File destination = Environment.instance().getDocumentDestination(langEntry, docUrl);
            final DownloadEntry de = new DownloadEntry(docUrl, destination);
            downloadList.add(de);

          }
        } catch (Exception e) {
          e.printStackTrace();
          log.error(String.format("Error [%s] making url(%s) template: %s, doctype: %s, seed: %s, lang: %s",
                  e.getMessage(), engine.name, engine.template, docType, seed, langEntry.engineLang));
        }
        
        
      }
    }
    try {
    executor.invokeAll(downloadList);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    executor.shutdownNow();

  }
}
