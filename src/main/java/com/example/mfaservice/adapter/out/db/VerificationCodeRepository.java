package com.example.mfaservice.adapter.out.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
interface VerificationCodeRepository extends CrudRepository<VerificationCodeEntity, Long> {

    Optional<VerificationCodeEntity> findByEmail(String email);

    int deleteVerificationCodeEntitiesByExpirationTimestampBefore(LocalDateTime timestampt);
}

