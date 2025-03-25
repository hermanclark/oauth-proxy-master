package org.nwea.oauthproxy.endpoint;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
public class ELBEndpoint implements Endpoint<ResponseEntity<String>> {

    String SUCCESS = "{\n" +
            "  \"status\": \"OK\"\n" +
            "}";
    @Override
    public String getId() {
        return "status";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public ResponseEntity<String> invoke() {
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
}
