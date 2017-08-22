package com.deal;

import java.io.File;

public interface ReadFile<T,V,K>{
	public  T   readFile(File file);
	public   int[][] staticsinglefile(V v,K k);


}
