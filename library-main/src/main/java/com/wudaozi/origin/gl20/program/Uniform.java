package com.wudaozi.origin.gl20.program;

import com.wudaozi.coder.ComponentType;
import com.wudaozi.coder.Handle;
import com.wudaozi.coder.uniform.SizeIfArray;
import com.wudaozi.coder.uniform.UniformSubclass;
import com.wudaozi.coder.uniform.UniformSuperclass;
import com.wudaozi.exception.WudaoziException;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import static android.opengl.GLES20.*;

/**
 * Created by yunhun.fyy on 2016/3/18.
 */
@UniformSuperclass
public abstract class Uniform {
    @UniformSubclass.Vector(componentSize = 1, componentType = ComponentType.FLOAT)
    private static final String SUBCLASS_FLOAT = "com.wudaozi.origin.gl20.program.UniformFloat";
    @UniformSubclass.Vector(componentSize = 2, componentType = ComponentType.FLOAT)
    private static final String SUBCLASS_FLOAT_VECTOR2 = "com.wudaozi.origin.gl20.program.UniformFloatVector2";
    @UniformSubclass.Vector(componentSize = 3, componentType = ComponentType.FLOAT)
    private static final String SUBCLASS_FLOAT_VECTOR3 = "com.wudaozi.origin.gl20.program.UniformFloatVector3";
    @UniformSubclass.Vector(componentSize = 4, componentType = ComponentType.FLOAT)
    private static final String SUBCLASS_FLOAT_VECTOR4 = "com.wudaozi.origin.gl20.program.UniformFloatVector4";

    @UniformSubclass.Vector(componentSize = 1, componentType = ComponentType.INTEGER)
    private static final String SUBCLASS_INTEGER = "com.wudaozi.origin.gl20.program.UniformInteger";
    @UniformSubclass.Vector(componentSize = 2, componentType = ComponentType.INTEGER)
    private static final String SUBCLASS_INTEGER_VECTOR2 = "com.wudaozi.origin.gl20.program.UniformIntegerVector2";
    @UniformSubclass.Vector(componentSize = 3, componentType = ComponentType.INTEGER)
    private static final String SUBCLASS_INTEGER_VECTOR3 = "com.wudaozi.origin.gl20.program.UniformIntegerVector3";
    @UniformSubclass.Vector(componentSize = 4, componentType = ComponentType.INTEGER)
    private static final String SUBCLASS_INTEGER_VECTOR4 = "com.wudaozi.origin.gl20.program.UniformIntegerVector4";

    @UniformSubclass.Vector(componentSize = 1, componentType = ComponentType.BOOL)
    private static final String SUBCLASS_BOOL = "com.wudaozi.origin.gl20.program.UniformBool";
    @UniformSubclass.Vector(componentSize = 2, componentType = ComponentType.BOOL)
    private static final String SUBCLASS_BOOL_VECTOR2 = "com.wudaozi.origin.gl20.program.UniformBoolVector2";
    @UniformSubclass.Vector(componentSize = 3, componentType = ComponentType.BOOL)
    private static final String SUBCLASS_BOOL_VECTOR3 = "com.wudaozi.origin.gl20.program.UniformBoolVector3";
    @UniformSubclass.Vector(componentSize = 4, componentType = ComponentType.BOOL)
    private static final String SUBCLASS_BOOL_VECTOR4 = "com.wudaozi.origin.gl20.program.UniformBoolVector4";

    @UniformSubclass.Matrix(dimensionality = 2)
    private static final String SUBCLASS_MATRIX2 = "com.wudaozi.origin.gl20.program.UniformMatrix2";
    @UniformSubclass.Matrix(dimensionality = 3)
    private static final String SUBCLASS_MATRIX3 = "com.wudaozi.origin.gl20.program.UniformMatrix3";
    @UniformSubclass.Matrix(dimensionality = 4)
    private static final String SUBCLASS_MATRIX4 = "com.wudaozi.origin.gl20.program.UniformMatrix4";

    private static final String SUBCLASS_SAMPLER2D = "com.wudaozi.origin.gl20.program.UniformSampler2D";
    private static final String SUBCLASS_SAMPLER3D = "com.wudaozi.origin.gl20.program.UniformSampler3D";

    private static final Map<Integer, String> sTypeMap;

    static {
        sTypeMap = new HashMap<>();
        sTypeMap.put(GL_FLOAT, SUBCLASS_FLOAT);
        sTypeMap.put(GL_FLOAT_VEC2, SUBCLASS_FLOAT_VECTOR2);
        sTypeMap.put(GL_FLOAT_VEC3, SUBCLASS_FLOAT_VECTOR3);
        sTypeMap.put(GL_FLOAT_VEC4, SUBCLASS_FLOAT_VECTOR4);

        sTypeMap.put(GL_INT, SUBCLASS_INTEGER);
        sTypeMap.put(GL_INT_VEC2, SUBCLASS_INTEGER_VECTOR2);
        sTypeMap.put(GL_INT_VEC3, SUBCLASS_INTEGER_VECTOR3);
        sTypeMap.put(GL_INT_VEC4, SUBCLASS_INTEGER_VECTOR4);

        sTypeMap.put(GL_BOOL, SUBCLASS_BOOL);
        sTypeMap.put(GL_BOOL_VEC2, SUBCLASS_BOOL_VECTOR2);
        sTypeMap.put(GL_BOOL_VEC3, SUBCLASS_BOOL_VECTOR3);
        sTypeMap.put(GL_BOOL_VEC4, SUBCLASS_BOOL_VECTOR4);

        sTypeMap.put(GL_FLOAT_MAT2, SUBCLASS_MATRIX2);
        sTypeMap.put(GL_FLOAT_MAT3, SUBCLASS_MATRIX3);
        sTypeMap.put(GL_FLOAT_MAT4, SUBCLASS_MATRIX4);

        sTypeMap.put(GL_SAMPLER_2D, SUBCLASS_SAMPLER2D);
        sTypeMap.put(GL_SAMPLER_CUBE, SUBCLASS_SAMPLER3D);
    }

    public final String name;

    @Handle
    protected final int handle;

    /**
     * If the uniform variable being queried is an array, this variable
     * will be written with the maximum array element used in the
     * program (plus 1). If the uniform variable being queried is not
     * an array, this value will be 1
     */
    @SizeIfArray
    protected final int mSizeIfArray;

    protected Uniform(String name, int handle, int sizeIfArray) {
        this.name = name;
        this.handle = handle;
        this.mSizeIfArray = sizeIfArray;
    }

    static Uniform create(String name, int handle, int sizeIfArray, int type) {
        try {
            final Class<? extends Uniform> subUniformClass = (Class<? extends Uniform>) Class.forName(sTypeMap.get(type));
            final Constructor<? extends Uniform> constructor = subUniformClass.getDeclaredConstructor(String.class,
                    int.class, int.class);
            constructor.setAccessible(true);
            return constructor.newInstance(name, handle, sizeIfArray);
        } catch (Exception e) {
            throw new WudaoziException(e);
        }
    }
}
