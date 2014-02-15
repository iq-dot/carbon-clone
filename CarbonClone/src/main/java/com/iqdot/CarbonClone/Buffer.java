package com.iqdot.CarbonClone;

/**
 * Enumerator class holding a list of supported buffer sizes.
 * @author iQ-Dot
 */
public enum Buffer {
	
	Min(1024), XSmall(2048), Small(4096), Standard(8192), Large(16384), XLarge(32768), Max(65536);
	
	private final int buffSize;
	
	Buffer(int buffSize) {
		this.buffSize = buffSize;
	}
	
	int getBuffSize() {
		return buffSize;
	}
	
	byte[] getBuffer() {
		return new byte[buffSize];
	}
}
