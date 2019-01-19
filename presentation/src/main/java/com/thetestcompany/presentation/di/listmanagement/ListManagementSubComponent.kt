package com.thetestcompany.presentation.di.listmanagement

import com.thetestcompany.presentation.listmanagement.ListManagementActivity
import dagger.Subcomponent

@Subcomponent(modules = [ListManagementModule::class])
interface ListManagementSubComponent {
    fun inject(listManagementActivity: ListManagementActivity)
}