package com.example.inghubtutorial;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.cglib.core.Local;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Customer Service Unit Test
 * @author taner geyhan
 */
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_createCustomer(){

        Customer customer = new Customer();
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setId(1L);
        customer.setUsedCreditLimit(1000.00);
        customer.setCreditLimit(50000.00);

        customerService.createCustomer(customer);
    }

    @Test
    void test_getAllCustomers(){

        Customer customer = new Customer();
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setId(1L);
        customer.setUsedCreditLimit(1000.00);
        customer.setCreditLimit(50000.00);

        Customer customerOther = new Customer();
        customerOther.setName("Name");
        customerOther.setSurname("Surname");
        customerOther.setId(2L);
        customerOther.setUsedCreditLimit(1000.00);
        customerOther.setCreditLimit(50000.00);

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerList.add(customerOther);

        Mockito.when(customerRepository.findAll()).thenReturn(customerList);

        List<Customer> actualCustomerList= customerService.getAllCustomers();
        Assertions.assertEquals(customerList.size(),actualCustomerList.size());
    }

    @Test
    void test_getCustomerById(){
        Customer customer = new Customer();
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setId(1L);
        customer.setUsedCreditLimit(1000.00);
        customer.setCreditLimit(50000.00);

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));

        Customer customerActual = customerService.getCustomerById(1L);

        Assertions.assertEquals(customerActual,customer);
    }

    @Test
    void test_deleteCustomer()throws Exception{
        Customer customer = new Customer();
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setId(1L);
        customer.setUsedCreditLimit(1000.00);
        customer.setCreditLimit(50000.00);

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1L);
    }

    @Test
    void test_deleteCustomer_throwException_CustomerNotFound()throws Exception{
        Customer customer = new Customer();
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setId(1L);
        customer.setUsedCreditLimit(1000.00);
        customer.setCreditLimit(50000.00);

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(null);

        Throwable throwable =  Assertions.assertThrows(Throwable.class, () -> {
            customerService.deleteCustomer(1L);
        });
        Assertions.assertEquals(NullPointerException.class, throwable.getClass());
    }

    @Test
    void test_updateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("Taner");
        customer.setSurname("Geyhan");
        customer.setId(1L);
        customer.setUsedCreditLimit(1000.00);
        customer.setCreditLimit(50000.00);

        Customer customerOther = new Customer();
        customerOther.setName("Name");
        customerOther.setSurname("Surname");
        customerOther.setId(2L);
        customerOther.setUsedCreditLimit(1000.00);
        customerOther.setCreditLimit(50000.00);

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));

        customerService.updateCustomer(1L,customerOther);
    }
}
