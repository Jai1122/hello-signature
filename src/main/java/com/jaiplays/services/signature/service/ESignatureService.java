package com.jaiplays.services.signature.service;

import com.hellosign.sdk.HelloSignClient;


import com.hellosign.sdk.HelloSignException;
import com.hellosign.sdk.resource.SignatureRequest;
import com.hellosign.sdk.resource.TemplateSignatureRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ESignatureService {

    @Value("${TEMPLATE_ID}")
    private String templateId;

    @Value("${HELLO_SIGN_API_KEY}")
    private String apiKey;

    public String send() throws HelloSignException {
        HelloSignClient client = new HelloSignClient(apiKey);
        TemplateSignatureRequest request = new TemplateSignatureRequest();
        request.setSubject("Sample Email Subject");
        request.setSigner("Client","test-hello-sign@yopmail.com","Test Client Name");
        request.setCustomFieldValue("name", "Jai's Play");
        request.setTemplateId(templateId);
        request.setTestMode(true);
        SignatureRequest newRequest = client.sendTemplateSignatureRequest(request);
        return newRequest.toString();
    }
}
