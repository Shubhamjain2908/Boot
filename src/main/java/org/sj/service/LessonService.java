package org.sj.service;

import java.util.ArrayList;
import java.util.List;

import org.sj.entity.Lessons;
import org.sj.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonService 
{
	@Autowired
	private LessonRepository lR;
	
	public List<Lessons> getAllLesson(String courseId) 
	{
		List<Lessons> lesson= new ArrayList<>();
		lR.findByCourseId(courseId).forEach(lesson::add);
		return lesson;
	}

	public Lessons getLesson(String id) 
	{
		return lR.findOne(id);
	}

	public void addLessons(Lessons lesson) 
	{
		lR.save(lesson);
	}

	public void updateLessons(Lessons lesson, String id) 
	{
		lR.save(lesson);
	}

	public void deleteLessons(String id) 
	{
		lR.delete(id);
	}
}
