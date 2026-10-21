package org.kirpich.smart_profile.repositories;

import org.kirpich.smart_profile.models.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Integer> {
}