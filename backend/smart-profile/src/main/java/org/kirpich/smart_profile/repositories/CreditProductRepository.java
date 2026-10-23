package org.kirpich.smart_profile.repositories;

import org.kirpich.smart_profile.models.CreditProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditProductRepository extends JpaRepository<CreditProduct, Integer> {
    @Query("select cp from CreditProduct cp")
    Page<CreditProduct> findAllPageable(Pageable pageable);
}
