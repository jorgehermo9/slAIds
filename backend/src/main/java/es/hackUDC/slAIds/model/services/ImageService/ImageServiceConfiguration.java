package es.hackUDC.slAIds.model.services.ImageService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import es.hackUDC.slAIds.model.services.ImageService.conditions.DefaultCondition;
import es.hackUDC.slAIds.model.services.ImageService.conditions.StableDiffusionCondition;

@Configuration
public class ImageServiceConfiguration {
    @Bean
    @Conditional(StableDiffusionCondition.class)
    public ImageService getStableDiffusionService() {
        return new ImageServiceStableDiffusion();
    }

    @Bean
    @Conditional(DefaultCondition.class)
    public ImageService getBasicService() {
        return new ImageServiceBasic();
    }
}
