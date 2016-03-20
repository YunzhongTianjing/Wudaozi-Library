package com.wudaozi.coder.uniform.generator;

import org.apache.velocity.VelocityContext;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * Created by yunzhongtianjing on 16/3/20.
 */
public class MatrixClassCoder extends AUniformCoder {


    private final int mDimensionality;

    public MatrixClassCoder(String canonicalClassName, int dimensionality) {
        super(canonicalClassName);
        this.mDimensionality = dimensionality;
    }

    @Override
    void mapTemplate(VelocityContext mapper) {
        mapper.put("packageName", packageName);
        mapper.put("className", className);
        mapper.put("handle", mHandleFieldName);
        mapper.put("sizeIfArray", mSizeIfArrayFieldName);
        mapper.put("dimensionality", mDimensionality);
    }

    @Override
    String getTemplatePath() {
        return "code-template/UniformMatrix.model";
    }
}
