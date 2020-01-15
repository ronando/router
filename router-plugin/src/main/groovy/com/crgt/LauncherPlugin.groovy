package com.crgt

import com.android.build.gradle.AppExtension
import com.crgt.protocol.ProtocolTransform
import com.crgt.router.RouterTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class LauncherPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def android = project.extensions.getByType(AppExtension)

        //register this plugin
        android.registerTransform(new RouterTransform(project))
        android.registerTransform(new ProtocolTransform())
    }
}