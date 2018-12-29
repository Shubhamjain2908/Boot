package org.sj.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Entity
@Table(name = "ZipApi")
@Data
public class Zip 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String key;
	@Lob
	private List<MultipartFile> file;
}
