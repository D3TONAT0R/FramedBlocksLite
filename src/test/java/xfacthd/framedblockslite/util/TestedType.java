package xfacthd.framedblockslite.util;

import xfacthd.framedblockslite.common.data.BlockType;

import java.lang.annotation.*;

@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestedType
{
    BlockType type();
}
