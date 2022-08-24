package com.ce.spring2.todo.model.service;

import java.util.List;
import java.util.Map;

import com.ce.spring2.todo.model.dto.Todo;

public interface TodoService {

	List<Todo> selectAllTodo();

	int insertTodo(Todo todo);

	int updateTodo(Map<String, Object> param);

}
