package com.weiwoju.kewuyou.module.library;

import com.weiwoju.kewuyou.repository.SettingManager;
import com.weiwoju.kewuyou.repository.UserManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        library = true,
        includes = {
                ContextProvider.class,
                UtilProvider.class
        }
)
public class PersistenceProvider {

    @Provides
    @Singleton
    public UserManager provideUserManager() {
        return new UserManager();
    }

    @Provides
    @Singleton
    public SettingManager provideSettingManager() {
        return new SettingManager();
    }
}
