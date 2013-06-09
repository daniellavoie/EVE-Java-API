package com.cspinformatique.eve.commons.repository.eve;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cspinformatique.eve.commons.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	@Query("FROM Item WHERE raceID <> 0 AND marketGroupID <> 0")
	public List<Item> findTradableItems();
}
