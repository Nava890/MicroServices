package com.ecommerce.customer;

import com.ecommerce.customer.Exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(customerMapper.toCustomer(request));
        return customer.getId();
    }

    public void update(CustomerRequest request) {
        var customer = customerRepository.findById(request.id)
                .orElseThrow(()->new CustomerNotFoundException(
                        String.format("Cannot update customer:: No Customer found with the provider id %s",request.id)
                ));
        mergeCustomoer(customer,request);
        customerRepository.save(customer);
    }

    private void mergeCustomoer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.id))customer.setId(request.id);
        if(StringUtils.isNotBlank(request.firstName))customer.setFirstName(request.firstName);
        if(StringUtils.isNotBlank(request.lastName))customer.setLastName(request.lastName);
        if(StringUtils.isNotBlank(request.email))customer.setEmail(request.email);
        if(request.address!=null)customer.setAddress(request.address);


    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean exsistsById(String customerId) {
        return customerRepository.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(()->new CustomerNotFoundException(String.format("There is no customer found for this customer id::%s",customerId)));
    }
}
