package SpriteRE;

import org.junit.Before;
import org.junit.Test;

import DOM.DOM;

import static org.junit.Assert.*;

public class SpriteRETest {
    DOM dom;
    SpriteRE sre;

    @Before
    public void setup() throws Exception {
        dom = new DOM();
        sre = new SpriteRE();
    }

    @Test
    public void renderSprites() throws Exception {

    }

    @Test
    public void setDOM() throws Exception {
        sre.setDOM(dom);
    }

    @Test
    public void setDOMWithInvalidValue() throws Exception {
        sre.setDOM(null);
    }

}