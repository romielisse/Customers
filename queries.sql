use customers;

select * from customer order by customerid desc;
select * from company order by companyid desc;

-- SET foreign_key_checks = 1;
-- delete from company where companyname is null;

-- rename table customers to customer;
-- alter table customer add customerId bigint(20) not null primary key auto_increment;
-- alter table customer drop company_id;
alter table company change company CompanyName text;

-- List all the people in the customer's table who's last name is ______.
select * from customer
where lastname='smith';

-- List all the customers and their employers and positions that live in ______
select firstname, lastname, position, company.companyname, city, state
from customer
inner join company on customer.companyid = company.companyID
order by lastname, firstname, company.companyname;

-- List top 10 employers by count of employees
select company.companyname, count(customer.customerid) as total_employees
from company
inner join customer on company.companyid = customer.companyID
group by company.companyname
order by total_employees desc;

-- List of states and the number of people in each
select customer.state, count(customer.customerid) as population
from customer
group by state
order by population desc;
