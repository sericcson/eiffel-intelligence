/*
    Copyright 2017 Ericsson AB.
    For a full list of individual contributors, please see the commit history.
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.ericsson.ei.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import com.ericsson.ei.controller.model.QueryResponse;
import com.ericsson.ei.controller.model.QueryResponseEntity;
import com.ericsson.ei.queryservice.ProcessAggregatedObject;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * This class represents the REST GET mechanism to extract the aggregated data
 * on the basis of the ID from the aggregatedObject.
 */
@Component
@CrossOrigin
public class QueryAggregatedObjectControllerImpl implements QueryAggregatedObjectController {

    private static Logger LOGGER = LoggerFactory.getLogger(QueryAggregatedObjectControllerImpl.class);

    @Autowired
    private ProcessAggregatedObject processAggregatedObject;

    /**
     * This method is responsible for the REST Get mechanism to extract the
     * aggregated data on the basis of the ID from the aggregatedObject.
     *
     * @param id
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<QueryResponse> getQueryAggregatedObject(@RequestParam("ID") final String id) {
        ObjectMapper mapper = new ObjectMapper();
        QueryResponseEntity queryResponseEntity = new QueryResponseEntity();
        QueryResponse queryResponse = new QueryResponse();
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        try {
            List<String> response = processAggregatedObject.processQueryAggregatedObject(id);
            if (!response.isEmpty()) {
                queryResponseEntity = mapper.readValue(response.get(0), QueryResponseEntity.class);
                httpStatus = HttpStatus.OK;
            }

            queryResponse.setQueryResponseEntity(queryResponseEntity);
            LOGGER.debug("The response is: " + response.toString());
            return new ResponseEntity<>(queryResponse, httpStatus);
        } catch (Exception e) {
            String errorMessage = "Failed to extract the aggregated data from the Aggregated Object based on ID " + id
                    + ". Error message:\n" + e.getMessage();
            LOGGER.error(errorMessage, e);
            queryResponse.setAdditionalProperty("errorMessage", errorMessage);
            return new ResponseEntity<>(queryResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}