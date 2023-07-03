package br.com.eai.recruiting.livecode.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorMessage {
    private Integer status;
    private String message;
    private String description;
    private Instant timestamp;
}
