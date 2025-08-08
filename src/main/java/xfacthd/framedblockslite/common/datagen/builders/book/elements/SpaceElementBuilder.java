package xfacthd.framedblockslite.common.datagen.builders.book.elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xfacthd.framedblockslite.common.datagen.builders.book.elements.attributes.ElementCategory;

public final class SpaceElementBuilder extends ExtendedElementBuilder<SpaceElementBuilder>
{
    private final String height;

    public SpaceElementBuilder(String height)
    {
        super(ElementCategory.PAGE, "space");
        this.height = height;
    }

    @Override
    protected void printInternal(Document doc, Element elementElem)
    {
        elementElem.setAttribute("height", height);
    }
}
