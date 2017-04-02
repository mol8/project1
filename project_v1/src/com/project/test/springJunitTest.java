package com.project.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.project.pojo.Users;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/<context>.xml" })
public class springJunitTest {
	
	@Test
	public void testSpringTest(){
		String string = "hola";
		assertNotNull("Success", string);
	}

}
