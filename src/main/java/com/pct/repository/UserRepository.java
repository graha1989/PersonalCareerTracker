package com.pct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pct.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Retrieves User by email.
	 * 
	 * @param email
	 * @return user
	 */
	User findByEmail(String email);

	/**
	 * Retrieves Users by email.
	 * 
	 * @param email
	 * @return user
	 */

	@Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
	List<User> findByEmailLike(@Param("email") String email);

	/**
	 * Retrieves user by username.
	 * 
	 * @param username
	 * @return user
	 */
	User findByUserName(String userName);

	/**
	 * Retrieves users by username like.
	 * 
	 * @param username
	 * @return user
	 */

	@Query("SELECT u FROM User u WHERE u.userName LIKE %:username%")
	List<User> findByUsernameLike(@Param("username") String username);

	/**
	 * Retrieves list of users with partially matching username or email.
	 * 
	 * @param username
	 * @param email
	 * @return list of users
	 */

	@Query("SELECT u FROM User u WHERE u.userName LIKE %:username% AND u.email LIKE %:email%")
	List<User> findByUsernameLikeAndEmailLike(@Param("username") String username, @Param("email") String email);

}
