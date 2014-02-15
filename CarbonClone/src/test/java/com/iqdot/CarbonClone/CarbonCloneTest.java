package com.iqdot.CarbonClone;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.zip.CRC32;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Suite of tests for CarbonClone.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-master.xml")
public class CarbonCloneTest 
{
	@Autowired
	public CarbonCopyMgr copyMgr = null;
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	private final String testWord = "Carbon Copy Test";
	private final String testFile = "testFile.txt";
	
    @Test
    public void testCarbonCopier() {
    	
    	ByteArrayInputStream  inStream  = new ByteArrayInputStream(testWord.getBytes());
    	ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        CarbonCopier copier = new CarbonCopier(inStream, outStream, new CRC32(), Buffer.Standard.getBuffer());
		try {
			Integer result = copier.call();
			assertTrue(result >= 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testCarbonCopyMgr() {
    	
    	try {
			folder.newFile(testFile);
			copyMgr.createCopyTask(Paths.get("."), Paths.get(testFile));
			copyMgr.startCopyTask();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
