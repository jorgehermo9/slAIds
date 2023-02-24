package es.hackUDC.slAIds.rest.dtos;

public record generationRequestDto (

	String title,
	String prompt,
	int numSlides,
	int minWords,
	int maxWords,
	String font
) {}
