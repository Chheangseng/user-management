package com.tcs.user_auth_management.repository;

import com.tcs.user_auth_management.model.entity.CacheStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheStoreRepository extends JpaRepository<CacheStore,String> {}
