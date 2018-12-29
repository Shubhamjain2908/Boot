package org.sj.controller;

import java.util.List;

import org.sj.entity.Course;
import org.sj.entity.Topic;
import org.sj.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController 
{
	@Autowired
	private CourseService cS;
	
	@RequestMapping("/topics/{topicId}/course")
	public List<Course> course(@PathVariable String topicId) 
	{
		return cS.getAllCourses(topicId);	//returning objects
	}
	
	@RequestMapping("/topics/{topicId}/course/{id}")
	public Course getCourse(@PathVariable String id) 
	{
		return cS.getCourse(id);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/topics/{topicId}/course")
	public void addCourse(@RequestBody Course course,@PathVariable String topicId) 
	{
		course.setTopic(new Topic(topicId,"",""));
		cS.addCourse(course);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/topics/{topicId}/course/{id}")
	public void updateCourse(@RequestBody Course course,@PathVariable String topicId,@PathVariable String id) 
	{
		course.setTopic(new Topic(topicId,"",""));
		cS.updateCourse(course,id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/topics/{topicId}/course/{id}")
	public void deleteCourse(@PathVariable String id) 
	{
		cS.deleteCourse(id);
	}
}
