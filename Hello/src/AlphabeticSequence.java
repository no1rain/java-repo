import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AlphabeticSequence {

    /** 일련번호(두자리) 초과 값 */
    private static final int BASE_NUMBER = 100;

    /** 알파벳 갯수 */
    private static final int ALPHABET_COUNT = 26;

    /** 알파벳 */
    private static final String[] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

     /** 일련번호 포맷 컬럼 */
    private static final String FORMAT_COL = "attr_cls";

    /** 일련번호 출력 구분 */
    private static final String ATTRIB_STR = "0Z";

    public static void main(String[] args) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        //List<Map<String, Object>> list = Lists.newArrayList();
        List<Map<String, Object>> list = new ArrayList<>();

        param.put("attr_lvl", 1);
        param.put(FORMAT_COL, "0Z");
        param.put("start_no", "95");
        param.put("end_no", "3Z");
        // list에 map를 담는다.
        list.add(param);

        // map Initial
        param = new HashMap<String, Object>();
        param.put("attr_lvl", 3);
        param.put(FORMAT_COL, "00");
        param.put("start_no", "9");
        param.put("end_no", "13");
        // list에 map를 담는다.
        list.add(param);

        // map Initial
        param = new HashMap<String, Object>();
        param.put("attr_lvl", 2);
        param.put(FORMAT_COL, "A9");
        param.put("start_no", "99");
        param.put("end_no", "A3");
        // list에 map를 담는다.
        list.add(param);

        System.out.println("list1===" + list);
        
        //오름 차순으로 정렬하기
        Collections.sort(list, new Comparator<Object>() {
            // Comparable 인터페이스를 구현하여 전달
            @Override
            public int compare(Object s1, Object s2) {
                return (Integer)((Map<String,Object>)s1).get("attr_lvl") - (Integer)((Map<String,Object>)s2).get("attr_lvl");
            }
        });
        System.out.println("list2===" + list);

        //내림 차순으로 정렬하기
        Collections.sort(list, new Comparator<Object>() {
            @Override
            public int compare(Object s1, Object s2) {
                return (Integer)((Map<String,Object>)s2).get("attr_lvl") - (Integer)((Map<String,Object>)s1).get("attr_lvl");
            }
        });
        System.out.println("list3===" + list);

        for(Map<String, Object> map: list) {
            int sNum = createAlnumToNumber(map, "start_no");
            int eNum = createAlnumToNumber(map, "end_no");
            if((sNum*eNum) < 0) {
                System.out.println("구분이 잘못되었거나, 범위를 벗어났습니다.");
                return;
            }
            if(sNum > eNum) {
                System.out.println("시작번호가 종료번호 보다 클 수 없습니다.");
                return;
            }

            while(sNum <= eNum) {
                System.out.println(sNum + " ===> " + createNumberToAlnum(sNum, map.get(FORMAT_COL).toString()));
                sNum++;
            }
        }
    }

    /*
     * Number를 출력 구분형태로 치환
     */
    private static String createNumberToAlnum(int sequence, String attr_cls) {
        String alNumSeq = "";

        try {
            StringBuilder sb = new StringBuilder();
            int quotient = -1, remainde = -1;

            switch(attr_cls) {
                case "AA":
                case "AZ":
                case "ZZ":
                    // 00~99까지는 변환 불필요
                    if(BASE_NUMBER > sequence) return String.valueOf(sequence);

                    // 첫번째 영문 추출
                    quotient = (sequence - BASE_NUMBER) / ALPHABET_COUNT;
                    // 두번째 영문 추출(나머지)
                    remainde = (sequence - BASE_NUMBER) % ALPHABET_COUNT;

                    // 영문 + 영문 연결하기.
                    sb.append(alphabets[quotient]);
                    sb.append(alphabets[remainde]);

                    alNumSeq = sb.toString();
                    break;
                case "0A":
                case "0Z":
                case "9A":
                case "9Z":
                    // 00~99까지는 변환 불필요
                    if(BASE_NUMBER > sequence) return String.valueOf(sequence);

                    // 첫번째값 추출
                    quotient = (sequence - BASE_NUMBER) / ALPHABET_COUNT;
                    // 두번째 영문 추출(나머지)
                    remainde = (sequence - BASE_NUMBER) % ALPHABET_COUNT;

                    // 숫자 + 영문 연결하기
                    sb.append(String.valueOf(quotient));
                    sb.append(alphabets[remainde]);

                    alNumSeq = sb.toString();
                    break;
                case "A0":
                case "A9":
                    // 00~99까지는 변환 불필요
                    if(BASE_NUMBER > sequence) return String.valueOf(sequence);
                    
                    // 첫번째값 추출
                    quotient = (sequence - BASE_NUMBER) / 10;
                    // 두번째 영문 추출(나머지)
                    remainde = (sequence - BASE_NUMBER) % 10;

                    // 영문 + 숫자 연결하기
                    sb.append(alphabets[quotient]);
                    sb.append(String.valueOf(remainde));

                    alNumSeq = sb.toString();
                    break;
                default:
                    alNumSeq = String.valueOf(sequence);
                    break;
            }
        } catch (Exception e) {
            System.out.println("createNumberToAlnum Exception##" + e.getMessage());
        }
        return alNumSeq;
    }

    /*
     * map의 column에서 값을 뽑아 Number로 치환
     */
    public static int createAlnumToNumber(Map<String, Object> map, String column) {
        int sequence = 0;
        int idx = -1;

        try {
            String rangeNo = String.valueOf(map.get(column));
            String attrCls = String.valueOf(map.get(FORMAT_COL));

            switch(attrCls) {
                case "A0":
                case "A9":
                    if(rangeNo.length() != 2) return idx;
                    if(isAlphabet(rangeNo)) return idx;
                    if(isNumeric(rangeNo)) return Integer.parseInt(rangeNo);

                    // 01,..,99,A0,A1,..,Z9
                    idx = Arrays.asList(alphabets).indexOf(rangeNo.substring(0, 1));    // 영문 추출
                    if(idx > -1) {
                        sequence = BASE_NUMBER + (10*idx);
                        sequence += Integer.parseInt(rangeNo.substring(1, 2));
                    }
                    break;
                case "0A":
                case "0Z":
                case "9A":
                case "9Z":
                    if(rangeNo.length() != 2) return idx;
                    if(isAlphabet(rangeNo)) return idx;
                    if(isNumeric(rangeNo)) return Integer.parseInt(rangeNo);

                    // 01,..,99,0A,0B,..,9Z
                    idx = Arrays.asList(alphabets).indexOf(rangeNo.substring(1, 2));    // 영문 추출
                    if(idx > -1) {
                        sequence = BASE_NUMBER + (ALPHABET_COUNT * Integer.parseInt(rangeNo.substring(0, 1)));
                        sequence += idx;
                    }
                    break;
                case "AA":
                case "AZ":
                case "ZZ":
                    if(rangeNo.length() != 2) return idx;
                    if(isNumeric(rangeNo)) return Integer.parseInt(rangeNo);

                    // 1. 00~ZZ (00~99,AA~ZZ)
                    idx = Arrays.asList(alphabets).indexOf(rangeNo.substring(0, 1));    // 1번째 영문 추출
                    if(idx == -1)
                        return idx;

                    sequence = BASE_NUMBER + (ALPHABET_COUNT * idx);

                    idx = Arrays.asList(alphabets).indexOf(rangeNo.substring(1, 2));    // 2번째 영문 추출
                    if(idx == -1)
                        return idx;
                    
                    sequence = sequence + idx;
                    break;
                default:
                    if(isNumeric(rangeNo)) return Integer.parseInt(rangeNo);
                    break;
            }
        } catch (Exception e) {
            System.out.println("createAlnumToNumber Exception##" + e.getMessage());
        }
        return sequence;
    }

    /*
     * 영문 여부 판별
     */
    public static boolean isAlphabet(String str) {
        Pattern pattern = Pattern.compile("[^A-Z]");
        return !pattern.matcher(str).find();
    }

    /*
     * 숫자 여부 판별
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[^0-9]");
        return !pattern.matcher(str).find();
    }

    public static boolean isNumeric2(String str) {
        boolean result = true;
        for(char c : str.toCharArray()){
            if(!Character.isDigit(c)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
