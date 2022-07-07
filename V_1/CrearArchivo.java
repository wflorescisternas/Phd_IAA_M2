import java.io.PrintWriter;
public class CrearArchivo {
    public static void main(String ars[]){
        try {
            PrintWriter writer = new PrintWriter("/filename.txt", "UTF-8");
            writer.println("Primera línea");
            writer.println("Segunda línea");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}