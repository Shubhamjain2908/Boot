package com.thinkhr.external.api.services;

import static com.thinkhr.external.api.ApplicationConstants.APP_AUTH_DATA;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.segment.analytics.Analytics;
import com.segment.analytics.messages.TrackMessage;
import com.thinkhr.external.api.db.entities.Event;
import com.thinkhr.external.api.repositories.EventRepository;
import com.thinkhr.external.api.services.events.EventBuilder;

/**
 * Event Tracking Service
 * 
 * @author Surabhi Bhawsar
 * @since 2018-03-05
 *
 */

@Service
public class EventService {
    
    @Autowired
    protected EventRepository eventRepository;
    
    @Autowired
    EventBuilder eventBuilder;
    
    @Value("${com.thinkhr.external.api.event.persist}")
    private boolean persistAllowed;
    
    @Value("${com.thinkhr.external.api.event.integrationKey}")
    private String integrationKey;
    
    private Logger logger = LoggerFactory.getLogger(CompanyService.class);

    /**
     * To track the request
     * 
     * @param request
     */
    public void trackRequest(HttpServletRequest request) {
        trackEvent(request);
    }
    
    /**
     * To build and track the event from request
     * 
     * @param request
     */
    @Transactional
    private void trackEvent(HttpServletRequest request) {

        Event event = null;
        try {
            event = eventBuilder.buildEvent(request);
        } catch (IOException e) {
            logger.error("Error in Building Event", e);
        }
        
        if (persistAllowed) {
            eventBuilder.addAttributesToPersist(event, request);
            eventRepository.save(event);
        }
        
        trackEventInSegment(event, request);
    }

    /**
     * To track the created event in segment
     * 
     * @param event
     * @param request 
     */
    private void trackEventInSegment(Event event, HttpServletRequest request) {
        
        Analytics analytics = Analytics.builder(integrationKey).build();
        
        analytics.enqueue(TrackMessage.builder("app_events")
                .userId(request.getAttribute(APP_AUTH_DATA) != null ? eventBuilder.getContactId(request).toString() : "0")
                .properties(createSegmentMap(event))
                );
        
    }

    /**
     * Create map to track limited fields in Segment
     * 
     * @param event
     * @return
     */
    private Map<String, Object> createSegmentMap(Event event) {

        Map<String,Object> segmentMap = new HashMap<String,Object>();
        ObjectMapper mapper = new ObjectMapper();
        segmentMap = mapper.convertValue(event, Map.class);
        
        return segmentMap;
    }
}
