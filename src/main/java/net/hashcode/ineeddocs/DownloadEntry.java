/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.hashcode.ineeddocs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Callable;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 *
 * @author shairon
 */
public class DownloadEntry implements Runnable , Callable {

  private final URL docUrl;
  private static final Logger log = Logger.getLogger(DownloadEntry.class);
  private final File destination;

  public DownloadEntry(URL docUrl, File destination) {
    this.docUrl = docUrl;
    this.destination = destination;
  }

  
  public void download() {

    try {

      log.info(String.format("Downloading %s ", destination.getPath()));

      HttpClient dclient = new DefaultHttpClient();
      HttpResponse resp = dclient.execute(new HttpGet(docUrl.toURI()));
      HttpEntity he = resp.getEntity();

      InputStream in = he.getContent();
      FileOutputStream docOut = new FileOutputStream(destination);
      byte buff[] = new byte[2048];
      int count = -1;
      while ((count = in.read(buff)) != -1) {
        docOut.write(buff, 0, count);
      }
      docOut.flush();
      docOut.close();
      in.close();
      dclient.getConnectionManager().closeExpiredConnections();
    } catch (Exception e) {
      log.error(String.format("Downloading %s, error[%s]: %s",docUrl, e,e.getMessage()));
    }
  }

  public void run() {
    download();
  }

  public Object call() throws Exception {
    download();
    return null;
  }

  
}
