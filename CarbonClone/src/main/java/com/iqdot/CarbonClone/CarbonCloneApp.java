package com.iqdot.CarbonClone;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CarbonCloneApp {

	public static void main(String[] args) {

		Path filePath = Paths.get(args[0]);
		Path clonePath = Paths.get(args[1]);
		
		try (
			ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-master.xml");
			InputStream fileOrig = new FileInputStream(filePath.toString());
			OutputStream fileClone = new FileOutputStream(clonePath.toString())
		) {		
			CarbonCopyMgr copyMgr = context.getBean("carbonCopyMgr", CarbonCopyMgr.class);
			copyMgr.createCopyTask(clonePath, filePath);;
			copyMgr.startCopyTask();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
