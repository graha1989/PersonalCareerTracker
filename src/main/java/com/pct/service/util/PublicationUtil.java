package com.pct.service.util;

import com.pct.domain.Professor;
import com.pct.domain.PublicationCategory;
import com.pct.domain.dto.InternationalPublicationDto;
import com.pct.domain.dto.ProfessorPublicationDto;

public class PublicationUtil {

	public static Professor createOrUpdateProfessorPublicationInstanceFromPublicationDto(
			ProfessorPublicationDto professorPublicationDto, Professor professor, PublicationCategory category) {

		if (professorPublicationDto.getId() == null) {
			professor.creatreNewProfessorPublication(category, professorPublicationDto.getIsbn(),
					professorPublicationDto.getTitle(), professorPublicationDto.getJournalTitle(),
					professorPublicationDto.getAuthors(), professorPublicationDto.getPublisher(),
					professorPublicationDto.getPageRange(), professorPublicationDto.getPublicationType(),
					professorPublicationDto.getQuoted(), professorPublicationDto.getYear());
		} else {
			professor.editProfessorPublication(category, professorPublicationDto.getIsbn(),
					professorPublicationDto.getTitle(), professorPublicationDto.getJournalTitle(),
					professorPublicationDto.getAuthors(), professorPublicationDto.getPublisher(),
					professorPublicationDto.getPageRange(), professorPublicationDto.getQuoted(),
					professorPublicationDto.getYear(), professorPublicationDto.getPublicationType(),
					professorPublicationDto.getId());
		}

		return professor;
	}

	public static Professor createOrUpdateInternationalPublicationInstanceFromPublicationDto(
			InternationalPublicationDto internationalPublicationDto, Professor professor, PublicationCategory category) {

		if (internationalPublicationDto.getId() == null) {
			professor.creatreNewInternationalPublication(category, internationalPublicationDto.getIsbn(),
					internationalPublicationDto.getTitle(), internationalPublicationDto.getJournalTitle(),
					internationalPublicationDto.getAuthors(), internationalPublicationDto.getPublisher(),
					internationalPublicationDto.getPagesWithQuotes(), internationalPublicationDto.getPublicationType(),
					internationalPublicationDto.getYear());
		} else {
			professor.editInternationalPublication(category, internationalPublicationDto.getIsbn(),
					internationalPublicationDto.getTitle(), internationalPublicationDto.getJournalTitle(),
					internationalPublicationDto.getAuthors(), internationalPublicationDto.getPublisher(),
					internationalPublicationDto.getPagesWithQuotes(), internationalPublicationDto.getYear(),
					internationalPublicationDto.getPublicationType(), internationalPublicationDto.getId());
		}

		return professor;

	}
}
