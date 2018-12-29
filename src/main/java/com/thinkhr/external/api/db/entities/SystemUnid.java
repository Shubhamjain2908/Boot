package com.thinkhr.external.api.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "app_system_unid")
@Data
public class SystemUnid {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id") 
    private Integer id;
    
}
