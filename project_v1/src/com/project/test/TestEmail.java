package com.project.test;

import static org.junit.Assert.assertTrue;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.project.util.SendMail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/<context>.xml" })
public class TestEmail {
	
	@Test
	public void testEmail(){
		//enviamos un correo de prueba.
		SendMail.sendMailTest("rayosx.spain@gmail.com", "test");
		assertTrue(true);
	}

}