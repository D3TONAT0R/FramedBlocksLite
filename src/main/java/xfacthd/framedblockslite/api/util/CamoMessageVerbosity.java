package xfacthd.framedblockslite.api.util;

public enum CamoMessageVerbosity
{
    NONE,
    DEFAULT,
    DETAILED;

    public boolean isAtLeast(CamoMessageVerbosity verbosity)
    {
        return ordinal() >= verbosity.ordinal();
    }
}
