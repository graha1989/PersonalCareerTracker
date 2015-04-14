package com.pct.service.util;

import com.pct.domain.Professor;
import com.pct.domain.PublicationCategory;
import com.pct.domain.dto.ProfessorPublicationDto;

public class PublicationUtil {

	public static Professor createOrUpdatePublicationInstanceFromPublicationDto(
			ProfessorPublicationDto professorPublicationDto, Professor professor, PublicationCategory category) {

		if (professorPublicationDto.getId() == null) {
			professor.creatreNewProfessorPublication(category, professorPublicationDto.getIsbn(),
					professorPublicationDto.getTitle(), professorPublicationDto.getJournalTitle(),
					professorPublicationDto.getAuthors(), professorPublicationDto.getPublisher(),
					professorPublicationDto.getPageRange(), professorPublicationDto.getPublicationType(),
					professorPublicationDto.getQuoted());
		} else {
			professor.editProfessorPublication(category, professorPublicationDto.getIsbn(),
					professorPublicationDto.getTitle(), professorPublicationDto.getJournalTitle(),
					professorPublicationDto.getAuthors(), professorPublicationDto.getPublisher(),
					professorPublicationDto.getPageRange(), professorPublicationDto.getQuoted(),
					professorPublicationDto.getPublicationType(), professorPublicationDto.getId());
		}

		return professor;
	}

}
