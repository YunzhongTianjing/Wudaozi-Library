package com.wudaozi.origin.gl20.program;

import com.wudaozi.exception.WudaoziException;
import com.wudaozi.origin.BufferElementType;
import com.wudaozi.origin.gl20.buffer.BufferObject;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES20.*;

/**
 * Created by yunzhongtianjing on 16/3/5.
 */
public class Attribute {
    private enum Type {
        FLOAT(GL_FLOAT, 1),
        FLOAT_VEC2(GL_FLOAT_VEC2, 2),
        FLOAT_VEC3(GL_FLOAT_VEC3, 3),
        FLOAT_VEC4(GL_FLOAT_VEC4, 4),
        FLOAT_MAT2(GL_FLOAT_MAT2, -1),
        FLOAT_MAT3(GL_FLOAT_MAT3, -1),
        FLOAT_MAT4(GL_FLOAT_MAT4, -1);

        private final int mGLValue;
        private final int mComponentNum;

        Type(int glValue, int componentNum) {
            this.mGLValue = glValue;
            this.mComponentNum = componentNum;
        }

        private static Type getByGLType(int glType) {
            switch (glType) {
                case GL_FLOAT:
                    return FLOAT;
                case GL_FLOAT_VEC2:
                    return FLOAT_VEC2;
                case GL_FLOAT_VEC3:
                    return FLOAT_VEC3;
                case GL_FLOAT_VEC4:
                    return FLOAT_VEC4;
                case GL_FLOAT_MAT2:
                    return FLOAT_MAT2;
                case GL_FLOAT_MAT3:
                    return FLOAT_MAT3;
                case GL_FLOAT_MAT4:
                    return FLOAT_MAT4;
                default:
                    throw new WudaoziException("Unknown attribute type{%s}", glType);
            }
        }
    }

    public final String name;
    public final int handle;
    private final int mSizeIfArray;
    public final Type type;

    private Attribute(String name, int handle, int sizeIfArray, int type) {
        this.name = name;
        this.handle = handle;
        this.mSizeIfArray = sizeIfArray;
        this.type = Type.getByGLType(type);
    }


    static Attribute create(String name, int handle, int sizeIfArray, int type) {
        return new Attribute(name, handle, sizeIfArray, type);
    }

    public static class DataSource {
        private static class AttributeWrapper {
            private final int mOffsetInBytes;
            private final int mOffsetInElements;
            private final Attribute mCore;
            private final int mSizeInBytes;

            private AttributeWrapper(Attribute attribute, int offsetInBytes, int offsetInElements, int sizeInBytes) {
                this.mCore = attribute;
                this.mOffsetInBytes = offsetInBytes;
                this.mOffsetInElements = offsetInElements;
                this.mSizeInBytes = sizeInBytes;
            }
        }

        private final Buffer mBuffer;
        private final BufferObject mBufferObject;
        private final List<AttributeWrapper> mAttributesWrappers;
        private final boolean mNormalized;
        private final int mStrideInBytes;
        private final BufferElementType mType;

        private DataSource(Buffer buffer, BufferObject bufferObject, BufferElementType elementType,
                           List<Attribute> attributes, boolean normalized) {
            this.mBuffer = buffer;
            this.mBufferObject = bufferObject;
            this.mNormalized = normalized;
            this.mType = elementType;
            this.mAttributesWrappers = wrapAttributes(attributes, mType);
            this.mStrideInBytes = calculateStride(mAttributesWrappers);
        }

        private List<AttributeWrapper> wrapAttributes(List<Attribute> attributes, BufferElementType elementType) {
            final List<AttributeWrapper> result = new ArrayList<>(attributes.size());
            int offsetInBytes = 0;
            int offsetInElements = 0;

            for (Attribute attribute : attributes) {
                final int sizeInBytes = attribute.type.mComponentNum * elementType.byteSize;
                result.add(new AttributeWrapper(attribute, offsetInBytes, offsetInElements, sizeInBytes));
                offsetInBytes += sizeInBytes;
                offsetInElements += attribute.type.mComponentNum;
            }
            return result;
        }

        private int calculateStride(List<AttributeWrapper> attributesWrappers) {
            int stride = 0;
            for (AttributeWrapper wrapper : attributesWrappers)
                stride += wrapper.mSizeInBytes;
            return stride;
        }

        public void use() {
            for (AttributeWrapper wrapper : mAttributesWrappers) {
                final Attribute attribute = wrapper.mCore;
                glEnableVertexAttribArray(attribute.handle);
                if (null != mBuffer) {
                    glVertexAttribPointer(
                            attribute.handle, attribute.type.mComponentNum,//Destination(attribute) params
                            mType.glType, mNormalized, mStrideInBytes, mBuffer.position(wrapper.mOffsetInElements)//Source(buffer) params
                    );
                } else {
                    mBufferObject.bind();
                    glVertexAttribPointer(attribute.handle, attribute.type.mComponentNum,//Destination(attribute) params
                            mType.glType, mNormalized, mStrideInBytes, wrapper.mOffsetInBytes//Source(buffer) params
                    );
                }
            }
        }


        public static class Builder {
            private boolean mNormalize;
            private final Buffer mBuffer;
            private final BufferObject mBufferObject;
            private final BufferElementType mElementType;
            private final List<Attribute> attributes = new ArrayList<>();

            public Builder(Buffer source) {
                this.mBuffer = source;
                this.mBufferObject = null;
                this.mElementType = BufferElementType.getElementTypeByBuffer(source);
            }

            public Builder(BufferObject source) {
                this.mBuffer = null;
                this.mBufferObject = source;
                this.mElementType = source.elementType;
            }

            public Builder normalize(boolean normalize) {
                this.mNormalize = normalize;
                return this;
            }

            public DataSource build() {
                return new DataSource(mBuffer, mBufferObject, mElementType, attributes, mNormalize);
            }

            public Builder assign(Attribute attribute) {
                attributes.add(attribute);
                return this;
            }
        }

    }
}
