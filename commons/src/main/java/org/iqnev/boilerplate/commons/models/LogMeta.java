package org.iqnev.boilerplate.commons.models;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RegisterForReflection
public class LogMeta {

    private String traceId;

    private String correlationId;

    private String user;

    private String userId;

    private String companyId;

    private String msgId;
}