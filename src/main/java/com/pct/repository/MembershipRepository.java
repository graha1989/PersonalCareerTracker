package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.ScientificProfessionalOrgMem;

public interface MembershipRepository extends JpaRepository<ScientificProfessionalOrgMem, Long> {

	@Query("SELECT m FROM ScientificProfessionalOrgMem m JOIN m.professor p WHERE p.id=:professorId")
	List<ScientificProfessionalOrgMem> findAllMemberships(@Param("professorId") Long professorId);

}
