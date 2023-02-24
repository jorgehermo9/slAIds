package es.hackUDC.slAIds.model.services.transferObjects;

import java.util.List;

public class IndexTransfer {

	private List<String> slideTitles;
	private List<String> slideDescriptions;
	
	public IndexTransfer() {
		super();
	}
	public IndexTransfer(List<String> slideTitles, List<String> slideDescriptions) {
		super();
		this.slideTitles = slideTitles;
		this.slideDescriptions = slideDescriptions;
	}
	public List<String> getSlideTitles() {
		return slideTitles;
	}
	public void setSlideTitles(List<String> slideTitles) {
		this.slideTitles = slideTitles;
	}
	public List<String> getSlideDescriptions() {
		return slideDescriptions;
	}
	public void setSlideDescriptions(List<String> slideDescriptions) {
		this.slideDescriptions = slideDescriptions;
	}
	
	
	
	
}
