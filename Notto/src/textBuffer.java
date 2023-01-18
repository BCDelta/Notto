import java.util.*;

public class textBuffer {

    private String[] sentenceArray;
    private Stack<String> undoBuffer, redoBuffer, textarea;


    public textBuffer(String text) {
        //Make instance variable array a copy to that of the parameter's array
        sentenceArray = text.split(" ");
        //Need 3 stack variables to hold the text, undo buffer, and redo buffer
        undoBuffer = new Stack<String>();
        redoBuffer = new Stack<String>();
        textarea = new Stack<String>();

        for(int i = 0; i < sentenceArray.length; i++) {
            textarea.push(sentenceArray[i]);
        }

    }

    public String formatText() {
        String formattedString = textarea.toString().replace("[", "")
                                                    .replace(",", "")
                                                    .replace("]", "");
        return formattedString;
    }

    public void undoFeature() {
        undoBuffer.push(textarea.peek());
        textarea.pop();
    }

    public void redoFeature() {
        redoBuffer.push(undoBuffer.peek());
        undoBuffer.pop();
        textarea.push(redoBuffer.peek());
        System.out.println(textarea);
    }
}
