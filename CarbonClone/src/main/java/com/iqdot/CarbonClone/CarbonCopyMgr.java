package com.iqdot.CarbonClone;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.zip.Checksum;

public abstract class CarbonCopyMgr {
	
	private final ExecutorService executor;
	private Map<Path,Path> filesToCopy;
	private List<Future<Integer>> copyResult;
	private Buffer buffSize;
	
	public CarbonCopyMgr(ExecutorService exec, Map<Path,Path> fileMap, List<Future<Integer>> futureList) {
		executor = exec;
		filesToCopy = fileMap;
		copyResult = futureList;
	}
	
	public Buffer getBuffSize() {
		return buffSize;
	}

	public void setBuffSize(Buffer buffSize) {
		this.buffSize = buffSize;
	}

	/**
	 * Create a single copy task that will populate a collection of files to copy.
	 * This does not start the copy process, to start the copy process, call startCopyTask()
	 * 
	 * @param destination The destination path, this defaults to the same name, if the directory is same, then _copy is appended
	 * @param files The list of files to copy
	 */
	public boolean createCopyTask(Path destination, Path ... files) {
		
		for (Path file : files) {
			filesToCopy.put(file, destination);
		}
		return true;
	}
	
	/**
	 * This will asynchronously start the copy process and empty the current map of files to copy, which allows
	 * to re-populate the map again and add further copy operations while the existing ones run.
	 * @return The array of futures holding the result of the operation
	 */
	public final List<Future<Integer>> startCopyTask() {
		
		for (Map.Entry<Path, Path> entry : filesToCopy.entrySet()) {
			Path orig  = entry.getKey();
			Path clone = entry.getValue();
			final CarbonCopier copier;
			try {
				copier = new CarbonCopier(new FileInputStream(orig.toString()),
										  new FileOutputStream(clone.toString()),
										  getChecksum(),
										  getBuffSize().getBuffer());
				copyResult.add(executor.submit(copier));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return copyResult;
	}
	
	public abstract Checksum getChecksum();
}
