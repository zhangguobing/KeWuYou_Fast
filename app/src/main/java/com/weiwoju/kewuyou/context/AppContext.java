package com.weiwoju.kewuyou.context;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.weiwoju.kewuyou.controller.MainController;
import com.weiwoju.kewuyou.module.ApplicationModule;
import com.weiwoju.kewuyou.module.inter.Injector;
import com.weiwoju.kewuyou.module.library.ContextProvider;
import com.weiwoju.kewuyou.module.library.InjectorModule;
import com.weiwoju.kewuyou.network.GsonHelper;
import com.weiwoju.kewuyou.util.PreferenceUtil;
import com.weiwoju.kewuyou.util.ToastUtil;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by zhangguobing on 2017/4/21.
 */
public class AppContext extends Application implements Injector{
    private static AppContext mInstance;

    private ObjectGraph mObjectGraph;
    private RefWatcher mRefWatcher;

    @Inject
    MainController mMainController;

    public static AppContext getContext() {
        return mInstance;
    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    public MainController getMainController() {
        return mMainController;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // 吐司初始化
        ToastUtil.init(this);

        // 本地存储工具类初始化
        PreferenceUtil.init(this, GsonHelper.builderGson());

        // 日志打印器初始化
        Logger.init(getPackageName()).setLogLevel(AppConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);

        // bugly初始化
        CrashReport.initCrashReport(this, "1a859a4b54", false);

        // LeakCanary
        mRefWatcher = LeakCanary.install(this);

        // 依赖注解初始化
        mObjectGraph = ObjectGraph.create(
                new ApplicationModule(),
                new ContextProvider(this),
                new InjectorModule(this)
        );
        mObjectGraph.inject(this);
    }

    @Override
    public void inject(Object object) {
        mObjectGraph.inject(object);
    }

}
