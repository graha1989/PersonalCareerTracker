package com.pct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pct.domain.Korisnik;

/**
 * Spring Data repository for Korisnik(User) entity
 * 
 * @author a.grahovac
 *
 */
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

}
