package br.com.stickindex.sample.presentation.presenter

import android.Manifest
import android.app.SearchManager
import android.arch.lifecycle.Lifecycle
import android.content.Intent
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import br.com.edsilfer.toolkit.core.components.BasePresenter
import br.com.stickindex.sample.domain.Router
import br.com.stickindex.sample.presentation.view.MainView
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener

/**
 * Created by edgarsf on 18/03/2018.
 */
class MainPresenter(
        lifecycle: Lifecycle,
        private val view: MainView,
        private val router: Router
) : BasePresenter(lifecycle) {
    override fun onStart() {
        super.onStart()
        requestReadContactsPermission()
    }

    private fun requestReadContactsPermission() {
        Dexter.withActivity(view)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        view.render()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        view.finish()
                    }
                }).check()
    }

    fun onMenuSearchClick(): Boolean {
        router.launchSearchView()
        return true
    }

    fun onMenuSettingsClick(): Boolean = true

    fun onSearchResult(intent: Intent) {
        val query = intent.getStringExtra(SearchManager.QUERY)
        makeText(view, "User searched for: $query", LENGTH_SHORT).show()
    }
}