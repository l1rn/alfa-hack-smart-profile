package org.kirpich.smart_profile.repositories;

import org.kirpich.smart_profile.models.CreditProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditProductRepository extends JpaRepository<CreditProduct, Integer> {
}
