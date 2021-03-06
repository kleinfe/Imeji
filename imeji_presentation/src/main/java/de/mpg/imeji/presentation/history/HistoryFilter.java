/**
 * License: src/main/resources/license/escidoc.license
 */
package de.mpg.imeji.presentation.history;

import java.io.IOException;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class HistoryFilter implements Filter
{
    private FilterConfig filterConfig = null;
    private ServletContext servletContext;

    public void destroy()
    {
        filterConfig = null;
    }

    public void doFilter(ServletRequest serv, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException
    {
        HttpServletRequest request = (HttpServletRequest)serv;
        servletContext = request.getSession().getServletContext();
        HistorySession hs = getHistorySession(request, resp);
        String q = (String)request.getParameter("q");
        String h = (String)request.getParameter("h");
        String f = (String)request.getParameter("f");
        // Parameter used by pretty query to pass parameter defined in pretty-config in the url pattern
        String[] ids = request.getParameterValues("com.ocpsoft.vP_0");
        // If f exists, then it is a filter, not added to history
        if (f == null)
        {
            if (h == null)
            {
                // if h not defined, then it is a new page
                hs.add(request.getPathInfo().replaceAll("/", ""), q, ids);
            }
            else if (!"".equals(h))
            {
                // If h defined, then it is an history link
                hs.remove(Integer.parseInt(h));
            }
        }
        chain.doFilter(serv, resp);
    }

    public void init(FilterConfig arg0) throws ServletException
    {
        this.filterConfig = arg0;
    }

    private HistorySession getHistorySession(ServletRequest request, ServletResponse resp)
    {
        String name = (String)HistorySession.class.getSimpleName();
        FacesContext fc = getFacesContext(request, resp);
        Object result = fc.getCurrentInstance().getExternalContext().getSessionMap().get(name);
        if (result == null)
        {
            try
            {
                HistorySession newBean = HistorySession.class.newInstance();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(name, newBean);
                return newBean;
            }
            catch (Exception e)
            {
                throw new RuntimeException("Error creating History Session", e);
            }
        }
        else
        {
            return (HistorySession)result;
        }
    }

    /**
     * Get Faces Context from Filter
     * 
     * @param request
     * @param response
     * @return
     */
    private FacesContext getFacesContext(ServletRequest request, ServletResponse response)
    {
        // Try to get it first
        FacesContext facesContext = FacesContext.getCurrentInstance();
        // if (facesContext != null) return facesContext;
        FacesContextFactory contextFactory = (FacesContextFactory)FactoryFinder
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        LifecycleFactory lifecycleFactory = (LifecycleFactory)FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        facesContext = contextFactory.getFacesContext(servletContext, request, response, lifecycle);
        // Set using our inner class
        InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);
        // set a new viewRoot, otherwise context.getViewRoot returns null
        UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "imeji");
        facesContext.setViewRoot(view);
        return facesContext;
    }

    public abstract static class InnerFacesContext extends FacesContext
    {
        protected static void setFacesContextAsCurrentInstance(FacesContext facesContext)
        {
            FacesContext.setCurrentInstance(facesContext);
        }
    }
}
