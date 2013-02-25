package com.best.phonemanager.util;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Debug;

import com.best.phonemanager.entity.ProcessInfo;

/**
 * @author ZhangShuaiQi
 * @date 2013-2-22 下午1:43:54
 */
public class AppUtil {

	private ActivityManager mActivityManager;
	private Context context;

	public AppUtil(Context context) {
		super();
		this.context = context;
		mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
	}

	// 获得系统进程信息
	public List<ProcessInfo> getRunningAppProcessInfo() {
		// ProcessInfo Model类 用来保存所有进程信息
		List<ProcessInfo> processInfoList = new ArrayList<ProcessInfo>();

		// 通过调用ActivityManager的getRunningAppProcesses()方法获得系统里所有正在运行的进程
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
				.getRunningAppProcesses();

		for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
			// 进程ID号
			int pid = appProcessInfo.pid;
			// 用户ID 类似于Linux的权限不同，ID也就不同 比如 root等
			int uid = appProcessInfo.uid;
			// 进程名，默认是包名或者由属性android：process=""指定
			String processName = appProcessInfo.processName;
			// 获得该进程占用的内存
			int[] myMempid = new int[] { pid };
			// 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息
			Debug.MemoryInfo[] memoryInfo = mActivityManager
					.getProcessMemoryInfo(myMempid);
			// 获取进程占内存用信息 kb单位
			int memSize = memoryInfo[0].dalvikPrivateDirty;

			if(appProcessInfo.processName.equals("system") || appProcessInfo.processName.equals("com.android.systemui")){  
                continue;  
            }
			PackageUtil packageUtil = new PackageUtil(context);
			PackageInfo tempAppInfo = packageUtil.getApplicationInfo(appProcessInfo.processName);
			if(tempAppInfo != null){
				// 构造一个ProcessInfo对象
				ProcessInfo processInfo = new ProcessInfo();
				processInfo.setPid(pid);
				processInfo.setUid(uid);
				processInfo.setMemorySize(memSize);
				processInfo.setPackageName(appProcessInfo.processName);
				processInfo.setName(packageUtil.getApplicationInfo(processName).applicationInfo.loadLabel(context.getApplicationContext().getPackageManager()) + "");
				processInfo.setIcon(packageUtil.getApplicationInfo(processName).applicationInfo.loadIcon(context.getApplicationContext().getPackageManager()));
				processInfoList.add(processInfo);
			}
		}
		return processInfoList;
	}
	
	public class PackageUtil
	{
	    // ApplicationInfo 类，保存了普通应用程序的信息，主要是指Manifest.xml中application标签中的信息
	    private List<PackageInfo> allAppList;
	   
	    public PackageUtil(Context context) {
	        // 通过包管理器，检索所有的应用程序（包括卸载）与数据目录
	        PackageManager pm = context.getApplicationContext().getPackageManager();
	        allAppList = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
	    }
	   
	   
	    public PackageInfo getApplicationInfo(String appName) {
	        if (appName == null) {
	            return null;
	        }
	        for (PackageInfo appinfo : allAppList) {
	            if (appName.equals(appinfo.applicationInfo.processName)) {
	                return appinfo;
	            }
	        }
	        return null;
	    }
	}

}
