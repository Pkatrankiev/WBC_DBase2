package Test;
 
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelLockReader {

    public static void main(String[] args) throws Exception {

        String path = "k:\\Docs\\Д-я БиК\\КЦ ПД\\Обща\\04 Поща\\Петър\\~$2024 годишен отчет за обучението.xlsx";
        Path lockPath = Paths.get(path);

        if (!Files.exists(lockPath)) {
            System.out.println("Файлът не е отворен.");
            return;
        }

        byte[] bytes = Files.readAllBytes(lockPath);

        // Excel записва в UTF-16LE
        String content = new String(bytes, Charset.forName("UTF-16LE"));
        System.out.println(content);
        // Премахваме null символи и боклук
//        content = content.replaceAll("[^\\p{Print}]", "");
//
//        System.out.println("Raw content:");
//        System.out.println(content);
//
//        
      

        Pattern p = Pattern.compile("[А-Яа-я0-9]+");
        Matcher m = p.matcher(content);

        while (m.find()) {
            String candidate = m.group();
            System.out.println(candidate);
            if (candidate.length() > 2) {
                System.out.println("Намерен текст: " + candidate);
            }
        }
        
        
        
        // Опит за извличане на username (обикновено е първият текст)
        String[] parts = content.split(" ");
        if (parts.length > 0) {
            System.out.println("Вероятен потребител: " + parts[0]);
        }
    }
}