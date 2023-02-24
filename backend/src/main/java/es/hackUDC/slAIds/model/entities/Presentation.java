package es.hackUDC.slAIds.model.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Presentation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private String descriptionPrompt;
	
	@OneToOne
	private Index index;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="presentation")
	private List<Slide> slides;
	
	public Presentation() {}
	
	public Presentation(String title, String descriptionPrompt, Index index, List<Slide> slides) {
		this.title = title;
		this.descriptionPrompt = descriptionPrompt;
		this.index = index;
		this.slides = slides;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescriptionPrompt() {
		return descriptionPrompt;
	}
	public void setDescriptionPrompt(String descriptionPrompt) {
		this.descriptionPrompt = descriptionPrompt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

	public List<Slide> getSlides() {
		return slides;
	}

	public void setSlides(List<Slide> slides) {
		this.slides = slides;
	}
	

}
