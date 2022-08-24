package com.ce.spring2.todo.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ce.spring2.todo.model.dao.TodoDao;
import com.ce.spring2.todo.model.dto.Todo;

@Service
public class TodoServiceImpl implements TodoService {
	@Autowired
	private TodoDao todoDao;
	
	@Override
	public List<Todo> selectAllTodo() {
		return todoDao.selectAllTodo();
	}
	
	@Override
	public int insertTodo(Todo todo) {
		return todoDao.insertTodo(todo);
	}
	
	@Override
	public int updateTodo(Map<String, Object> param) {
		return todoDao.updateTodo(param);
	}
}
