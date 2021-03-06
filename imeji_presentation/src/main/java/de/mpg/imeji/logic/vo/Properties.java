/**
 * License: src/main/resources/license/escidoc.license
 */
package de.mpg.imeji.logic.vo;

import java.net.URI;
import java.util.Calendar;

import de.mpg.j2j.annotations.j2jLiteral;
import de.mpg.j2j.annotations.j2jResource;

@j2jResource("http://imeji.org/terms/properties")
public class Properties
{
    @j2jResource("http://purl.org/dc/terms/creator")
    private URI createdBy;
    @j2jResource("http://imeji.org/terms/modifiedBy")
    private URI modifiedBy;
    @j2jLiteral("http://purl.org/dc/terms/created")
    private Calendar created;
    @j2jLiteral("http://purl.org/dc/terms/modified")
    private Calendar modified;
    @j2jLiteral("http://purl.org/dc/terms/issued")
    private Calendar versionDate;
    @j2jResource("http://imeji.org/terms/status")
    private URI status = Status.PENDING.getUri();
    @j2jLiteral("http://imeji.org/terms/versionNumber")
    private int version = 0;
    @j2jLiteral("http://imeji.org/terms/discardComment")
    private String discardComment;

    public enum Status
    {
        PENDING("http://imeji.org/terms/status#PENDING"), RELEASED("http://imeji.org/terms/status#RELEASED"), WITHDRAWN(
                "http://imeji.org/terms/status#WITHDRAWN");
        private URI uri;

        private Status(String uri)
        {
            this.uri = URI.create(uri);
        }

        public URI getUri()
        {
            return uri;
        }
    }

    public Properties()
    {
        // TODO Auto-generated constructor stub
    }

    public void setCreatedBy(URI createdBy)
    {
        this.createdBy = createdBy;
    }

    public URI getCreatedBy()
    {
        return createdBy;
    }

    public void setModifiedBy(URI modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }

    public URI getModifiedBy()
    {
        return modifiedBy;
    }

    public void setStatus(Status status)
    {
        this.status = status.getUri();
    }

    public Status getStatus()
    {
        return Status.valueOf(status.getFragment());
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public int getVersion()
    {
        return version;
    }

    public String getDiscardComment()
    {
        return discardComment;
    }

    public void setDiscardComment(String discardComment)
    {
        this.discardComment = discardComment;
    }

    public Calendar getCreated()
    {
        return created;
    }

    public void setCreated(Calendar created)
    {
        this.created = created;
    }

    public Calendar getModified()
    {
        return modified;
    }

    public void setModified(Calendar modified)
    {
        this.modified = modified;
    }

    public Calendar getVersionDate()
    {
        return versionDate;
    }

    public void setVersionDate(Calendar versionDate)
    {
        this.versionDate = versionDate;
    }
}
