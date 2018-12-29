package com.thinkhr.external.api.db.learn.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Database entity object for the thinkhr_learn.mdl_role_assignments database table.
 * 
 * @author Ajay Jain
 * @since 2017-12-19
 *
 */
@Entity
@Table(name = "mdl_role_assignments")
@Data
public class LearnUserRoleAssignment {
    
    public static final Integer DEFAULT_CONTEXT_ID = 1;
    public static final Integer DEFAULT_MODIFIER_ID = 0;
    public static final String DEFAULT_COMPONENT = "";
    public static final Integer DEFAULT_ITEM_ID = 0;
    public static final Integer DEFAULT_SORT_ORDER = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "roleid")
    private LearnRole learnRole;

    @ManyToOne
    @JoinColumn(name = "userid")
    private LearnUser learnUser;
    

    @Column(name = "contextid")
    private Integer contextId;

    @Column(name = "timemodified")
    private Long timeModified;
    
    @Column(name = "modifierid")
    private Integer modifierId;
    
    @Column(name = "component")
    private String component;
    
    @Column(name = "itemid")
    private Integer itemId;
    
    @Column(name = "sortorder")
    private Integer sortOrder;
    
}