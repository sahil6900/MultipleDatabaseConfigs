package org.multiple.databases.mutltipledatabaseconnectivity.repository.mysql;

import org.multiple.databases.mutltipledatabaseconnectivity.entity.mysql.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
