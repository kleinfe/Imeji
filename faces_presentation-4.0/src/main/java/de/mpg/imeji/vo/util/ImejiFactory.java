package de.mpg.imeji.vo.util;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import thewebsemantic.LocalizedString;
import de.mpg.imeji.collection.ViewCollectionBean;
import de.mpg.imeji.image.ImageBean;
import de.mpg.jena.util.ComplexTypeHelper;
import de.mpg.jena.util.ObjectHelper;
import de.mpg.jena.vo.CollectionImeji;
import de.mpg.jena.vo.ComplexType;
import de.mpg.jena.vo.ContainerMetadata;
import de.mpg.jena.vo.Image;
import de.mpg.jena.vo.MetadataProfile;
import de.mpg.jena.vo.Organization;
import de.mpg.jena.vo.Person;
import de.mpg.jena.vo.Properties;
import de.mpg.jena.vo.Statement;
import de.mpg.jena.vo.ComplexType.ComplexTypes;
import de.mpg.jena.vo.complextypes.ConePerson;
import de.mpg.jena.vo.complextypes.Date;
import de.mpg.jena.vo.complextypes.Geolocation;
import de.mpg.jena.vo.complextypes.License;
import de.mpg.jena.vo.complextypes.Number;
import de.mpg.jena.vo.complextypes.Text;

public class ImejiFactory
{
    public static CollectionImeji newCollection()
    {
        CollectionImeji coll = new CollectionImeji();
        coll.setMetadata(newContainerMetadata());
        coll.setProperties(newProperties());
        coll.setProfile(new MetadataProfile());
        return coll;
    }

    public static ContainerMetadata newContainerMetadata()
    {
        ContainerMetadata cm = new ContainerMetadata();
        return cm;
    }

    public static Properties newProperties()
    {
        Properties props = new Properties();
        return props;
    }

    public static Statement newStatement()
    {
        Statement s = new Statement();
        s.getLabels().add(new LocalizedString("", ""));
        return s;
    }

    public static ComplexType newComplexType(URI uri)
    {
        switch (ComplexTypeHelper.getComplexTypesEnum(uri))
        {
            case CONE_AUTHOR:
                return new ConePerson();
            case DATE:
                return new Date();
            case GEOLOCATION:
                return new Geolocation();
            case LICENCE:
                new License();
            case NUMBER:
                return new Number(0);
            default:
                return new Text("");
        }
    }

    //    
    // public static List<CollectionVO> newCollectionList(Collection<CollectionImeji> ci)
    // {
    // List<CollectionVO> collList = new ArrayList<CollectionImeji>();
    // for(CollectionImeji coll : ci)
    // {
    // collList.add(newCollection(coll));
    // }
    //    	
    // return collList;
    // }
    //    
    // public static List<AlbumVO> newAlbumList(Collection<Album> ci)
    // {
    // List<AlbumVO> collList = new ArrayList<AlbumVO>();
    // for(Album a : ci)
    // {
    // collList.add(newAlbum(a));
    // }
    //        
    // return collList;
    // }
    //    
    // public static ImageVO newImage(Image im)
    // {
    // ImageVO vo = new ImageVO();
    // vo.setCollection(im.getCollection());
    // vo.setFullImageUrl(im.getFullImageUrl());
    // vo.setId(im.getId());
    // vo.setMetadata(im.getMetadata());
    // vo.setProperties(im.getProperties());
    // vo.setThumbnailImageUrl(im.getThumbnailImageUrl());
    // vo.setVisibility(im.getVisibility());
    // vo.setWebImageUrl(im.getWebImageUrl());
    // return vo;
    // }
    // public static MdProfileVO newMdProfile(String str)
    // {
    // MdProfileVO vo = new MdProfileVO();
    // DescriptionSetTemplateDocument dstDoc;
    // try
    // {
    // dstDoc = DescriptionSetTemplateDocument.Factory.parse(str);
    // }
    // catch (XmlException e)
    // {
    // throw new RuntimeException("Error parsing Md-Profile: ", e);
    // }
    //        
    // for (DescriptionTemplate dt : dstDoc.getDescriptionSetTemplate().getDescriptionTemplateArray())
    // {
    // for (StatementTemplate st : dt.getStatementTemplateArray())
    // {
    // vo.getStatements().add(newStatementVO(st));
    // }
    // }
    // return vo;
    // }
    //    
    // public static StatementVO newStatementVO(StatementTemplate st)
    // {
    // StatementVO vo = new StatementVO();
    // vo.setName(st.getID());
    // if (st.getPropertyArray().length > 0)
    // {
    // vo.setElementNamespace(st.getPropertyArray(0));
    // }
    // if (st.getNonLiteralConstraint().getVocabularyEncodingSchemeURIArray().length > 0)
    // {
    // vo.setVocabulary(st.getNonLiteralConstraint().getVocabularyEncodingSchemeURIArray(0));
    // }
    // if (st.getMinOccurs().intValue() > 0)
    // {
    // vo.setRequired(true);
    // }
    // if (st.getMaxOccurs().intValue() > 1)
    // {
    // vo.setMultiple(true);
    // }
    // for (LiteralOption lo : st.getLiteralConstraint().getLiteralOptionArray())
    // {
    // vo.getConstraints().add(lo.getStringValue());
    // }
    // return vo;
    // }
    // public static MdsContainerVO newMdsContainerVO(ContainerMetadata cm)
    // {
    // MdsContainerVO vo = new MdsContainerVO();
    // vo.setDescription(cm.getDescription());
    // vo.setPersons(cm.getPersons());
    // vo.setTitle(cm.getTitle());
    // return vo;
    // }
    public static Person newPerson()
    {
        Person pers = new Person();
        pers.setAlternativeName("");
        pers.setFamilyName("");
        pers.setGivenName("");
        pers.setIdentifier("");
        pers.getOrganizations().add(newOrganization());
        return pers;
    }

    public static Organization newOrganization()
    {
        Organization org = new Organization();
        org.setName("");
        return org;
    }

    public static List<ViewCollectionBean> collectionListToBeanList(Collection<CollectionImeji> collList)
    {
        List<ViewCollectionBean> beanList = new ArrayList<ViewCollectionBean>();
        for (CollectionImeji coll : collList)
        {
            beanList.add(new ViewCollectionBean(coll));
        }
        return beanList;
    }

    public static List<ImageBean> imageListToBeanList(Collection<Image> imgList)
    {
        List<ImageBean> beanList = new ArrayList<ImageBean>();
        for (Image img : imgList)
        {
            beanList.add(new ImageBean(img));
        }
        return beanList;
    }
}
