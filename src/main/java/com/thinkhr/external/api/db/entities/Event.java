package com.thinkhr.external.api.db.entities;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Entity for Events
 * 
 * @author Surabhi Bhawsar
 * @since 2018-03-05
 *
 */

@Entity
@Table(name = "app_events")
@Data
@DynamicUpdate
@DynamicInsert
@JsonInclude(Include.NON_EMPTY) 
public class Event {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id") 
    @JsonIgnore
    private Integer id;
    
    @JsonIgnore
    @Column(name = "contact_id")
    private Integer contactId;
    
    @JsonIgnore
    @Column(name = "company_id")
    private Integer companyId;
    
    @JsonIgnore
    @Column(name = "app_id")
    private Integer appId;
    
    @JsonIgnore
    @Column(name = "event_id")
    private Integer eventId;
    
    @JsonIgnore
    @Column(name = "object_company_id")
    private Integer objectCompanyId;
    
    @JsonIgnore
    @Column(name = "object_type_id")
    private Integer objectTypeId;
    
    @Column(name = "object_type_name")
    private String objectTypeName;
    
    @JsonIgnore
    @Column(name = "object_id")
    private Integer objectId;
    
    @JsonIgnore
    @Column(name = "action_id")
    private Integer actionId;
    
    @Column(name = "action_name")
    private String actionName;
    
    @JsonIgnore
    @Column(name = "assoc_object_type_id")
    private Integer assocObjectTypeId;
    
    @JsonIgnore
    @Column(name = "assoc_object_type_name")
    private String assocObjectTypeName;
    
    @JsonIgnore
    @Column(name = "assoc_object_id")
    private Integer assocObjectId;
    
    @Column(name = "event_context")
    private String requestData;
    
    @JsonIgnore
    @Column(name = "state_before")
    private String stateBefore;
    
    @JsonIgnore
    @Column(name = "state_after")
    private String stateAfter;
    
    @JsonIgnore
    @Column(name = "created")
    private Long created;
    
    @Transient
    private String appName;
    
    @Transient
    private String apiEndPointClass;
    
    @Transient
    private Integer impersonatorId;
    
    @Transient
    private Integer userId;
    
    @Transient
    private String route;
    
    @Transient
    private Map<String,String> headers;
    
    @Transient
    private Map<String,String[]> queryParams;

}
