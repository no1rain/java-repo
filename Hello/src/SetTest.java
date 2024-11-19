import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SetTest {

    public static void main(String[] args) {        
        String[] arrStr = { "1101", "2101", "3101" };

        Set<String> set = new HashSet<>(Arrays.asList(arrStr));

        System.out.println(set.isEmpty());
        System.out.println("set====" + set);

        if(set.contains("1101")) {
            System.out.println("포함");
        }
    }
}
