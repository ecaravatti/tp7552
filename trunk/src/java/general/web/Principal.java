
package general.web;

import org.apache.wicket.markup.html.WebPage;


/**
 * Body frame page for the frames example.
 * 
 * @author Javier Persico
 */
public class Principal extends WebPage
{

    /** name for page map etc. */
    public static final String RIGHT_FRAME_NAME = "right";

    //private final FrameTarget frameTarget = new FrameTarget(PaginaPrueba.class);

    /**
     * Constructor
     */
    public Principal()
    {

    }

    /**
     * Gets frameTarget.
     * 
     * @return frameTarget
     */
    /*public FrameTarget getFrameTarget()
    {
        return frameTarget;
    }*/

    /**
     * @see org.apache.wicket.Component#isVersioned()
     */
    @Override
    public boolean isVersioned()
    {
        return false;
    }
}