import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class App {
	
	private static String RFXRANK = "rfx_rank";

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Set<Object> set = new HashSet<Object>();

        set.add("Geeks");
        set.add("For");
        set.add("Geeks");
        System.out.println(set);

	    Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 1, 4, 3, 6, 5));
		
		System.out.println("set1====="+ set1);
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put(RFXRANK, 6);
		
		boolean isCompare = isGreatThenMap(set1, map1, "G");

		System.out.println("isCompare====="+ isCompare);
    }

	private static boolean isGreatThenMap(Set<Integer> set, Map<String, Object> map, String inSign) {
		boolean isGreat = false;
		
		if(set.isEmpty()) return isGreat;

        Integer sign = "G".equals(inSign) ? -1 : "L".equals(inSign) ? 1 : 0;
		System.out.println("sign====="+ sign);
		Integer allottedCnt = Integer.parseInt(map.get(RFXRANK).toString());
		for(Integer obj : set) {
            // Set내에 입력받은 값보다 큰게 존재
			if(allottedCnt.compareTo(obj) == sign) {
                isGreat = true;
                break;
            }
		}
        System.out.println("Contains=====" + set.contains(map.get(RFXRANK)));
		return isGreat;
	}
}
