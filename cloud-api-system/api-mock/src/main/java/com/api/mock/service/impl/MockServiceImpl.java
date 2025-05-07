package com.api.mock.service.impl;

import com.api.mock.service.MockService;
import org.springframework.stereotype.Service;

/**
 * @author pengYuJun
 */
@Service
public class MockServiceImpl implements MockService {
    @Override
    public String hello() {
        return "hello";
    }
}
