package org.sj.service;

import java.util.ArrayList;
import java.util.List;

import org.sj.entity.Topic;
import org.sj.exception.DataNotFoundException;
import org.sj.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TopicService 
{
	@Autowired
	private TopicRepository tR;

	public List<Topic> getTopics() {
		List<Topic> t=new ArrayList<>();
		tR.findAll().forEach(t::add);
		return t;
	}
	
	public Topic getTopic(String id) 
	{
		Topic t=new Topic();
		t=tR.findOne(id);
		if(t==null) 
		{
			throw new DataNotFoundException("Topic with id : "+id+" not found");
		}
		return tR.findOne(id);
	}

	public void addTopic(Topic topic) 
	{
		tR.save(topic);
	}

	public void updateTopic(Topic topic, String id) 
	{
		tR.save(topic);
	}

	public void deleteTopic(String id) 
	{
		tR.delete(id);
	}
}
