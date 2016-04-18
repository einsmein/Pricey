package com.repository;

import com.domain.Rebate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RebateBaseRepo<T extends Rebate> extends JpaRepository<Rebate, Integer> {
}
