package com.jaiplays.services.signature.controller;

import com.hellosign.sdk.HelloSignException;
import com.hellosign.sdk.resource.Event;
import com.jaiplays.services.signature.service.ESignatureService;
import lombok.extern.java.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Log
public class ESignatureController {

    @Value("${HELLO_SIGN_API_KEY}")
    private String apiKey;

    @Autowired
    private ESignatureService eSignatureService;

    @PostMapping("/sign")
    public String send() throws HelloSignException {
        return eSignatureService.send();
    }

    @PostMapping("/hellosign/webhook")
    public String webhook(@RequestParam String json) throws JSONException, HelloSignException {
        JSONObject jsonObject = new JSONObject(json);
        Event event = new Event(jsonObject);
        boolean isValidRequest = event.isValid(apiKey);

        if(isValidRequest){
            switch (event.getTypeString()){
                case "signature_request_sig ned":
                    log.info("Signature Request Signed ");
                    break;
                case "signature_request_sent":
                    log.info("Signature Request Sent");
                    break;
                default:
                    break;
            }
        }

        return "Hello API Event Received";
    }

}
