package com.ericsson.ei.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.ei.jmespath.JmesPathInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * This class implements the Interface for JMESPath API, generated by RAML 0.8
 * Provides interaction with JmesPathInterface class
 * 
 * Usage: 1. If input contains a rule as an argument and json expression in a
 * text file, then the following curl command may be used. curl -H
 * "Content-type: application/x-www-form-urlencoded" -X POST --data-urlencode
 * jsonContent@testjson.txt
 * http://localhost:8090/jmespathrule/runRule?rule=data.outcome
 * 
 * 2. If input contains rule and json expression as two String arguments, then
 * the following curl command may be used. curl -H "Content-type:
 * application/x-www-form-urlencoded" -X POST -d
 * jsonContent={"data":{"outcome":{"conclusion":"SUCCESSFUL"},"test":"persistentLogs"}}
 * http://localhost:8090/jmespathrule/runRule?rule=data.outcome
 * 
 */

@Component
@CrossOrigin
@Api(value = "jmespath")
@RequestMapping(value = "/jmespathrule/ruleCheck", produces = "application/json")
public class RuleCheckControllerImpl implements RuleCheckController {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionControllerImpl.class);

    @Autowired
    JmesPathInterface jmesPathInterface;

    /**
<<<<<<< HEAD
     * This method interacts with JmesPathInterface class method runRuleOnEvent
     * to evaluate a rule on JSON object.
     * 
     * @param rule-
     *            takes a String as a rule that need to be evaluated on JSON
     *            content
=======
     * This method interacts with JmesPathInterface class method runRuleOnEvent to
     * evaluate a rule on JSON object.
     * 
     * @param rule-
     *            takes a String as a rule that need to be evaluated on JSON content
>>>>>>> a516e969eb13fedb1741cf8c4f2d76d776655f23
     * @param jsonContent-
     *            takes JSON object as a String
     * @return return a String object
     * 
     */
    @Override
    @CrossOrigin
    @ApiOperation(value = "run rule on event")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> updateJmespathruleRuleCheck(String rule, String jsonContent) {
        String res = new String("[]");

        try {
            JSONObject jsonObj = new JSONObject(jsonContent);
            String jsonString = jsonObj.toString();
            res = jmesPathInterface.runRuleOnEvent(rule, jsonString).toString();
            LOG.info("Query :" + rule + " executed Successfully");
            return new ResponseEntity<String>(res, HttpStatus.OK);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<String>(res, HttpStatus.BAD_REQUEST);
        }
    }

}
