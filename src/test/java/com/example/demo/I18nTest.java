package com.example.demo;

import com.example.demo.auth.config.I18n;
import org.junit.jupiter.api.Test;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class I18nTest {

    @InjectMocks
    private I18n i18n;

    @Mock
    private InterceptorRegistry registry;

    @Test
    void testAddInterceptors() {
        i18n.addInterceptors(registry);
        verify(registry).addInterceptor(i18n.localeChangeInterceptor());
    }
}