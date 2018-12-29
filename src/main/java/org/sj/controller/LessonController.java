package org.sj.controller;

import java.util.List;

import org.sj.entity.Course;
import org.sj.entity.Lessons;
import org.sj.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LessonController 
{
	@Autowired
	private LessonService lS;
	
	@RequestMapping("/topics/{topicId}/course/{courseId}/lesson")
	public List<Lessons> getAllLesson(@PathVariable String courseId)
	{
		return lS.getAllLesson(courseId);
	}
	
	@RequestMapping("/topics/{topicId}/course/{courseId}/lesson/{id}")
	public Lessons getOnelesson(@PathVariable String id) 
	{
		return lS.getLesson(id);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/topics/{topicId}/course/{courseId}/lesson")
	public void addLesson(@RequestBody Lessons lesson,@PathVariable String courseId) 
	{
		lesson.setCourse(new Course(courseId,"","",""));
		lS.addLessons(lesson);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/topics/{topicId}/course/{courseId}/lesson/{id}")
	public void updateLesson(@RequestBody Lessons lesson,@PathVariable String id) 
	{
		lS.updateLessons(lesson, id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/topics/{topicId}/course/{courseId}/lesson/{id}")
	public void deleteLesson(@PathVariable String id) 
	{
		lS.deleteLessons(id);
	}
	
}
