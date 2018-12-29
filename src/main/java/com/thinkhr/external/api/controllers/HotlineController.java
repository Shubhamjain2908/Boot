package com.thinkhr.external.api.controllers;

import com.thinkhr.external.api.exception.ApplicationException;
import com.thinkhr.external.api.exception.MessageResourceHandler;
import com.thinkhr.external.api.model.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.thinkhr.external.api.ApplicationConstants.BROKER_ID_PARAM;

/**
 * Configuration Controller for performing operations
 * related with Configuration object.
 * 
 * @author Sudhakar Kaki
 * @since 2018-02-22
 * 
 * 
 */
@RestController
@Validated
@RequestMapping(path = "/v1/hotlineitem")
public class HotlineController {
    
    private Logger logger = LoggerFactory.getLogger(HotlineController.class);

    @Autowired
    MessageResourceHandler resourceHandler;

    /**
     * Add an alternate configuration in database
     * 
     */
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<KeyValuePair> addItem(@Valid @RequestBody KeyValuePair hotlineItem,
            @RequestAttribute(name = BROKER_ID_PARAM) Integer brokerId)
            throws ApplicationException {
        logger.debug("************** Hotline Item BEGINS *****************");
        logger.debug(hotlineItem.getKey());
        logger.debug(hotlineItem.getValue());
        logger.debug("************** Hotline Item BEGINS *****************");
        return new ResponseEntity<KeyValuePair>(hotlineItem, HttpStatus.CREATED);
    }

}
