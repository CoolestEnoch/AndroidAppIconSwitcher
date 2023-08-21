package xposed.hkrpg.xposedHooks

import android.R
import android.content.res.XModuleResources
import android.provider.ContactsContract.Directory.PACKAGE_NAME
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.IXposedHookInitPackageResources
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam
import de.robv.android.xposed.callbacks.XC_LoadPackage


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
            findMethod("xposed.hkrpg.MainActivity") {
                name == "isActive" && returnType == Boolean::class.java
            }.hookBefore {

                it.result = true
            }
        }

        // 星穹铁道
        if ("com.miHoYo.hkrpg" == packageName) {
            hookHkrpg(lpparam)
        }else if("com.MobileTicket" == packageName){
            hook12306app(lpparam)
        } else {
            hookAndroidForXingQiongTieDaoAnd12306(lpparam)
        }
    }
}