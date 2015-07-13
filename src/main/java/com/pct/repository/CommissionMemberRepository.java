package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.CommissionMember;

public interface CommissionMemberRepository extends JpaRepository<CommissionMember, Long> {

	@Query("SELECT m FROM CommissionMember m WHERE m.professor.id = :professorId")
	CommissionMember findMemberByProfessorId(@Param("professorId") Long professorId);

	@Query("SELECT m FROM CommissionMember m WHERE m.professor IS NULL AND m.id NOT IN :commissionMembersOnThisContestWhoAreNotProfessors AND concat(m.name, ' ', m.surname) LIKE %:value%")
	List<CommissionMember> findMembersWhoAreNotProfessorsAndNotCommissionMembersOnSelectedContest(@Param("value") String value,
			@Param("commissionMembersOnThisContestWhoAreNotProfessors") List<Long> commissionMembersOnThisContestWhoAreNotProfessors);

	@Query("SELECT m FROM CommissionMember m WHERE m.professor IS NULL AND concat(m.name, ' ', m.surname) LIKE %:value%")
	List<CommissionMember> findMembersWhoAreNotProfessorsAndNotCommissionMembersOnSelectedContest(@Param("value") String value);

	@Query("SELECT m FROM CommissionMember m WHERE m.professor IS NULL AND m.id NOT IN :commissionMembersOnThisContestWhoAreNotProfessors AND concat(m.name, ' ', m.surname) LIKE %:value%")
	List<CommissionMember> findMembersWhoAreNotProfessorsAndNotAlreadyUsed(@Param("value") String value,
			@Param("commissionMembersOnThisContestWhoAreNotProfessors") List<Long> commissionMembersOnThisContestWhoAreNotProfessors);

}
