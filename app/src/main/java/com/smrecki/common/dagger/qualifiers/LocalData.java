package com.smrecki.common.dagger.qualifiers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by tomislav on 22/02/2017.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalData {
}
