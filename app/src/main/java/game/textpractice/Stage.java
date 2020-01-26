package game.textpractice;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Stage {
    public String name;
    public String message;
    public ArrayList<Choice> choices;

    public Stage() {
        this.choices = new ArrayList<Choice>();
    }

    public static class Choice {
        public String text;
        public String nextStageName;
    }
}
