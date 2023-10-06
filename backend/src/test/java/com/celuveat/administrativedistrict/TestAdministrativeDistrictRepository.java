package com.celuveat.administrativedistrict;

import com.celuveat.administrativedistrict.domain.AdministrativeDistrict;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("test")
public interface TestAdministrativeDistrictRepository extends JpaRepository<AdministrativeDistrict, Long> {
}
