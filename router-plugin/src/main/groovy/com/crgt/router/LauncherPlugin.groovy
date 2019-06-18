package com.crgt.router

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class LauncherPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def android = project.extensions.getByType(AppExtension)
        def transformImpl = new RouterTransform(project)

        //register this plugin
        android.registerTransform(transformImpl)
    }
}