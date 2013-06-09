package com.cspinformatique.eve.commons.repository.eve;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cspinformatique.eve.commons.model.Key;

public interface KeyRepository extends JpaRepository<Key, String> {
	public List<Key> findByUserId(Integer userId);
}