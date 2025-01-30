package com.greenmart.app.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenmart.app.domain.entities.Vendor;

@Repository
public interface VendorRespository extends JpaRepository<Vendor, UUID> {

}
