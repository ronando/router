package com.crgt.router;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * router annotation processor
 *
 * @author android
 * @date 2019/6/5
 * @mail android@crgecent.com
 */

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouterProcessor extends AbstractProcessor {
    private static final String TAG = RouterProcessor.class.getSimpleName();

    private Filer mFiler;
    private Elements mElementUtil;
    private Types mTypeUtil;

    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
        mElementUtil = processingEnv.getElementUtils();
        mTypeUtil = processingEnv.getTypeUtils();

        // Attempt to get user configuration [moduleName]
        Map<String, String> options = processingEnv.getOptions();
        if (!(options == null || options.isEmpty())) {
            moduleName = options.get("moduleName");
        }

        if (!(moduleName == null || moduleName.length() == 0)) {
            moduleName = moduleName.replaceAll("[^0-9a-zA-Z_]+", "");
//            log("The user has configuration the module name, it was [" + moduleName + "]");
        } else {
            log("no module name, at 'build.gradle', like :\n" +
                    "apt {\n" +
                    "    arguments {\n" +
                    "        moduleName project.getName();\n" +
                    "    }\n" +
                    "}\n");
            throw new RuntimeException("router-compiler >>> No module name, for more information, look at gradle log.");
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportAnnotationTypes = new HashSet<>();
        // only support RouterPath now
        supportAnnotationTypes.add(RouterPath.class.getCanonicalName());
        return supportAnnotationTypes;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        Set<? extends Element> routeElements = roundEnv.getElementsAnnotatedWith(RouterPath.class);
        log("annotation processing...");
        try {
            return parseRoutes(routeElements);
        } catch (Exception ignore) {
            return false;
        }
    }

    private boolean parseRoutes(Set<? extends Element> routeElements) throws IOException {
        if (null == routeElements || routeElements.size() == 0) {
            return false;
        }
        // Get activity type
        TypeElement typeActivity = mElementUtil.getTypeElement(CompilerConsts.ACTIVITY);
        TypeElement typeFragment = mElementUtil.getTypeElement(CompilerConsts.FRAGMENT);
        TypeElement typeFragmentV4 = mElementUtil.getTypeElement(CompilerConsts.FRAGMENT_V4);
        TypeElement typeService = mElementUtil.getTypeElement(CompilerConsts.SERVICE);

        // Generate RouterRoster.java
        ParameterSpec methodParamSpec = ParameterSpec.builder(RosterMap.class, CompilerConsts.METHOD_PARAM_NAME).build();

        MethodSpec.Builder methodGetActivitiesBuilder = MethodSpec.methodBuilder(CompilerConsts.METHOD_GET_ACTIVITIES)
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addParameter(methodParamSpec);

        MethodSpec.Builder methodGetFragmentsBuilder = MethodSpec.methodBuilder(CompilerConsts.METHOD_GET_FRAGMENTS)
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addParameter(methodParamSpec);

        MethodSpec.Builder methodGetServicesBuilder = MethodSpec.methodBuilder(CompilerConsts.METHOD_GET_SERVICES)
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addParameter(methodParamSpec);

        // parse element
        for (Element element : routeElements) {
            TypeMirror tm = element.asType();
            RouterPath routerPath = element.getAnnotation(RouterPath.class);
            for (String path : routerPath.path()) {
//                log("Path = " + path);

                if (mTypeUtil.isSubtype(tm, typeActivity.asType())) {
                    // activity
                    // rosterMap.put("main", "com.crgt.demo.routeprotocal.MainActivity");
                    methodGetActivitiesBuilder.addStatement("$N.put($S, $S)", CompilerConsts.METHOD_PARAM_NAME, path, ClassName.get((TypeElement) element));
                } else if (mTypeUtil.isSubtype(tm, typeFragment.asType()) || mTypeUtil.isSubtype(tm, typeFragmentV4.asType())) {
                    // fragment
                    // rosterMap.put("test", "com.crgt.demo.routeprotocal.TestFragment");
                    methodGetFragmentsBuilder.addStatement("$N.put($S, $S)", CompilerConsts.METHOD_PARAM_NAME, path, ClassName.get((TypeElement) element));
                } else if (mTypeUtil.isSubtype(tm, typeService.asType())) {
                    // service
                    // rosterMap.put("test", "com.crgt.demo.routeprotocal.TestService");
                    methodGetServicesBuilder.addStatement("$N.put($S, $S)", CompilerConsts.METHOD_PARAM_NAME, path, ClassName.get((TypeElement) element));
                }
            }
        }

        // Generate RosterCollectImpl.java
        TypeSpec.Builder typeRosterCollect = TypeSpec.classBuilder(CompilerConsts.ROUTER_ROSTER_NAME + "$$" + moduleName)
                .addJavadoc(CompilerConsts.WARNING_TIPS)
                .addModifiers(PUBLIC)
                .addSuperinterface(RosterCollector.class)
                .addMethod(methodGetActivitiesBuilder.build())
                .addMethod(methodGetFragmentsBuilder.build())
                .addMethod(methodGetServicesBuilder.build());

        JavaFile.builder(CompilerConsts.OUTPUT_DIRECTORY, typeRosterCollect.build()).build().writeTo(mFiler);
        return true;
    }

    private static String className2Const(String className) {
        if (className == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        int length = className.length();
        for (int i = 0; i < length; i++) {
            char letter = className.charAt(i);
            if (Character.isUpperCase(letter) && i != 0) {
                result.append("_");
            }
            result.append(Character.toUpperCase(letter));
        }
        return result.toString();
    }

    private static void log(String msg) {
        System.out.println(TAG + ": " + msg);
    }
}
