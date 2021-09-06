package com.phiz.common.auth.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.phiz.common.auth.service.OTPUtilityService;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPUtilityServiceImpl implements OTPUtilityService {


    private static final Integer EXPIRE_MIN = 5;

    private LoadingCache<String, Integer> otpCache;

    public OTPUtilityServiceImpl() {
        super();

        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    @Override
    public Integer generateOTP(String key) {

        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        otpCache.put(key, OTP);
        return OTP;
    }

    @Override
    public Integer getOPTByKey(String key) {

        return otpCache.getIfPresent(key);
    }

    @Override
    public void clearOTPFromCache(String key) {

        otpCache.invalidate(key);
    }
}
