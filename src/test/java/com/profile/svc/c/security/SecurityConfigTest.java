package com.profile.svc.c.security;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityConfigTest {

    private final CorsConfigurationSource corsConfigurationSource =
            new SecurityConfig(new CorsProperties(List.of(
                    "http://localhost:4200",
                    "https://www.johnnieh.me"
            ))).corsConfigurationSource();

    @Test
    void allowsConfiguredBrowserOriginsOnly() {
        CorsConfiguration configuration = configurationFor("/api/client/details");

        assertTrue(configuration.checkOrigin("http://localhost:4200") != null);
        assertTrue(configuration.checkOrigin("https://www.johnnieh.me") != null);
        assertFalse(configuration.checkOrigin("https://unapproved.example") != null);
    }

    @Test
    void allowsOnlyReadAndPreflightMethodsFromBrowserOrigins() {
        CorsConfiguration configuration = configurationFor("/api/client/details");

        assertTrue(configuration.checkHttpMethod(HttpMethod.GET) != null);
        assertTrue(configuration.checkHttpMethod(HttpMethod.OPTIONS) != null);
        assertFalse(configuration.checkHttpMethod(HttpMethod.POST) != null);
        assertEquals(Boolean.FALSE, configuration.getAllowCredentials());
    }

    private CorsConfiguration configurationFor(String path) {
        HttpServletRequest request = new MockHttpServletRequest("GET", path);
        return corsConfigurationSource.getCorsConfiguration(request);
    }
}
