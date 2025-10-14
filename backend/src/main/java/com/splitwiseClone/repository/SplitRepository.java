package com.splitwiseClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.splitwiseClone.model.Split;

@Repository
public interface SplitRepository extends JpaRepository<Split, Long>{

}
