package com.example.demo;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaQuery;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Iterable<Customer> findAllByLastnameContainingIgnoreCase(String lastname);
    Iterable<Customer> findAllByCompanyAndPosition(Company company, String position);
    long countByCompany(Company company);

    Iterable<Customer> findAll(Pageable pageable);


}
