package com.logistics.deliveryscheduler.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Stores request scoped data.
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter @Setter @NoArgsConstructor
public class RequestContext {

    /**
     * Request ID.
     */
    private String requestId;

}
