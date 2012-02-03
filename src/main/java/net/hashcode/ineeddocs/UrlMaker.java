package net.hashcode.ineeddocs;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;
import org.stringtemplate.v4.ST;

/**
 *
 * @author shairon
 */
public class UrlMaker {

  private static final Logger log = Logger.getLogger(UrlMaker.class);

  public static URL make(Engine engine, String engineLang, String docType, String seed) throws MalformedURLException {

    ST template = new ST(engine.template).add("docType", docType).add("lang", engineLang).add("seed", seed);

    return new URL(template.render());

  }
}
