package game.textpractice;

import android.renderscript.ScriptGroup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

public class StageParser {
    public static ArrayList<Stage> parse(InputStream xmlStream) throws Exception {
        // output of this function; array list of Stage instances
        ArrayList<Stage> stages = new ArrayList();

        // preparing to parse raw data
        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(xmlStream);
        Element root = doc.getDocumentElement();

        // start parsing raw file to list of Stage instances
        NodeList stageTags = root.getElementsByTagName("stage");
        for(int i = 0; i < stageTags.getLength(); i ++) {
            Element stageElement = (Element) stageTags.item(i);

            // first of all, set name and message attributes to instance
            Stage myStage = new Stage();
            myStage.name = stageElement.getAttribute("name");
            myStage.message = stageElement.getAttribute("message");

            // next, parse all children choice elements
            NodeList choiceTags = stageElement.getElementsByTagName("choice");
            for (int j = 0; j < choiceTags.getLength(); j++) {
                Element choiceElement = (Element) choiceTags.item(j);

                // it contains text and next stage name to move when this choice
                // has been clicked
                Stage.Choice myChoice = new Stage.Choice();
                myChoice.text = choiceElement.getAttribute("text");
                myChoice.nextStageName = choiceElement.getAttribute("click");

                // finally, add this choice to its parent stage instance
                myStage.choices.add(myChoice);
            }

            // stage instance processed would be added to output list
            stages.add(myStage);
        }

        // close all unnecessary streams
        xmlStream.close();

        // return stage list
        return stages;
    }
}
