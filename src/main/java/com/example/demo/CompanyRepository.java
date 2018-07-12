package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long>{

    Iterable<Company> findByCompanyname(String companyname);
}
