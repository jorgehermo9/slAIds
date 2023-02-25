package es.hackUDC.slAIds.rest.dtos;

import java.util.ArrayList;
import java.util.List;

import es.hackUDC.slAIds.model.entities.Presentation;

public class PresentationConversor {
	
	private PresentationConversor() {}
	
	public final static PresentationDto toPresentationDto(Presentation presentation) {
		return new PresentationDto(presentation.getId(), presentation.getTitle());
	}

	
	public final static List<PresentationDto> toPresentationDtoList(List<Presentation> presentationList) {
		
		List<PresentationDto> presentationDtoList = new ArrayList<PresentationDto>();
		
		for(Presentation p : presentationList) {
			presentationDtoList.add(toPresentationDto(p));
		}
		
		return presentationDtoList;
	}
}
