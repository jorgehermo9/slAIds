package es.hackUDC.slAIds.model.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Index {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT[]")
    private List<String> slideTitles;

    @Column(columnDefinition = "TEXT[]")
    private List<String> slideDescriptions;

    public Index() {
    }

    public Index(List<String> slideTitles, List<String> slideDescriptions) {

        this.slideTitles = slideTitles;
        this.slideDescriptions = slideDescriptions;
    }

    public List<String> getSlideTitles() {
        return slideTitles;
    }

    public void setSlideTitle(List<String> slideTitles) {
        this.slideTitles = slideTitles;
    }

    public List<String> getSlideDescriptions() {
        return slideDescriptions;
    }

    public void setSlideDescriptions(List<String> slideDescriptions) {
        this.slideDescriptions = slideDescriptions;
    }

}
