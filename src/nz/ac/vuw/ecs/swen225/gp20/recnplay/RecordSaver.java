package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.maze.Move;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class RecordSaver {
    private ArrayList<Move> moves;
    private static String saveMessage;

    public RecordSaver(ArrayList<Move> moves) {//todo name of file
        this.moves = moves;
        saveMessage = "Please enter a name for the recording file (without extension):";
    }

    public void save(String fileName){
//        System.out.println("Save:");
        //todo do we want this here
        //get name of new file if not provided
        if (fileName == null || fileName.isBlank() || fileName.isEmpty()){
            fileName = getFileName();
            if (fileName == null) return; //cancel
        }
        fileName += ".json";
        StringBuilder jsonRecording = new StringBuilder();

        //make text to store in json format
        jsonRecording.append("{\n\t\"Actions\": [\n");
        for (int i = 0; i < moves.size(); i++) {
            jsonRecording.append("\t\t{\n");
            jsonRecording.append("\t\t\t\"movement\": \"").append(moves.get(i)).append("\"\n");
            jsonRecording.append(i < moves.size()-1 ? "\t\t},\n":"\t\t}\n");
        }
        jsonRecording.append("\t]\n}");

        //save to file
        File savingFile = new File("Recordings/"+fileName);
        if (savingFile.exists()){
            //check if file exists and ask user if they want
            //to override existing file based on https://stackoverflow.com/questions/1816673/how-do-i-check-if-a-file-exists-in-java
            int wantTo = JOptionPane.showConfirmDialog(null, "Are you sure you want to override "+fileName+"?");
            if (wantTo != JOptionPane.YES_OPTION){
                //try again
                save(null);
                return;
            }
        }
        try {
            //write contents to file or make new file
            // - based on https://stackoverflow.com/questions/52581404/java-create-a-new-file-or-override-the-existing-file
            BufferedWriter newFile = new BufferedWriter(new FileWriter(savingFile));
            newFile.write(jsonRecording.toString());
            newFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "File saved to Recordings folder.");
    }

    public static String getFileName(){
        String fileName = "";
        while (fileName.isEmpty() || fileName.isBlank()){
            try {
                fileName = JOptionPane.showInputDialog(saveMessage); //todo error checking
            } catch (NullPointerException ignored){
                JOptionPane.showMessageDialog(null, "File save cancelled.");
                return null;
            }

            saveMessage = "Filename is empty. Please enter a valid filename.";
        }

        return fileName;
    }
}
