/**
 * License: src/main/resources/license/escidoc.license
 */
package de.mpg.imeji.presentation.album;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import de.mpg.imeji.logic.controller.AlbumController;
import de.mpg.imeji.logic.controller.ItemController;
import de.mpg.imeji.logic.search.Search;
import de.mpg.imeji.logic.search.SearchResult;
import de.mpg.imeji.logic.search.vo.SearchIndex;
import de.mpg.imeji.logic.search.vo.SearchQuery;
import de.mpg.imeji.logic.search.vo.SortCriterion;
import de.mpg.imeji.logic.search.vo.SortCriterion.SortOrder;
import de.mpg.imeji.logic.security.Security;
import de.mpg.imeji.logic.security.Operations.OperationsType;
import de.mpg.imeji.logic.util.ObjectHelper;
import de.mpg.imeji.logic.vo.Album;
import de.mpg.imeji.logic.vo.CollectionImeji;
import de.mpg.imeji.presentation.beans.Navigation;
import de.mpg.imeji.presentation.beans.SessionBean;
import de.mpg.imeji.presentation.image.ImagesBean;
import de.mpg.imeji.presentation.image.SelectedBean;
import de.mpg.imeji.presentation.image.ThumbnailBean;
import de.mpg.imeji.presentation.util.BeanHelper;
import de.mpg.imeji.presentation.util.ImejiFactory;

public class AlbumImagesBean extends ImagesBean
{
    private int totalNumberOfRecords;
    private String id = null;
    private AlbumBean album;
    private URI uri;
    private SessionBean sb;
    private CollectionImeji collection;
    private Navigation navigation;

    public AlbumImagesBean()
    {
        super();
        this.sb = (SessionBean)BeanHelper.getSessionBean(SessionBean.class);
        this.navigation = (Navigation)BeanHelper.getApplicationBean(Navigation.class);
    }

    public void init()
    {
        AlbumController ac = new AlbumController(sb.getUser());
        try
        {
            this.setAlbum(new AlbumBean(ac.retrieveLazy(ObjectHelper.getURI(Album.class, id))));
        }
        catch (Exception e)
        {
            BeanHelper.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getNavigationString()
    {
        if (album != null)
        {
            if (sb.getSelectedImagesContext() != null
                    && !(sb.getSelectedImagesContext().equals("pretty:albumImages"
                            + album.getAlbum().getId().toString())))
            {
                sb.getSelected().clear();
            }
            sb.setSelectedImagesContext("pretty:albumImages" + album.getAlbum().getId().toString());
        }
        return "pretty:albumImages";
    }

    @Override
    public int getTotalNumberOfRecords()
    {
        return totalNumberOfRecords;
    }

    @Override
    public List<ThumbnailBean> retrieveList(int offset, int limit)
    {
        uri = ObjectHelper.getURI(Album.class, id);
        SortCriterion sortCriterion = initSortCriterion();
        ItemController controller = new ItemController(sb.getUser());
        SearchResult result = controller.searchImagesInContainer(uri, new SearchQuery(), sortCriterion, limit, offset);
        totalNumberOfRecords = result.getNumberOfRecords();
        result.setQuery(getQuery());
        result.setSort(sortCriterion);
        return ImejiFactory.imageListToThumbList(loadImages(result));
    }

    @Override
    public String initFacets() throws Exception
    {
        // NO FACETs FOR ALBUMS
        return "pretty";
    }

    public String removeFromAlbum() throws Exception
    {
        AlbumController ac = new AlbumController(sb.getUser());
        BeanHelper.info(album.getAlbum().getImages().size() + " " + sb.getMessage("success_album_remove_images"));
        album.getAlbum().getImages().clear();
        ac.update(album.getAlbum());
        AlbumBean activeAlbum = sb.getActiveAlbum();
        if (activeAlbum != null
                && activeAlbum.getAlbum().getId().toString().equals(album.getAlbum().getId().toString()))
        {
            sb.setActiveAlbum(album);
        }
        SelectedBean sb = (SelectedBean)BeanHelper.getSessionBean(SelectedBean.class);
        sb.clearAll();
        return "pretty:";
    }

    public String getImageBaseUrl()
    {
        if (album.getAlbum() == null)
            return "";
        return navigation.getApplicationUri() + album.getAlbum().getId().getPath();
    }

    public String getBackUrl()
    {
        return navigation.getImagesUrl() + "/album" + "/" + this.id;
    }

    public String release()
    {
        ((AlbumBean)BeanHelper.getSessionBean(AlbumBean.class)).setId(id);
        ((AlbumBean)BeanHelper.getSessionBean(AlbumBean.class)).initView();
        ((AlbumBean)BeanHelper.getSessionBean(AlbumBean.class)).release();
        return "pretty:";
    }

    public String delete()
    {
        ((AlbumBean)BeanHelper.getSessionBean(AlbumBean.class)).setId(id);
        ((AlbumBean)BeanHelper.getSessionBean(AlbumBean.class)).initView();
        ((AlbumBean)BeanHelper.getSessionBean(AlbumBean.class)).delete();
        return "pretty:albums";
    }

    public String withdraw() throws Exception
    {
        ((AlbumBean)BeanHelper.getSessionBean(AlbumBean.class)).setId(id);
        ((AlbumBean)BeanHelper.getSessionBean(AlbumBean.class)).initView();
        String dc = getAlbum().getAlbum().getDiscardComment();
        ((AlbumBean)BeanHelper.getSessionBean(AlbumBean.class)).getAlbum().setDiscardComment(dc);
        ((AlbumBean)BeanHelper.getSessionBean(AlbumBean.class)).withdraw();
        return "pretty:";
    }

    public boolean isEditable()
    {
        Security security = new Security();
        return security.check(OperationsType.UPDATE, sb.getUser(), album.getAlbum());
    }

    public boolean isDeletable()
    {
        Security security = new Security();
        return security.check(OperationsType.DELETE, sb.getUser(), album.getAlbum());
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setCollection(CollectionImeji collection)
    {
        this.collection = collection;
    }

    public CollectionImeji getCollection()
    {
        return collection;
    }

    public void setAlbum(AlbumBean album)
    {
        this.album = album;
    }

    public AlbumBean getAlbum()
    {
        return album;
    }
}
