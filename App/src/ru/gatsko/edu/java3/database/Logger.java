package ru.gatsko.edu.java3.database;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger implements Serializable {
    static String path = "C:/Users/gatsko/IdeaProjects/First/App/src/ru/gatsko/edu/java3/database/logs.txt";
    static String logfile = "C:/Users/gatsko/IdeaProjects/First/App/src/ru/gatsko/edu/java3/database/querylog.txt";


    int totalQueries;
    public void Logger(){
        this.totalQueries = 0;
    }
    public static Logger makeLogger(){
        File file = new File(path);
        if (file.exists()){
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
                return (Logger)in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                file.delete();
                e.printStackTrace();
                return new Logger();
            }
        } else{
            return new Logger();
        }
    }
    public void saveLogger(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logCommand(String command){
        PrintWriter out = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(logfile, true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(df.format(new Date()) + ": " + command);
        out.close();
    }
}
