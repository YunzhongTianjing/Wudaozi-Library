package com.wudaozi.coder.uniform.generator;

import com.wudaozi.coder.ComponentType;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by yunzhongtianjing on 16/3/20.
 */
public class VectorClassCoder {
    private final String mClassName;
    private final int mComponentSize;
    private final ComponentType mComponentType;
    private final ProcessingEnvironment mProcessEnv;
    private String mHandleFieldName;
    private String mSizeIfArrayFieldName;

    public VectorClassCoder(String canonicalClassName, ComponentType componentType, int componentSize, ProcessingEnvironment processingEnv) {
        this.mClassName = canonicalClassName;
        this.mComponentType = componentType;
        this.mComponentSize = componentSize;
        this.mProcessEnv = processingEnv;
    }

    public VectorClassCoder initialize(String handleFieldName, String sizeIfArrayFieldName) {
        mHandleFieldName = handleFieldName;
        mSizeIfArrayFieldName = sizeIfArrayFieldName;
        return this;
    }

    public void generate() {
        final VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();

        final Template template = engine.getTemplate("code-template/Uniform.model");
        final VelocityContext context = new VelocityContext();

        final int divider = mClassName.lastIndexOf('.');
        context.put("packageName", mClassName.substring(0, divider));
        context.put("className", mClassName.substring(divider + 1));
        context.put("singleValue", true);
        context.put("componentType", mComponentType.name());
        context.put("componentSize", mComponentSize);
        context.put("handle", mHandleFieldName);
        context.put("sizeIfArray", mSizeIfArrayFieldName);

//        final StringWriter writer = new StringWriter();
//        template.merge(context, writer);

//        mProcessEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "FYY:" + writer.toString());
        try {
            final JavaFileObject sourceFile = mProcessEnv.getFiler().createSourceFile(mClassName);
            final Writer writer = sourceFile.openWriter();
            template.merge(context, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mProcessEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,"FYY:"+ );
    }
}
