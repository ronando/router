package com.crgt

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelperPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        String versionName = ""

        project.buildscript.configurations.classpath.resolvedConfiguration.firstLevelModuleDependencies.forEach {
            println "helper_parse_version = " + it.name
            if (it.name.startsWith("com.crgt.android:router-plugin")) {
                versionName = it.moduleVersion
                return
            }
        }

        project.subprojects { Project p ->
            p.afterEvaluate {
                if (it.plugins.hasPlugin("com.android.application")) {
                    it.plugins.apply('com.crgt.router')
                }

                if (it.plugins.hasPlugin("com.android.application") || it.plugins.hasPlugin("com.android.library")) {
                    it.dependencies.add('implementation', "com.crgt.android:base-router-api:1.3.5")
                    it.dependencies.add('kapt', "com.crgt.android:base-router-compiler:1.3.0")

                    it.android.defaultConfig.javaCompileOptions.annotationProcessorOptions.argument('moduleName', it.name)
                }
            }
        }
    }
}