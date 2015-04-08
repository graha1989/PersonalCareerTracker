package com.pct.service.util;

import com.pct.domain.Professor;
import com.pct.domain.PublicationCategory;
import com.pct.domain.dto.PublicationDto;

public class PublicationUtil {

	public static Professor createOrUpdatePublicationInstanceFromPublicationDto(PublicationDto publicationDto,
			Professor professor, PublicationCategory category) {

		if (publicationDto.getId() == null) {
			professor.creatreNewProfessorPublication(category, publicationDto.getIsbn(), publicationDto.getTitle(),
					publicationDto.getAuthors(), publicationDto.getPublisher(), publicationDto.getPageRange(),
					publicationDto.getPublicationType(), publicationDto.getQuoted());
		} else {
			professor.editProfessorPublication(category, publicationDto.getIsbn(), publicationDto.getTitle(),
					publicationDto.getAuthors(), publicationDto.getPublisher(), publicationDto.getPageRange(),
					publicationDto.getQuoted(), publicationDto.getPublicationType(), publicationDto.getId());
		}

		return professor;
	}

}
