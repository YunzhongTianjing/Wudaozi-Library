package com.wudaozi.coder.uniform.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;

/**
 * Created by yunzhongtianjing on 16/3/20.
 */
public abstract class AUniformCoder {
    private final String mCanonicalClassName;
    final String className;
    final String packageName;
    String mHandleFieldName;
    String mSizeIfArrayFieldName;

    public AUniformCoder(String canonicalClassName) {
        this.mCanonicalClassName = canonicalClassName;
        final int divider = mCanonicalClassName.lastIndexOf('.');
        packageName = mCanonicalClassName.substring(0, divider);
        className = mCanonicalClassName.substring(divider + 1);
    }

    public AUniformCoder initialize(String handleFieldName, String sizeIfArrayFieldName) {
        mHandleFieldName = handleFieldName;
        mSizeIfArrayFieldName = sizeIfArrayFieldName;
        return this;
    }

    public void generate(ProcessingEnvironment processEnv) {
        final VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();

        final Template template = engine.getTemplate(getTemplatePath());
        final VelocityContext context = new VelocityContext();
        mapTemplate(context);

        try {
            final JavaFileObject sourceFile = processEnv.getFiler().createSourceFile(mCanonicalClassName);
            final Writer writer = sourceFile.openWriter();
            template.merge(context, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    abstract void mapTemplate(VelocityContext mapper);

    abstract String getTemplatePath();
}
