package net.hashcode.ineeddocs;

import java.io.File;

/**
 *
 * @author shairon
 */
public class TestHelper {

  public static String getPath(String resourceName) {
    return ClassLoader.getSystemResource(resourceName).getPath();
  }

  public static File getFile(String resourceName) {
    return new File(getPath(resourceName));
  }
}
