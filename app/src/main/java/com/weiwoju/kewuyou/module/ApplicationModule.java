package com.weiwoju.kewuyou.module;

import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.module.library.InjectorModule;
import com.weiwoju.kewuyou.module.library.NetworkProvider;
import com.weiwoju.kewuyou.module.library.PersistenceProvider;
import com.weiwoju.kewuyou.module.library.UtilProvider;

import dagger.Module;

/**
 * Created by zhangguobing on 2017/4/21.
 */
@Module(
        injects = {
                AppContext.class,
        },
        includes = {
                InjectorModule.class,
                NetworkProvider.class,
                UtilProvider.class,
                PersistenceProvider.class
        }

)
public class ApplicationModule {
}
