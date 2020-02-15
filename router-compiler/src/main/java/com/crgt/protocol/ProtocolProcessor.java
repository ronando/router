package com.crgt.protocol;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import com.crgt.protocol.annotation.ProtocolPath;
import com.crgt.protocol.model.ProtocolMap;
import com.crgt.protocol.utils.Constants;
import com.crgt.protocol.utils.Logger;


/**
 * 协议注解处理器
 *
 * @author jesse.lu
 * @Date 2019/6/19
 * @mail： jesse.lu@foxmail.com
 */
@AutoService(Processor.class)
public class ProtocolProcessor extends AbstractProcessor {

    private String moduleName;

    private Filer mFiler;
    private Logger logger;
    private TypeMirror iCollector;
    private TypeMirror absProtocolProcessor;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        logger = new Logger(processingEnvironment.getMessager());
        Elements elementUtil = processingEnv.getElementUtils();
        iCollector = elementUtil.getTypeElement(Constants.PROTOCOL_COLLECT_INTERFACE).asType();
        absProtocolProcessor = elementUtil.getTypeElement(Constants.PROTOCOL_PROCESSOR_INTERFACE).asType();

        // Attempt to get user configuration [moduleName]
        Map<String, String> options = processingEnv.getOptions();
        if (!(options == null || options.isEmpty())) {
            moduleName = options.get("moduleName");
        }

        if (!(moduleName == null || moduleName.length() == 0)) {
            moduleName = moduleName.replaceAll("[^0-9a-zA-Z_]+", "");

            logger.info("The user has configuration the module name, it was [" + moduleName + "]");
        } else {
            logger.error("These no module name, at 'build.gradle', like :\n" +
                    "apt {\n" +
                    "    arguments {\n" +
                    "        moduleName project.getName();\n" +
                    "    }\n" +
                    "}\n");
            throw new RuntimeException("ARouter::Compiler >>> No module name, for more information, look at gradle log.");
        }
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>(1);
        types.add(ProtocolPath.class.getName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> protocols = roundEnvironment.getElementsAnnotatedWith(ProtocolPath.class);
        try {
            parseProtocols(protocols);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void parseProtocols(Set<? extends Element> protocols) throws IOException {
        if (protocols == null || protocols.isEmpty()) {
            return;
        }

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.METHOD_COLLECT)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ProtocolMap.class, Constants.COLLECT_METHOD_PARAMETER_MAP);

        for (Element element : protocols) {
            logger.info(element.getSimpleName());
            if (verify(element)) {
                ProtocolPath protocol = element.getAnnotation(ProtocolPath.class);
                String[] paths = protocol.path();
                if (paths == null || paths.length == 0) {
                    continue;
                }
                for (String path : paths) {
                    methodBuilder.addStatement("$N.put($S, $S)", Constants.COLLECT_METHOD_PARAMETER_MAP, path, ClassName.get((TypeElement) element));
                }
            } else {
                logger.error("annotation args wrong or ProtocolCollector interface not implemented!");
            }
        }
        TypeSpec protocolCallback = TypeSpec.classBuilder(Constants.PROTOCOL_COLLECT_IMPL + Constants.SEPARATOR + moduleName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(ClassName.get(iCollector))
                .addMethod(methodBuilder.build())
                .build();

        JavaFile.builder(Constants.OUTPUT_DIRECTORY, protocolCallback).build().writeTo(mFiler);
    }


    private boolean verify(Element element) {
        ProtocolPath protocolPath = element.getAnnotation(ProtocolPath.class);
        return null != protocolPath && ((TypeElement) element).getSuperclass().equals(absProtocolProcessor);
    }

}
