package com.ce.spring2.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ce.spring2.demo.model.dao.DemoDao;

@Service
public class DemoServiceImpl implements DemoService {
	@Autowired
	private DemoDao demoDao;
}
