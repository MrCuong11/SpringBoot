package com.example.authdemo.service;

import com.example.authdemo.repository.StoreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreRepository.StoreRevenueProjection> getStoreRevenue2021() {
        return storeRepository.findStoreRevenue2021();
    }
} 