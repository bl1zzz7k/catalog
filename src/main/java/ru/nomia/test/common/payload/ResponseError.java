package ru.nomia.test.common.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseError implements Serializable {
    private static final long serialVersionUID = 5249213765734673818L;
    private int code;
    private String message;

    public ResponseError(HttpStatusCodeException ex) {
        this.code = ex.getRawStatusCode();
        this.message = ex.getStatusText();
    }
}
