package com.github.marloww.moremocklocationapps;

import android.app.AppOpsManager;
import android.os.Build;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class XposedModule implements IXposedHookZygoteInit, IXposedHookLoadPackage {

    public static final int OP_MOCK_LOCATION = 58; // AppOpsManager.OP_MOCK_LOCATION

    public XC_MethodHook opHook;
    public XC_MethodHook finishOpHook;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "checkOp",
                    String.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "checkOp",
                    int.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "checkOpNoThrow",
                    String.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "checkOpNoThrow",
                    int.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "noteOp",
                    String.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "noteOp",
                    int.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "noteOpNoThrow",
                    String.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "noteOpNoThrow",
                    int.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "noteProxyOp",
                    String.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "noteProxyOp",
                    int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "noteProxyOpNoThrow",
                    String.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "noteProxyOpNoThrow",
                    int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "startOp",
                    String.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "startOp",
                    int.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "startOpNoThrow",
                    String.class, int.class, String.class, opHook);
            XposedHelpers.findAndHookMethod("android.app.AppOpsManager", lpparam.classLoader, "startOpNoThrow",
                    int.class, int.class, String.class, opHook);
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            opHook = new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Object o = param.args[0];
                    if (o.equals(OP_MOCK_LOCATION)
                            || o.equals(AppOpsManager.OPSTR_MOCK_LOCATION)) {
                        param.setResult(AppOpsManager.MODE_ALLOWED);
                    }
                }
            };

            finishOpHook = new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Object o = param.args[0];
                    if (o.equals(OP_MOCK_LOCATION)
                            || o.equals(AppOpsManager.OPSTR_MOCK_LOCATION)) {
                        param.setResult(null);
                    }
                }
            };
        }
    }
}