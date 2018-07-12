package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Long>{

    Iterable<Company> findByCompanyname(String companyname);

    @Query("select size(c.customers) from Company c where c.companyid=:id")
    long countCompaniesByCompanyid(@Param("id") long id);


}
