/**
 * License: src/main/resources/license/escidoc.license
 */
package de.mpg.imeji.logic.vo;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import de.mpg.imeji.logic.search.FulltextIndex;
import de.mpg.j2j.annotations.j2jId;
import de.mpg.j2j.annotations.j2jLazyList;
import de.mpg.j2j.annotations.j2jLiteral;
import de.mpg.j2j.annotations.j2jResource;

@j2jResource("http://imeji.org/terms/container")
@j2jId(getMethod = "getId", setMethod = "setId")
public class Container extends Properties implements FulltextIndex
{
    private URI id;
    // @j2jResource("http://imeji.org/terms/properties")
    // private Properties properties = new Properties();
    @j2jLazyList("http://imeji.org/terms/item")
    private Collection<URI> images = new ArrayList<URI>();
    @j2jResource("http://imeji.org/terms/container/metadata")
    private ContainerMetadata metadata = new ContainerMetadata();
    @j2jLiteral("http://imeji.org/terms/fulltext")
    private String fulltext;

    public void setId(URI id)
    {
        this.id = id;
    }

    public URI getId()
    {
        return id;
    }

    public void setMetadata(ContainerMetadata metadata)
    {
        this.metadata = metadata;
    }

    public ContainerMetadata getMetadata()
    {
        return metadata;
    }

    // public void setProperties(Properties properties)
    // {
    // this.properties = properties;
    // }
    //
    // public Properties getProperties()
    // {
    // return properties;
    // }
    public void setImages(Collection<URI> images)
    {
        this.images = images;
    }

    public Collection<URI> getImages()
    {
        return images;
    }

    public void setFulltextIndex(String fulltext)
    {
        this.fulltext = fulltext;
    }

    public String getFulltextIndex()
    {
        return fulltext;
    }

    public void indexFulltext()
    {
        fulltext = metadata.getTitle();
        if (!"".equals(metadata.getDescription()))
        {
            fulltext += " " + metadata.getDescription();
        }
        for (Person p : metadata.getPersons())
        {
            fulltext += " " + p.AsFullText();
        }
        fulltext.trim();
    }
}
