package xfacthd.framedblockslite.codegen;

import xfacthd.framedblockslite.codegen.impl.skippreds.SkipPredicateGeneratorImpl;

public final class SkipPredicateGenerator
{
    public static void main(String[] args)
    {
        // The type for which the predicate should be generated (must be defined in SkipPredicateGeneratorData.KNOWN_TYPES) or null to regenerate all
        String sourceType = "FRAMED_TYPE";

        SkipPredicateGeneratorImpl.generateAndExportClasses(sourceType);
    }



    private SkipPredicateGenerator() { }
}
