package xposed.hkrpg.xposed.hooks

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.getObjectAs
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.github.kyuubiran.ezxhelper.utils.isNotStatic
import com.github.kyuubiran.ezxhelper.utils.isPublic
import com.github.kyuubiran.ezxhelper.utils.paramCount
import de.robv.android.xposed.callbacks.XC_LoadPackage
import xposed.hkrpg.utils.APP_LABEL_12306
import xposed.hkrpg.utils.APP_LABEL_HKRPG
import xposed.hkrpg.utils.ICON_12306_BASE64
import xposed.hkrpg.utils.ICON_HKRPG_BASE64
import xposed.hkrpg.utils.PACKAGE_NAME_12306
import xposed.hkrpg.utils.PACKAGE_NAME_HKRPG


fun hookPackageManagerForXingQiongTieDaoAnd12306(lpparam: XC_LoadPackage.LoadPackageParam) {
    // 12306 -> 穹轨
    hookIcon(PACKAGE_NAME_12306, ICON_HKRPG_BASE64)
    hookLabel(PACKAGE_NAME_12306, APP_LABEL_HKRPG)

    // 穹轨 -> 12306
    hookIcon(PACKAGE_NAME_HKRPG, ICON_12306_BASE64)
    hookLabel(PACKAGE_NAME_HKRPG, APP_LABEL_12306)


    /*

        // ========== 图标
        findMethod("android.content.pm.PackageItemInfo") {
            name == "loadIcon" && returnType == Drawable::class.java && paramCount == 1 && isNotStatic && isPublic
        }.hookBefore {
            if (*/
    /*((it.args[0] as PackageManager).getObjectAs<String>("mPackageName", String::class.java) == "com.MobileTicket") ||*//*
 (it.thisObject.getObjectAs<String>("packageName", String::class.java) == "com.MobileTicket")) {
            val bytes: ByteArray = Base64.decode(ICON_HKRPG_BASE64, Base64.DEFAULT)
            val myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            it.result = BitmapDrawable(myBitmap)
        }
    }

    findMethod("android.content.pm.PackageItemInfo") {
        name == "loadIcon" && returnType == Drawable::class.java && paramCount == 1 && isNotStatic && isPublic
    }.hookBefore {
        if (it.thisObject.getObjectAs<String>("packageName", String::class.java) == "com.MobileTicket") {
            val bytes: ByteArray = Base64.decode(ICON_HKRPG_BASE64, Base64.DEFAULT)
            val myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            it.result = BitmapDrawable(myBitmap)
        }
    }

    // ========== 标题
    findMethod("android.app.ApplicationPackageManager") {
        name == "getText" && returnType == CharSequence::class.java && isNotStatic && isPublic
    }.hookBefore {
        if (it.args[0] == "com.MobileTicket") {
            it.result = APP_LABEL_HKRPG
        }
    }

    findMethod("android.content.pm.PackageItemInfo") {
        name == "loadLabel" && returnType == CharSequence::class.java && paramCount == 1 && isNotStatic && isPublic
    }.hookBefore {
        val packageName = it.thisObject.getObjectAs<String>("packageName", String::class.java)
        if (packageName == "com.MobileTicket") {
            it.result = APP_LABEL_HKRPG
        }
    }

*/

    // ========== 旧代码
    /*findMethod("android.app.ApplicationPackageManager") {
        name == "getApplicationInfo" && returnType == ApplicationInfo::class.java && isNotStatic && isPublic
    }.hookAfter {
        val mAppinfo = it.result as ApplicationInfo
        if (mAppinfo.packageName == "com.MobileTicket") {
            //it.result =
        }
    }*/


    /*findMethod("android.app.ApplicationPackageManager") {
        name == "getApplicationIcon" && returnType == Drawable::class.java && isPublic && isNotStatic
    }.hookBefore {
        val bytes: ByteArray = Base64.decode(HKRPG_ICON_BASE64, Base64.DEFAULT)
        val myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        it.result = BitmapDrawable(myBitmap)
    }*/

    /*
        findAllConstructors(PackageItemInfo::class.java) {
            paramCount == 1 && isNotStatic && isPublic
        }.hookAfter {
            //val packageName = it.thisObject.getObjectAs<String>("packageName", String::class.java)
            it.thisObject.putObject("name", "啊哈哈哈", String::class.java)
        }*/

    /*var serviceManagerHook: XC_MethodHook.Unhook? = null
    serviceManagerHook = findMethod("android.os.ServiceManager") {
        name == "addService"
    }.hookBefore { param ->
        if (param.args[0] == "package") {
            serviceManagerHook?.unhook()
            val pms = param.args[1] as PackageManager

            findMethod(pms::class.java, findSuper = true) {
                name == "getPackageInfo"
            }.hookAfter {
                try {
                    val packageName = it.args[0] as String
                    XposedBridge.log("hooked! package name = $packageName")
                    if (packageName.contains("com.MobileTicket")) {
                        // Modify the app name and icon
                        val packageInfo = it.result as PackageInfo
                        val appInfo = packageInfo.applicationInfo

                        // New app name
                        appInfo.loadLabel(it.thisObject as PackageManager)
                        val newAppName: CharSequence = "New App Name"
                        XposedHelpers.setObjectField(appInfo, "mLabel", newAppName)

                        // New app icon (replace "bitmap" with your desired bitmap)
                        //val newAppIcon: Drawable = bitmap
                        //XposedHelpers.setObjectField(appInfo, "mIcon", newAppIcon)
                    }
                } catch (e: Exception) {
                    XposedBridge.log(e)
                }
            }

        }
    }*/
}

fun hookIcon(goalPkgName: String, newIconBase64: String) {
    findMethod("android.content.pm.PackageItemInfo") {
        name == "loadIcon" && returnType == Drawable::class.java && paramCount == 1 && isNotStatic && isPublic
    }.hookBefore {
        if ((it.thisObject.getObjectAs<String>(
                "packageName",
                String::class.java
            ) == goalPkgName) || (it.thisObject.getObjectAs<String>(
                "packageName",
                String::class.java
            ) == goalPkgName)
        ) {
            val bytes: ByteArray = Base64.decode(newIconBase64, Base64.DEFAULT)
            val myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            it.result = BitmapDrawable(myBitmap)
        }
    }
}

fun hookLabel(goalPkgName: String, newAppLabel: String) {
    /*    findMethod("android.app.ApplicationPackageManager") {
            name == "getText" && returnType == CharSequence::class.java && isNotStatic && isPublic
        }.hookBefore {
            if (it.args[0] == goalPkgName) {
                it.result = newAppLabel
            }
        }*/

    findMethod("android.content.pm.PackageItemInfo") {
        name == "loadLabel" && returnType == CharSequence::class.java && paramCount == 1 && isNotStatic && isPublic
    }.hookBefore {
        val packageName = it.thisObject.getObjectAs<String>("packageName", String::class.java)
        if (packageName == goalPkgName) {
            it.result = newAppLabel
        }
    }
}