package com.carrentalbackend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Component
public class RequestTool {
    @Autowired
    protected MockMvc mockMvc;

    public ResultActions sendGetRequest(String path) throws Exception {
        return mockMvc.perform(get(path));
    }

    public ResultActions sendPatchRequest(String path, String request) throws Exception {
        return mockMvc.perform(patch(path).contentType(MediaType.APPLICATION_JSON_VALUE).content(request));
    }

    public ResultActions sendPostRequest(String path, String request) throws Exception {
        return mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON_VALUE).content(request));
    }

    public ResultActions sendDeleteRequest(String path) throws Exception {
        return mockMvc.perform(delete(path));
    }

}
