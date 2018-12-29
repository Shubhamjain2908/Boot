package org.sj.controller;

import java.util.List;

import org.sj.entity.Topic;
import org.sj.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController 
{
	@Autowired
	private TopicService ts;
	
	@RequestMapping("/topics")
	public List<Topic> topic() 
	{
		return ts.getTopics();	//returning objects
	}
	
	@RequestMapping("/topics/{id}")
	public Topic getTopic(@PathVariable String id) 
	{
		return ts.getTopic(id);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/topics")
	public void addTopic(@RequestBody Topic topic) 
	{
		ts.addTopic(topic);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/topics/{id}")
	public void updateTopic(@RequestBody Topic topic,@PathVariable String id) 
	{
		ts.updateTopic(topic,id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/topics/{id}")
	public void deleteTopic(@PathVariable String id) 
	{
		ts.deleteTopic(id);
	}
}
