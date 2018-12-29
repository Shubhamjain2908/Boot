package org.sj.repository;

import java.util.List;

import org.sj.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
	
	public List<Course> findByTopicId(String topicId);
	
}
