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
public class VectorClassCoder extends AUniformCoder {

    private final ComponentType mComponentType;
    private final int mComponentSize;
    private final boolean mSingleValue;

    public VectorClassCoder(String canonicalClassName, ComponentType componentType, int componentSize, boolean isArray) {
        super(canonicalClassName);
        this.mComponentType = componentType;
        this.mComponentSize = componentSize;
        this.mSingleValue = !isArray;
    }

    @Override
    void mapTemplate(VelocityContext mapper) {
        mapper.put("packageName", packageName);
        mapper.put("className", className);
        mapper.put("singleValue", mSingleValue);
        mapper.put("componentType", mComponentType.name());
        mapper.put("componentSize", mComponentSize);
        mapper.put("handle", mHandleFieldName);
        mapper.put("sizeIfArray", mSizeIfArrayFieldName);
    }

    @Override
    String getTemplatePath() {
        return "code-template/UniformVector.model";
    }
}
