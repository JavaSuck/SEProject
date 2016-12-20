package DOM;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import Objects.Item;
import TCP.TCPCM;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;


public class DOMTest {
    private DOM dom;

    @Before
    public void setup() throws Exception {
        dom = new DOM();
        // create self character
        dom.addVirtualCharacter(0);
        dom.addItem("1", 5, false);
    }

    @Test
    public void getCharacters() throws Exception {
        HashMap<Integer, VirtualCharacter> characterArray = new HashMap<Integer, VirtualCharacter>();
        assertEquals(characterArray.getClass(), dom.getCharacters().getClass());
    }

    @Test
    public void addVirtualCharacter() throws Exception {
        dom.addVirtualCharacter(1);
        assertNotNull(dom.getCharacters().get(1));
    }

    @Test
    public void getVirtualCharacterXY() throws Exception {
        assertEquals(Point.class, dom.getVirtualCharacterXY().getClass());
    }

    @Test
    public void updateVirtualCharacter() throws Exception {
        dom.updateVirtualCharacter(0, 2, 10, 15, 20);
        VirtualCharacter chac = dom.getCharacters().get(0);
        assertEquals(2, chac.getCurrentDirection());
        assertEquals(10, chac.getSpeed());
        assertEquals(15, chac.getPosition().x);
        assertEquals(20, chac.getPosition().y);
    }

    @Test(expected = NullPointerException.class)
    public void updateVirtualCharacterWithWrongID() {
        dom.updateVirtualCharacter(99, 2, 10, 15, 20);
    }

    @Test
    public void addItem() throws Exception {
        dom.addItem("item1", 0, false);
        Item item = dom.getAllDynamicObjects().get(0);
        assertEquals("item1", item.getName());
        assertEquals(false, item.getShared());
    }

    @Test
    public void updateItem() throws Exception {
        dom.addItem("item2", 1, false);
        dom.updateItem(1, true, 2);
        Item item = dom.getAllDynamicObjects().get(1);
        assertEquals("item2", item.getName());
        assertEquals(true, item.getShared());
        assertEquals(2, item.getOwner());
    }

    @Test(expected = NullPointerException.class)
    public void updateItemWithInvalidID() {
        dom.updateItem(47, true, 3);
    }


    @Test
    public void getAllDynamicObjects() throws Exception {
        HashMap<Integer, Item> items = new HashMap<Integer, Item>();
        assertEquals(items.getClass(), dom.getAllDynamicObjects().getClass());
    }

    @Test
    public void keyGETPressed() throws Exception {
        TCPCM tcpcm = new TCPCM();
        dom.setTcpcm(tcpcm);
        dom.keyGETPressed("L");
    }

    @Test
    public void keyGETPressedWithOtherString() throws Exception {
        TCPCM tcpcm = new TCPCM();
        dom.setTcpcm(tcpcm);
        dom.keyGETPressed("Hello");
    }

}