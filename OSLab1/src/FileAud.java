import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

import static java.lang.System.in;
import static java.lang.System.out;

public class FileAud {
    // Exercise 4
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String filePath = br.readLine();
        FunctionPrint(filePath);
    }

    static void FunctionPrint(String filePath)
    {
        File f = new File(filePath);
        File dir = new File(filePath);
        File[] list1 = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });
        File[] list2 = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".out");
            }
        });
        for(int i=0; i<list1.length; i++)
        {
            if(list1[i].length()>1 && list1[i].length()<100 )
            out.println(list1[i].getAbsolutePath());
            if(list1[i].isDirectory())
                 FunctionPrint(list1[i].getPath());
        }
        for(int i=0; i<list2.length; i++)
        {
            if(list2[i].length()>1 && list2[i].length()<100 )
                out.println(list2[i].getAbsolutePath());
            if(list2[i].isDirectory())
                FunctionPrint(list2[i].getPath());
        }
    }

//      Exercise 6 - Reverse source.txt content and write it to destination.txt
//    public static void main(String[] args) throws IOException {
//
//        BufferedReader br = new BufferedReader(new FileReader("source.txt"));
//        BufferedWriter bw = new BufferedWriter(new FileWriter("destination.txt"));
//
//        StringBuilder sb = new StringBuilder();
//        String content;
//        while ((content = br.readLine()) != null)
//        {
//            sb = new StringBuilder(content);
//            sb.reverse();
//            bw.write(sb+"\n");
//        }
//
//        br.close();
//        bw.flush();
//        bw.close();
//    }

//      Exercise 1/2
//    public static final String filePath = ".";
//
//    public static void main(String[] args) throws FileNotFoundException {
//
//            File  f = new File("f.txt");
//            if (!f.isFile()) {
//                throw new FileNotFoundException();
//            }
//            File[] files = f.listFiles();
//            for
//            (File file: files) {
//                if (!file.exists()) {
//                    break;
//                }
//                if (file.exists()&& file.getName().contains("idea")) {
//                    System.out.println(file.getAbsolutePath());
//                }
//            }
//    }
//
//    public static void main(String[] args) {
//        File file = new File(".");
//        FunctionPrint(file);

//      Exercise 5 - fill in
//    public static void main(String[] args) throws IOException {
//        BufferedReader reader = null;
//        BufferedWriter writer =null;
//        try{
//            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
//            writer = new BufferedWriter(new OutputStreamWriter(out));
//        }catch(IOException e){
//            if(reader!=null)
//                reader.close();
//            if(writer!=null)
//            {
//                writer.flush();
//                writer.close();
//            }
//        }
//    }

    // Exercise 3
//    public static void main(String[] args) throws IOException {
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        String filePath = br.readLine();
//        FunctionPrint(filePath);
//    }
//
//    static void FunctionPrint(String filePath)
//    {
//        File f = new File(filePath);
//
//        File [] list  = f.listFiles();
//        for(int i=0; i<list.length; i++)
//        {
//            if(list[i].length()>50)
//                out.println(list[i].getName());
//            if(list[i].isDirectory())
//                FunctionPrint(list[i].getPath());
//        }
}