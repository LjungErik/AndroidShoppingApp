package com.thetestcompany.presentation.common.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class PermissionsDelegate(private val activity: Activity) {

    fun hasPermission(permission: String): Boolean {
        val permissionResult = ContextCompat.checkSelfPermission(
                activity,
                permission)
        return permissionResult == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(requestCode: Int, permissions: String) {
        ActivityCompat.requestPermissions(
                activity,
                arrayOf(permissions),
                requestCode
        )
    }

    fun isRequestGranted(permissions: Array<String>,
                         grantResults: IntArray,
                         requestedPermission: String): Boolean {

        if(grantResults.isNotEmpty()
                && permissions[0] == requestedPermission
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            return true
        }

        return false
    }
}