package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.Institution;
import com.pct.domain.enums.InstitutionType;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

	@Query("SELECT i FROM Institution i WHERE i.name LIKE %:name% AND i.city LIKE %:city% AND i.country LIKE %:country%")
	Institution findByNameLikeAndCityLikeAndCountryLike(@Param("name") String name, @Param("city") String city,
			@Param("country") String country);

	@Query("SELECT i FROM Institution i WHERE i.name LIKE %:value% AND i.institutionType=:institutionType")
	List<Institution> findByNameAndInstitutionTypeLike(@Param("value") String value, @Param("institutionType") InstitutionType institutionType);
	
	@Query("SELECT i FROM Institution i WHERE i.name LIKE %:value%")
	List<Institution> findByNameLike(@Param("value") String value);
}
