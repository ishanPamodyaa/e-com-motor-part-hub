package com.motorcycleparts.ecommerce.repositories;

import com.motorcycleparts.ecommerce.models.Address;
import com.motorcycleparts.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
    
    @Query("SELECT a FROM Address a WHERE a.user = :user AND a.isDefault = true")
    Optional<Address> findDefaultAddress(@Param("user") User user);
} 