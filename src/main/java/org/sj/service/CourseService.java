package org.sj.service;

import java.util.ArrayList;
import java.util.List;

import org.sj.entity.Course;
import org.sj.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService 
{
	@Autowired
	private CourseRepository cR;
	
	public List<Course> getAllCourses(String topicId) 
	{
		List<Course> course= new ArrayList<>();
		cR.findByTopicId(topicId).forEach(course::add);
		return course;
	}

	public Course getCourse(String id) 
	{
		return cR.findOne(id);
	}

	public void addCourse(Course course) 
	{
		cR.save(course);
	}

	public void updateCourse(Course course, String id) 
	{
		cR.save(course);
	}

	public void deleteCourse(String id) 
	{
		cR.delete(id);
	}

}
