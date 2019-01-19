package com.thetestcompany.presentation.di.listitemmanagement

import com.thetestcompany.presentation.listitemmanagement.ListItemManagementListFragment
import dagger.Subcomponent

@Subcomponent(modules = [ListItemManagementModule::class])
interface ListItemManagementSubComponent {
    fun inject(listItemManagementListFragment: ListItemManagementListFragment)
}