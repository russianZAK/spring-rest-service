package by.russianzak.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class[] getServletConfigClasses() {
    return new Class[] { AppConfig.class };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }

  @Override
  protected Class[] getRootConfigClasses() {
    return new Class[] {};
  }
}
