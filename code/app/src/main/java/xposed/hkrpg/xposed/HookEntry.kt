package xposed.hkrpg.xposed

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.IXposedHookInitPackageResources
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam
import de.robv.android.xposed.callbacks.XC_LoadPackage
import xposed.hkrpg.xposed.hooks.hook12306app
import xposed.hkrpg.xposed.hooks.hookHkrpg
import xposed.hkrpg.xposed.hooks.hookPackageManagerForXingQiongTieDaoAnd12306
import xposed.hkrpg.xposed.hooks.hookSystemui


class HookEntry : IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {

//    private lateinit var MODULE_PATH:String
//    private lateinit var mMoudleRes:XModuleResources

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
//        MODULE_PATH = startupParam.modulePath
    }

    override fun handleInitPackageResources(resparam: InitPackageResourcesParam?) {
//        mMoudleRes = XModuleResources.createInstance(MODULE_PATH, resparam.res)
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
            findMethod("xposed.hkrpg.ui.MainActivity") {
                name == "isActive" && returnType == Boolean::class.java
            }.hookBefore {

                it.result = true
            }
        }


        if ("com.miHoYo.hkrpg" == packageName) {
            // 星穹铁道
            hookHkrpg(lpparam)
        }else if("com.MobileTicket" == packageName){
            // 12306
            hook12306app(lpparam)
        }else if("com.android.systemui" == packageName){
            // systemui
            hookSystemui(lpparam)
        }else {
            // PackageManager以及SplashScreen
            hookPackageManagerForXingQiongTieDaoAnd12306(lpparam)
        }
    }
}