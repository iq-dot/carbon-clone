package com.iqdot.CarbonClone;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;

/**
 * A single thread that handles the task of a single copy operation
 */
public final class CarbonCopier implements Callable<Integer> 
{
	
	private final InputStream fileToCopy;
	private final OutputStream copyToDestination;
	private final byte[] bufferSize;
	private Checksum checksum;
	
	/**
	 * An instance is only for a single copy operation
	 */
	public CarbonCopier(InputStream fileCopy, OutputStream copyDestination, Checksum check, byte[] buffSize) {
		fileToCopy = fileCopy;
		copyToDestination = copyDestination;
		bufferSize = buffSize;
		checksum = check;
	}
	
	/**
	 * @return - -1 on failure, 0+ for success with number of error(s).
	 */
	public Integer call() throws Exception {
		
		Integer errCount = -1;
		
		Long startTime = System.currentTimeMillis();
		
		CheckedInputStream cin   = new CheckedInputStream(fileToCopy, checksum);
		CheckedOutputStream cout = new CheckedOutputStream(copyToDestination, checksum);
		
		while(cin.read(bufferSize) != -1) {
			cout.write(bufferSize);
			if (cin.getChecksum().getValue() != cout.getChecksum().getValue()) {
				errCount++;
			}
		}
		
		cin.close();
		cout.close();
		
		Long stopTime = System.currentTimeMillis();
		
		System.out.println(stopTime - startTime);
		
		return ++errCount;
	}
}
