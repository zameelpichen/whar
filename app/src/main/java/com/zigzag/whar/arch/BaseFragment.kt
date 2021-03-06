package com.zigzag.whar.arch

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import com.google.firebase.auth.FirebaseUser
import com.zigzag.whar.old.BaseContract
import com.zigzag.whar.old.BaseViewModel

import dagger.android.support.DaggerFragment

/**
 * Created by salah on 27/12/17.
 */

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : DaggerFragment(), BaseContract.View {

    private val lifecycleRegistry = LifecycleRegistry(this)
    protected lateinit var presenter: P
    private var isPresenterCreated = false

    @CallSuper
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(BaseViewModel<V, P>()::class.java)
        if (viewModel.presenter == null) {
            viewModel.presenter = getPresenterImpl()
            isPresenterCreated = true
        }

        presenter = viewModel.presenter as P
        presenter.attachLifecycle(lifecycle)
        presenter.attachView(this as V)
        if (isPresenterCreated)
            presenter.onPresenterCreated()
    }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachLifecycle(lifecycle)
        presenter.detachView()
    }

    protected abstract fun getPresenterImpl(): P

    override fun authRedirect(user : FirebaseUser?){
        // Operation managed in activity
    }
}
