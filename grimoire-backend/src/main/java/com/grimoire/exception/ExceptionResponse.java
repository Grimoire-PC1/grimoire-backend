package com.grimoire.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionResponse {

    @JsonProperty("timestamp")
    @Schema(example = "2024-03-07T13:08:32.021076715")
    private LocalDateTime timestamp;
    @JsonProperty("message")
    @Schema(example = "message exception")
    private String message;
    @JsonProperty("errors")
    @ArraySchema(schema = @Schema(example = "There was an error here!"))
    private List<String> errors;

    public ExceptionResponse(Exception e) {
        this.timestamp = LocalDateTime.now();
        this.message = e.getMessage();
        this.errors = new ArrayList<>();
    }
}
