package SpriteRE;

import java.util.ArrayList;
import java.util.HashMap;

import DOM.DOM;
import Objects.Item;

public class SpriteRE {
    DOM dom;

    public void renderSprites() {
        HashMap<Integer, Item> items = dom.getAllDynamicObjects();
        for (int i = 0; i < items.size(); i++) {
            items.get(i).draw();
        }
    }

    public void setDOM(DOM inputDOM) {
        dom = inputDOM;
    }

}
