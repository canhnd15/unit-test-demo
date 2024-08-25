package com.davidnguyen.unittestdemo.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Fail.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    protected ResultActions performGetRequest(
            String path,
            Object... uriVars
    ) {
        return performGetRequestWithQuery(path, Map.of(), uriVars);
    }

    protected ResultActions performGetRequestWithQuery(
            String path,
            Map<String, List<String>> queryParams,
            Object... uriVars
    ) {
        ResultActions resultActions = null;

        try {
            resultActions = mockMvc.perform(
                    getGetBuilder(path, uriVars)
//                            .with(csrf())
                            .queryParams(new LinkedMultiValueMap<>(queryParams))
            );
        } catch (Exception e) {
            fail("GET-request failed.", e);
        }

        return resultActions;
    }

    protected MockHttpServletRequestBuilder getGetBuilder(
            String basePath,
            Object... uriVars
    ) {
        HttpHeaders headers = new HttpHeaders();

        return MockMvcRequestBuilders
                .get(basePath, uriVars)
                .secure(true)
                .headers(headers);
    }
}

