CREATE VIEW TopTenEmployers AS
select company.companyname, count(customer.customerid) as total_employees
from company
inner join customer on company.companyid = customer.companyID
group by company.companyname
order by total_employees desc
limit 10;

