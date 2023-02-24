package es.hackUDC.slAIds.rest.dtos;

public class generationRequestDto {

	private String title;
	private String prompt;
	private int numSlides;

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public int getNumSlides() {
		return numSlides;
	}

	public void setNumSlides(int numSlides) {
		this.numSlides = numSlides;
	}
	
}
