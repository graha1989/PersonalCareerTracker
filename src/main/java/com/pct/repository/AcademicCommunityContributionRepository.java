package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.AcademicCommunityContribution;
import com.pct.domain.enums.AcademicCommunityContributionType;

public interface AcademicCommunityContributionRepository extends JpaRepository<AcademicCommunityContribution, Long> {

	@Query("SELECT a FROM AcademicCommunityContribution a JOIN a.professor p WHERE p.id=:professorId AND a.type=:type")
	List<AcademicCommunityContribution> findAllContributions(@Param("professorId") Long professorId,
			@Param("type") AcademicCommunityContributionType type);

}
