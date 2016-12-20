package DOM;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.BeforeClass;

import java.awt.*;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

/**
 * Created by DMOON on 2016/12/14.
 */
public class VirtualCharacterTest {
    private VirtualCharacter character;

    @Before
    public void setUp() {
        character = new VirtualCharacter("sprite.png");
    }

    @org.junit.Test
    public void getPosition() throws Exception {
        assertThat(character.getPosition(), instanceOf(Point.class));
    }

    @org.junit.Test
    public void walk() throws Exception {
        character.walk(2);
        assertEquals(2, character.getCurrentDirection());
    }

    @org.junit.Test
    public void start() throws Exception {
        character.start();
    }

    @org.junit.Test
    public void stop() throws Exception {
        int preDir = character.getCurrentDirection();
        character.stop();
        int curDir = character.getCurrentDirection();
        assertEquals(preDir, curDir);
    }

    @org.junit.Test
    public void updateCharacter() throws Exception {
        character.updateCharacter(1, 15, 20, 10);
        assertEquals(20, character.getPosition().x);
        assertEquals(10, character.getPosition().y);
        assertEquals(1, character.getCurrentDirection());
        assertEquals(15, character.getSpeed());
    }
}