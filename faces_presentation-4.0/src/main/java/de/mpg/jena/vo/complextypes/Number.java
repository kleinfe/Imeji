package de.mpg.jena.vo.complextypes;

import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import de.mpg.jena.vo.ComplexType;

@Namespace("http://imeji.mpdl.mpg.de/metadata/")
@RdfType("number")
public class Number extends ComplexType
{
    private Integer number;

    public Number()
    {
        super(ComplexTypes.NUMBER);
    }
    
    public Number(Integer value)
    {
        super(ComplexTypes.NUMBER);
        number = value;
    }

    public int getInteger()
    {
        return number;
    }
}
