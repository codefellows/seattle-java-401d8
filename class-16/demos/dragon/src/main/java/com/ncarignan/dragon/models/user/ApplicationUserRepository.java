package com.ncarignan.dragon.models.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    public ApplicationUser findByUsername(String username);
    // if I work within the bounds of acceptable requests / straightforward ones,
    // JPA knows how to write something as simple as `SELECT * FROM application_user WHERE username = $1`
    // findBy is key + Username is key
//    public ApplicationUser findByFavoritePet(String pet);
    // this line will not work because there is no column/ field of favorite_pet
}

