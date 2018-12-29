package com.thinkhr.external.api.services.events;

import static com.thinkhr.external.api.ApplicationConstants.APP_AUTH_DATA;
import static com.thinkhr.external.api.ApplicationConstants.REQUEST_BODY_JSON;
import static com.thinkhr.external.api.request.APIRequestHelper.getRequestHeaders;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkhr.external.api.db.entities.Event;
import com.thinkhr.external.api.db.entities.SystemUnid;
import com.thinkhr.external.api.db.entities.User;
import com.thinkhr.external.api.model.AppAuthData;
import com.thinkhr.external.api.repositories.SystemUnidRepository;
import com.thinkhr.external.api.services.CommonService;

/**
 * API Request Event Builder
 * 
 * @author Surabhi Bhawsar
 * @since 2018-03-05
 *
 */
@Service
public class EventBuilder extends CommonService{
    
    private static final Map<String, Integer> systemApps;
    private static final Map<String, Integer> systemObjects;
    private static final Map<String, Integer> objectActions;
    static
    {
        systemApps = new HashMap<String, Integer>();
        
        systemApps.put("app_portal",1);
        systemApps.put("app_engage",2);
        systemApps.put("app_learn",3);
        systemApps.put("app_login",4);
        systemApps.put("app_comply",5);
        systemApps.put("app_throne",6);
        systemApps.put("app_sendgrid",7);
        systemApps.put("app_twilio",8);
        systemApps.put("japi",9);
        
        systemObjects = new HashMap<String, Integer>();
        
        systemObjects.put("users",1);
        systemObjects.put("contact_user",2);
        systemObjects.put("card",3);
        systemObjects.put("playlist",4);
        systemObjects.put("playlist_send",5);
        systemObjects.put("sendgrid_email",6);
        systemObjects.put("twilio_sms",7);
        systemObjects.put("companies",8);
        systemObjects.put("portal_user",9);
        systemObjects.put("handbook",10);
        systemObjects.put("sku",11);
        systemObjects.put("configurations",12);
        systemObjects.put("role",13);
        systemObjects.put("app_throne",14);
        systemObjects.put("app_learn",15);
        
        objectActions = new HashMap<String, Integer>();
        
        objectActions.put("create",1);
        objectActions.put("view",2);
        objectActions.put("update",3);
        objectActions.put("delete",4);
        objectActions.put("email",5);
        objectActions.put("deliver",6);
        objectActions.put("send",7);
        objectActions.put("open",8);
        objectActions.put("click",9);
        objectActions.put("download",10);
        objectActions.put("bounce",11);
        objectActions.put("login",12);
        objectActions.put("logout",13);
        objectActions.put("start",14);
        objectActions.put("completed",15);
        objectActions.put("queue",16);
        objectActions.put("sms",17);
    }
    
    @Autowired
    protected SystemUnidRepository systemUnidRepository;

    /**
     * Build the Request Event
     * 
     * @param request
     * @param b 
     * @return
     * @throws IOException
     */
    public Event buildEvent(HttpServletRequest request) throws IOException {
        
        return createEventData(request);
    }
    
    /**
     * Create Event Data
     * 
     * @param request
     * @param b 
     * @return
     */
    private Event createEventData(HttpServletRequest request) {

        Event event  = new Event();
        
        event.setAppName("japi");
        event.setActionName(getActionName(request));//from request method
        event.setObjectTypeName(request.getRequestURI().split("/")[2]);//from request URI
        event.setApiEndPointClass(event.getObjectTypeName().substring(0,event.getObjectTypeName().length()-1).concat("Controller"));
        if (request.getAttribute(REQUEST_BODY_JSON) != null) {
            event.setRequestData(request.getAttribute(REQUEST_BODY_JSON).toString());
        }
        event.setImpersonatorId(0);//ImpersonationNotYetInJAPI
        event.setUserId(event.getContactId());
        event.setRoute(request.getRequestURI());
        event.setHeaders(getRequestHeaders());
        event.setQueryParams(request.getParameterMap());
        
        return event;
    }

    /**
     * Get Action Name
     * 
     * @param request
     * @return
     */
    private String getActionName(HttpServletRequest request) {

        String actionName;

        switch (request.getMethod()) {
        case "POST":
            actionName = "create";
            break;

        case "PUT":
            actionName = "update";
            break;

        default:
            actionName = request.getMethod().toLowerCase();
            break;
        }

        return actionName;
    }

    /**
     * Generate system unique id for event id
     * 
     * @return
     */
    private SystemUnid generateSystemUniqueId() {

        SystemUnid systemUnid = new SystemUnid();
        
        return systemUnidRepository.save(systemUnid);
    }
    
    
    /**
     * Get status before event
     * 
     * @param request
     * @param requestJson 
     * @return
     */
    private String getEventStateBefore(HttpServletRequest request, String requestJson){
        
        if(RequestMethod.POST.name().equalsIgnoreCase(request.getMethod())){
            return "";
        }else{
            return requestJson;
        }
        
    }
    
    /**
     * Get status after event
     * 
     * @param request
     * @param requestJson 
     * @return
     */
    private String getEventStateAfter(HttpServletRequest request, String requestJson){
        
        if(RequestMethod.POST.name().equalsIgnoreCase(request.getMethod()) || RequestMethod.PUT.name().equalsIgnoreCase(request.getMethod())){
            return requestJson;
        }else{
            return "";
        }
        
    }

    /**
     * To add additional attributes in event to persist in DB
     * 
     * @param event
     * @param request 
     * @return
     */
    public Event addAttributesToPersist(Event event, HttpServletRequest request) {
        
        AppAuthData appAuthData = (AppAuthData) request.getAttribute(APP_AUTH_DATA);
        String requestJson = request.getAttribute(REQUEST_BODY_JSON).toString();
        
        if(appAuthData!=null) {
            event.setContactId(getContactId(request));
            event.setCompanyId(appAuthData.getClientId());
        } else {
            event.setContactId(0); //To handle for dev environment, where we have disabled JWT authorization. For Prod it won't be a case
            event.setCompanyId(0);
        }
        
        
        event.setAppId(systemApps.get("japi"));//Default app id for JAPIs
        event.setEventId(generateSystemUniqueId().getId());
        event.setObjectCompanyId(event.getCompanyId());//RE company Id
        event.setObjectTypeId(systemObjects.get(event.getObjectTypeName()));
        event.setObjectId(getObjectId(request));//Id for object like userId, companyId. Possible in case of PUT/DELETE only
        event.setActionId(objectActions.get(event.getActionName()));//From Action pre-known Data
        event.setAssocObjectTypeId(0); // Null as basic operation for object
        event.setAssocObjectTypeName("");//Null as basic operation for object
        event.setAssocObjectId(0);//Null as basic operation for object
        event.setStateBefore(getEventStateBefore(request,requestJson));//A new method with logic
        event.setStateAfter(getEventStateAfter(request,requestJson));//A new method with logic
        event.setCreated(System.currentTimeMillis()/1000);
        
        return event;
    }

    /**
     * Get ContactId from User Repository
     * 
     * @param request
     * @return
     */
    public Integer getContactId(HttpServletRequest request) {
        
        AppAuthData appAuthData = (AppAuthData) request.getAttribute(APP_AUTH_DATA);
        
        User user = userRepository.findByUserName(appAuthData.getUser());
        if (user != null) {
            return userRepository.findByUserName(appAuthData.getUser()).getUserId();
        } else {
            return 0;
        }
    }

    /**
     * Get object Id from request.
     * 
     * @param request
     * @return
     */
    private Integer getObjectId(HttpServletRequest request) {

        if(RequestMethod.PUT.name().equalsIgnoreCase(request.getMethod()) || RequestMethod.DELETE.name().equalsIgnoreCase(request.getMethod())){
            int index = request.getRequestURI().lastIndexOf('/');
            return Integer.parseInt(request.getRequestURI().substring(index+1));
        }else{
            return 0;
        }
        
    }

}
