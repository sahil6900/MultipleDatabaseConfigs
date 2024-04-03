package org.multiple.databases.mutltipledatabaseconnectivity.controller;

import org.multiple.databases.mutltipledatabaseconnectivity.entity.mysql.Customer;
import org.multiple.databases.mutltipledatabaseconnectivity.entity.postgre.Card;
import org.multiple.databases.mutltipledatabaseconnectivity.repository.mysql.CustomerRepository;
import org.multiple.databases.mutltipledatabaseconnectivity.repository.postgre.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/multiple/db")
public class mysqlCustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardRepository cardRepository;

    @PostMapping("/save/customer")
    public Customer saveCustomer(@RequestBody Customer customer){
        Customer save = customerRepository.save(customer);

        return save;
    }

    @GetMapping("/customer/get/{id}")
    public Customer getCustomer(@PathVariable int id){
        Customer customer = customerRepository.findById(id).orElse(null);

        return customer;
    }

    @PostMapping("/save/card")
    public Card saveCard(@RequestBody Card card){
        Card save = cardRepository.save(card);

        return save;
    }

    @GetMapping("/card/get/{id}")
    public Card getCard(@PathVariable int id){
        Card card = cardRepository.findById(id).orElse(null);

        return card;
    }
}
