import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestApp01 {

	@SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

		final String jsonArr = "[" +
			"{\"id\":1,\"name\":\"Alex\",\"age\":41}," +
			"{\"id\":2,\"name\":\"Brian\",\"age\":42}," +
			"{\"id\":2,\"name\":\"Tom\",\"age\":32}," +
			"{\"id\":2,\"name\":\"William\",\"age\":57}," +
			"{\"id\":3,\"name\":\"Kein\",\"age\":28}," +
			"{\"id\":3,\"name\":\"Charles\",\"age\":43}]";

		ObjectMapper mapper = new ObjectMapper();

		try {
			List<Map<String, Object>> list = mapper.readValue(jsonArr, new TypeReference<List<Map<String, Object>>>(){});

			for(Map<String, Object> map: list) {
				System.out.println("map===" + map);
			}

			Map<String, Object> map = rmoveListDuplication(list);
			System.out.println("map=====" + map);

			Set<Object> totalitiesSet = (Set<Object>) map.get("totalitiesSet");
			Set<Object> duplicatesSet = (Set<Object>) map.get("duplicatesSet");

			System.out.println("totalitiesSet=====" + totalitiesSet);
			System.out.println("duplicatesSet=====" + duplicatesSet);

			// 모든 값 추출
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private static Map<String, Object> rmoveListDuplication(List<Map<String, Object>> list) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Set<Object> duplicatesSet = new HashSet<Object>();
		Set<Object> totalitiesSet = new HashSet<Object>();

		for(Map<String, Object> map: list) {
			if(map.get("id") != null) {
				if(totalitiesSet.contains(map.get("id"))) {
					duplicatesSet.add(map.get("id"));
				}
				totalitiesSet.add(map.get("id"));
			}
		}

		resultMap.put("totalitiesSet", totalitiesSet);
		resultMap.put("duplicatesSet", duplicatesSet);

		return resultMap;
	}
}
