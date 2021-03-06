package com.best.phonemanager.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

/**
 * @author ZhangShuaiQi
 * @date 2013-2-22 下午4:12:21
 */
public class CpuManager {
	String TAG = "CpuManager";
	Context mContext;
	
	
	public CpuManager(Context mContext) {
		super();
		this.mContext = mContext;
	}

	// 内存

	public long getAvailMemory() {// 获取android当前可用内存大小  
		  
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);  
        MemoryInfo mi = new MemoryInfo();  
        am.getMemoryInfo(mi);  
        //mi.availMem; 当前系统的可用内存  
        return mi.availMem;
//        return Formatter.formatFileSize(mContext, mi.availMem);// 将获取的内存大小规格化  
    }  
  
	public long getTotalMemory() {  
        String str1 = "/proc/meminfo";// 系统内存信息文件  
        String str2;  
        String[] arrayOfString;  
        long initial_memory = 0;  
  
        try {  
            FileReader localFileReader = new FileReader(str1);  
            BufferedReader localBufferedReader = new BufferedReader(  
                    localFileReader, 8192);  
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小  
  
            arrayOfString = str2.split("\\s");  
            for (String num : arrayOfString) {  
                Log.i(str2, num);  
            }  
  
            initial_memory = Integer.valueOf(arrayOfString[arrayOfString.length-2]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte  
            localBufferedReader.close();  
            return initial_memory;
  
        } catch (IOException e) {  
        	return 0;
        }  
//        return Formatter.formatFileSize(mContext, initial_memory);// Byte转换为KB或者MB，内存大小规格化  
    }  
	
	/**
	 * 获取已用内存的百分比
	 * @return
	 */
	public int getPercentageUsedRam(){
		double shengyu = getAvailMemory() / 1E8;
		double total = getTotalMemory() / 1E8;
		double yiyong = total-shengyu;
		double p = yiyong/total;
		int yiyongStr = (int) (p * 100);
		return yiyongStr;
	}
	
	// 3、Rom大小

	public long[] getRomMemroy() {
		long[] romInfo = new long[2];
		// Total rom memory
		romInfo[0] = getTotalInternalMemorySize();

		// Available rom memory
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		romInfo[1] = blockSize * availableBlocks;
		return romInfo;
	}

	public long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	// 4、sdCard大小

	public long[] getSDCardMemory() {
		long[] sdCardInfo = new long[2];
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long bSize = sf.getBlockSize();
			long bCount = sf.getBlockCount();
			long availBlocks = sf.getAvailableBlocks();

			sdCardInfo[0] = bSize * bCount;// 总大小
			sdCardInfo[1] = bSize * availBlocks;// 可用大小
		}
		return sdCardInfo;
	}
}
