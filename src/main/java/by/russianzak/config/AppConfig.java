package by.russianzak.config;

import by.russianzak.service.mapper.HouseEntityDtoMapper;
import by.russianzak.service.mapper.RoadSurfaceEntityDtoMapper;
import by.russianzak.service.mapper.StreetEntityDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"by.russianzak"})
public class AppConfig implements WebMvcConfigurer {

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    messageConverter.setObjectMapper(objectMapper);
    messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
    converters.add(messageConverter);
  }

  @Bean
  public StreetEntityDtoMapper streetEntityDtoMapper() {
    return StreetEntityDtoMapper.INSTANCE;
  }

  @Bean
  public HouseEntityDtoMapper houseEntityDtoMapper() {
    return HouseEntityDtoMapper.INSTANCE;
  }

  @Bean
  public RoadSurfaceEntityDtoMapper roadSurfaceEntityDtoMapper() {
    return RoadSurfaceEntityDtoMapper.INSTANCE;
  }
}
