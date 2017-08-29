package com.smrecki.common.dagger.components;


import com.smrecki.common.dagger.modules.FragmentModule;
import com.smrecki.common.dagger.scopes.FragmentScope;

import dagger.Component;

/**
 * Created by tomislav on 17/02/2017.
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

}
