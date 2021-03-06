package com.zigzag.whar.ui.profileEdit

import com.zigzag.whar.di.ActivityScoped
import dagger.Binds
import dagger.Module

/**
 * Created by salah on 13/1/18.
 */

@Module
abstract class ProfileEditModule {

    @ActivityScoped
    @Binds
    internal abstract fun profileEditActivityPresenter(presenter: ProfileEditPresenter): ProfileEditContract.Presenter

}