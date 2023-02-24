package es.hackUDC.slAIds.model.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Index {

	private List<String> slideTitle;
	private List<String> slideDescription;
	
	
	public Index(List<String> slideTitle, List<String> slideDescription) {

		this.slideTitle = slideTitle;
		this.slideDescription = slideDescription;
	}


	public List<String> getSlideTitle() {
		return slideTitle;
	}

	public void setSlideTitle(List<String> slideTitle) {
		this.slideTitle = slideTitle;
	}

	public List<String> getSlideDescription() {
		return slideDescription;
	}

	public void setSlideDescription(List<String> slideDescription) {
		this.slideDescription = slideDescription;
	}

	
}