package com.example.demo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    long countByState(String state);

    Iterable<Customer> findAllByLastnameContainingIgnoreCase(String lastname);
    Iterable<Customer> findAllByCompanyAndPosition(Company company, String position);
    Iterable<Customer> findAll(Pageable pageable);

    //@Query("select count(e) from Customer e where e.state=:id")
    //long countPopulationByState(@Param("id") String id);

    //long countCustomersByState(String state);
}
