package com.cesarfl.verificationservice.integration.persistence;

import com.cesarfl.verificationservice.domain.VerificationSource;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "verifications")
public class VerificationEntity {

    @Id
    private UUID verificationId;

    private String queryText;

    private Instant timestamp;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String resultJson;

    @Enumerated(EnumType.STRING)
    private VerificationSource source;

    protected VerificationEntity() {
    }

    public VerificationEntity(UUID verificationId,String queryText, Instant timestamp, String resultJson,  VerificationSource source) {
        this.resultJson = resultJson;
        this.timestamp = timestamp;
        this.queryText = queryText;
        this.verificationId = verificationId;
        this.source = source;
    }

    public UUID getVerificationId() {
        return verificationId;
    }

    public String getQueryText() {
        return queryText;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getResultJson() {
        return resultJson;
    }

    public VerificationSource getSource() {
        return source;
    }
}
