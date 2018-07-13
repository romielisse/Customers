package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @RequestMapping("/company")
    private String getCompanies(Model model)
    {
        model.addAttribute("companies", companyRepository.findAll());
        return "companylist";
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

    @PostMapping("/processCompany")
    public String processCompanyForm(Company company) {
        companyRepository.save(company);
        return "redirect:/company";
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

    @RequestMapping("/updateCompany/{id}")
    public String updateCompany(@PathVariable("id") long id, Model model) {
        model.addAttribute("company", companyRepository.findById(id));
        return "company";
    }

    @RequestMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable("id") long id) {
        companyRepository.deleteById(id);
        return "redirect:/company";
    }

    @RequestMapping("/topten")
    public String getEmployers(Model model){
        HashMap<String,Long> TopTen = new HashMap<>();

        Iterable<Company> companies = companyRepository.findAll();
        Iterator<Company> iter = companies.iterator();

        // Put company names and count into new HashMap
        while(iter.hasNext()){
            Company company = iter.next();
            TopTen.put(company.getCompanyname(),companyRepository.countCompaniesByCompanyid(company.getCompanyid()));
        }

        // Sort map
        //This comparator sorts by HashMap values.
        Comparator <Map.Entry<String, Long>> sortCompare =
                (Map.Entry<String, Long> firstValue, Map.Entry<String, Long> secondValue)
                        -> secondValue.getValue().compareTo(firstValue.getValue());

        //This is the list that will hold each entry from the map.
        List<Map.Entry<String, Long>> orderedList = new ArrayList<>();

        //Pulls the data from the existing map.
        orderedList.addAll(TopTen.entrySet());

        // Sort with the comparator we made.
        Collections.sort(orderedList, sortCompare);

        // Limit results to top 10
        ArrayList<Map.Entry<String,Long>> orderedListTopTen = new ArrayList<>();
        int count = 0;

        for (Map.Entry entry : orderedList) {
            if (count < 10) {
                orderedListTopTen.add(entry);
                count++;
            }
        }

        model.addAttribute("list", orderedListTopTen);
        return "topten";
    }

    @RequestMapping("/population")
    public String getPopulation(Model model) {
        HashMap<String,Long> Population = new HashMap<>();
        ArrayList<String> states = new ArrayList<>();

        Iterable<Customer> customers = customerRepository.findAll();
        Iterator<Customer> iter = customers.iterator();

        // Put distinct states into array list
        while(iter.hasNext()){
            Customer customer = iter.next();
            if(!states.contains(customer.getState())) {
                states.add(customer.getState());
            }
        }

        for(String state : states) {
            Population.put(state,customerRepository.countByState(state));
        }

        model.addAttribute("list", Population);
        //model.addAttribute("num", customerRepository.countCustomersByState(new Customer().getState()));
        return "population";
    }
}
