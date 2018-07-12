package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TopTenEmployersRepository topTenEmployersRepository;

    Pageable resultLimit = new PageRequest(0,100);

    @RequestMapping("/")
    private String getIndex(Model model)
    {
        model.addAttribute("customers", customerRepository.findAll(resultLimit));
        return "index";
    }

    @GetMapping("/search")
    public String getSearchForm(Model model) {
        return "search";
    }

    @PostMapping("/lastnameSearch")
    public String getLastNameSearch(HttpServletRequest request, Model model) {
        String lastname = request.getParameter("lastname");
        model.addAttribute("customers", customerRepository.findAllByLastnameContainingIgnoreCase(lastname));

        return "index";
    }

    @PostMapping("/employmentSearch")
    public String getEmploymentSearch(HttpServletRequest request, Model model) {
        String companyname = request.getParameter("companyname");
        String position = request.getParameter("position");
        Company company = companyRepository.findByCompanyname(companyname).iterator().next();
        model.addAttribute("customers", customerRepository.findAllByCompanyAndPosition(company, position));

        return "index";
    }

    @RequestMapping("/topten")
    public String getEmployers(Model model){
        // List<Company> companies = companyRepository.findAll();
        model.addAttribute("list", topTenEmployersRepository.findAll());
        return "topcompanies";
    }

    @GetMapping("/addupdate")
    public String getCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("companies", companyRepository.findAll());
        return "addupdate";
    }

    @PostMapping("/processCustomer")
    public String processForm(Customer customer, Model model) {
        customerRepository.save(customer);
        model.addAttribute("customer", customer);
        return "detail";
    }

    @GetMapping("/addCompany")
    public String getCompanyForm(Model model){
        model.addAttribute("company", new Company());
        return "company";
    }

    @PostMapping("/addCompany")
    public String processForm(Company company) {
        companyRepository.save(company);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCustomer(@PathVariable("id") long id, Model model) {
        model.addAttribute("customer", customerRepository.findById(id).get());
        return "detail";
    }

    @RequestMapping("/update/{id}")
    public String updateCustomer(@PathVariable("id") long id, Model model) {
        model.addAttribute("customer", customerRepository.findById(id));
        model.addAttribute("companies", companyRepository.findAll());
        return "addupdate";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id) {
        customerRepository.deleteById(id);
        return "redirect:/";
    }
}
