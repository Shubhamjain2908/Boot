package org.sj.repository;

import java.util.List;

import org.sj.entity.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lessons, String> {
	public List<Lessons> findByCourseId(String courseId);
}
