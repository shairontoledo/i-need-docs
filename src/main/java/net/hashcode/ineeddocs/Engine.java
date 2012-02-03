package net.hashcode.ineeddocs;

/**
 *
 * @author shairon
 */
public class Engine {

  String template;
  String name;

  public Engine(String name, String template) {
    this.name = name;
    this.template = template;
  }

  @Override
  public String toString() {
    return name;
  }
}
