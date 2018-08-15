package ru.gatsko.edu.java3.streams;

import java.io.*;
import java.util.*;

class WorkWithHugeFile{
    static BufferedWriter bw = null;
    static BufferedReader br = null;
    static public void Maker(String filename, int size){
        try {
            bw = new BufferedWriter( new FileWriter( filename));
            Random r = new Random();
            for ( int i= 0 ; i < size ; i++) {
                if (i%100 == 0 && i > 0) {
                    bw.write('\n');
                } else {
                    int rr = r.nextInt(90)+32;
                    bw.write((char) rr);
                }
            }
            bw.close();
//            br = new BufferedReader( new FileReader( filename));
//            String str;
//            while ((str = br.readLine()) != null )
//                System.out.println(str);
//            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static public void ReaderPage(String fileName, int pageNum, int pageSize){
        if (pageNum < 0) {
            System.out.println("Страницы начинаются с первой");
            return;
        }
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            raf.seek(pageNum * pageSize);
            int charNum = raf.read();
            if (charNum == -1){
                System.out.println("В файле нет такой страницы");
                return;
            }
            for (int i = 0; i < pageSize; i++){
                charNum = raf.read();
                if (charNum > -1) System.out.print((char)charNum);
            }
            System.out.print('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Filler{
    static byte[] bw = {10,20,30,40,50};
    static FileOutputStream out = null;
    static void fill(String filename, byte[] bw){
        try {
            out = new FileOutputStream(filename);
            out.write(bw);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static void fill(String filename){
        fill(filename,bw);
    }
    static void fillByArray(String filename, String[] files, int size){
        try {
            out = new FileOutputStream(filename);
            ArrayList<InputStream> arrayInput = new ArrayList<>();
            for (String file : files){
                arrayInput.add(new FileInputStream( file ));
            }
            SequenceInputStream seq = new SequenceInputStream(Collections.enumeration(arrayInput));
            int rb = seq.read();
            while (rb != -1) {
                out.write(rb);
                rb = seq.read();
            }
//            for (String file: files){
//                Reader.ReturnValue data = Reader.getByteArray(file, size);
//                out.write(Arrays.copyOfRange(data.arr, 0, data.size));
//            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
class Reader{
    static class ReturnValue{
        byte[] arr;
        int size;
        ReturnValue(byte[] arr, int size){
            this.arr = arr;
            this.size = size;
        }
    }
    static FileInputStream in = null;
    static void out(String filename, Integer size) {
        ReturnValue data = getByteArray(filename, size);
        byte[] br = data.arr;
        int count = data.size;
        System.out.println(Arrays.toString(Arrays.copyOfRange(br, 0, count)));
    }
    static ReturnValue getByteArray(String filename, Integer size) {
        byte[] br = new byte[size];
        int count = 0;
        try {
            in = new FileInputStream( filename );
            count = in.read(br);
            System.out.println( "Прочитано " + count + " байт из файла " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ReturnValue(br,count);
    }
}

public class Application {
    static final String fileName = "bytefile.txt";
    static final String hugeFileName = "huge.txt";
    static final int BYTESIZE = 100;
    static final int PAGESIZE = 500;
    static final int HUGEFILESIZE = 15000000;
    public static void main(String[] args) {
        Filler.fill(fileName);
        Reader.out(fileName,BYTESIZE);
        String[] files = new String[] {"file1.txt","file2.txt","file3.txt","file4.txt","file5.txt"};
        for (String file: files){
            Filler.fill(file);
        }
        Filler.fillByArray("fileUnion.txt", files, BYTESIZE);
        Reader.out("fileUnion.txt",BYTESIZE);


        WorkWithHugeFile.Maker(hugeFileName,HUGEFILESIZE);

        Scanner in = new Scanner(System.in);
        System.out.println("Введите номер страницы для отображения");

        while (true){
            String input = in.nextLine();
            if ("end".equals(input)){break;}
            if (!input.isEmpty()) {
                try{ WorkWithHugeFile.ReaderPage(hugeFileName, Integer.parseInt(input) - 1, PAGESIZE);
                } catch (NumberFormatException e) { System.out.println("В качестве номера страницы должно быть введено число"); }

            }
        }
    }
}
