package com.profile.svc.c.security;

import com.profile.svc.c.middleware.ClientDetailsRateLimitInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientDetailsRateLimitInterceptorTest {

    @Test
    void blocksRequestsAfterCapacityIsConsumed() throws Exception {
        ClientDetailsRateLimitInterceptor interceptor =
                new ClientDetailsRateLimitInterceptor(new RateLimitProperties(1, 60));
        MockHttpServletRequest request = requestFrom("192.0.2.1");

        assertTrue(interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));

        MockHttpServletResponse response = new MockHttpServletResponse();
        assertFalse(interceptor.preHandle(request, response, new Object()));
        assertEquals(429, response.getStatus());
        assertEquals("Rate limit exceeded", response.getErrorMessage());
        assertEquals("1", response.getHeader("Retry-After"));
    }

    @Test
    void tracksEachRemoteAddressSeparately() throws Exception {
        ClientDetailsRateLimitInterceptor interceptor =
                new ClientDetailsRateLimitInterceptor(new RateLimitProperties(1, 60));

        assertTrue(interceptor.preHandle(
                requestFrom("192.0.2.1"), new MockHttpServletResponse(), new Object()));
        assertTrue(interceptor.preHandle(
                requestFrom("192.0.2.2"), new MockHttpServletResponse(), new Object()));
    }

    private MockHttpServletRequest requestFrom(String remoteAddress) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr(remoteAddress);
        return request;
    }
}
