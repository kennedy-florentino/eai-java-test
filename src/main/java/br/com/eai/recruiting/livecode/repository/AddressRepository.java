package br.com.eai.recruiting.livecode.repository;

import br.com.eai.recruiting.livecode.domain.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByZipCode(String zipCode, Pageable pageable);
}
