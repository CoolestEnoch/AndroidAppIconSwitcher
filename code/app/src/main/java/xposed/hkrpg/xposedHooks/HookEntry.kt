package xposed.hkrpg.xposedHooks

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

class HookEntry : IXposedHookLoadPackage, IXposedHookZygoteInit {

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
    }

    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        /*
        lpparam: XC_LoadPackage.LoadPackageParam
         */
        val packageName: String = lpparam.packageName
        EzXHelperInit.initHandleLoadPackage(lpparam)

        //模块激活状态
        if (lpparam.packageName == "xposed.hkrpg"/*BuildConfig.APPLICATION_ID*/) {
            findMethod("xposed.hkrpg.MainActivity") {
                name == "isActive" && returnType == Boolean::class.java
            }.hookBefore {

                it.result = true
            }
        }

        hookAndroidForXingQiongTieDaoAnd12306(lpparam)
        // 星穹铁道
        /*if("android" == packageName){
        }*/
    }
}