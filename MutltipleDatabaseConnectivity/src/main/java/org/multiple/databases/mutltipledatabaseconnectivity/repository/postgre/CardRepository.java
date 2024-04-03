package org.multiple.databases.mutltipledatabaseconnectivity.repository.postgre;

import org.multiple.databases.mutltipledatabaseconnectivity.entity.postgre.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Integer> {
}
