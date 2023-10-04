package model.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ItemTest {

    @Test
    public void test() {
        Item i = new Item(0,0);
        i.initHitbox(1,1);


    }

}
