import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AlphabeticSequence {

    /** 알파벳 갯수 */
    private static final int ALPHABET_COUNT = 26;

    /** 일련번호(두자리) 초과 값 */
    private static final int BASE_NUMBER = 100;

    /** 일련번호 출력 값 */
    private static final String ATTRIB_STR = "0Z";

    public static void main(String[] args) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("attr_lvl", 1);
        map.put("start_no", "95");
        map.put("end_no", "3Z");

        int sNum = getAlnumToNumber(map, "start_no");
        int eNum = getAlnumToNumber(map, "end_no");
        if(sNum < 0 || eNum < 0) {
            System.out.println("구분이 잘못되었거나, 범위를 벗어났습니다.");
            return;
        }
        if(sNum > eNum) {
            System.out.println("시작번호가 종료번호 보다 클 수 없습니다.");
            return;
        }

        while(sNum <= eNum) {
            System.out.println(sNum + "=========>" + createNumberToAlnum(sNum));
            sNum++;
        }
    }

    private static String createNumberToAlnum(int seq) {
        String[] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        String returnStr = "";
        int quotient = -1, remainde = -1;

        try {
            StringBuilder sb = new StringBuilder();

            switch(ATTRIB_STR) {
                case "AA":
                case "AZ":
                case "ZZ":
                    // 00~99까지는 변환 불필요
                    if(BASE_NUMBER > seq) return String.valueOf(seq);

                    // 첫번째 영문 추출
                    quotient = (seq - BASE_NUMBER) / ALPHABET_COUNT;
                    // 두번째 영문 추출(나머지)
                    remainde = (seq - BASE_NUMBER) % ALPHABET_COUNT;

                    // 첫번째 영문 + 두번째 영문 연결하기.
                    sb.append(alphabets[quotient]);
                    sb.append(alphabets[remainde]);

                    returnStr = sb.toString();
                    break;
                case "0A":
                case "0Z":
                case "9A":
                case "9Z":
                    // 00~99까지는 변환 불필요
                    if(BASE_NUMBER > seq) return String.valueOf(seq);

                    // 첫번째값 추출
                    quotient = (seq - BASE_NUMBER) / ALPHABET_COUNT;
                    // 두번째 영문 추출(나머지)
                    remainde = (seq - BASE_NUMBER) % ALPHABET_COUNT;

                    sb.append(String.valueOf(quotient));
                    sb.append(alphabets[remainde]);

                    returnStr = sb.toString();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("createNumberToAlnum Exception##" + e.getMessage());
        }
        return returnStr;
    }

    public static int getAlnumToNumber(Map<String, Object> map, String column) {
        String[] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        int sequence = 0;
        int idx = -1;

        try {
            String rangeNo = String.valueOf(map.get(column));

            switch(ATTRIB_STR) {
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
                        // 01,..,99,1A,1B,..,9Z
                        //sequence = BASE_NUMBER + (ALPHABET_COUNT * (Integer.parseInt(rangeNo.substring(0, 1)) -1)) + idx;
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
                    // 2. AA~ZZ
                    break;
                case "A..Z":
                    break;
                default:
                    if(isNumeric(rangeNo)) return Integer.parseInt(rangeNo);
                    break;
            }
        } catch (Exception e) {
            System.out.println("getAlnumToNumber Exception##" + e.getMessage());
        }
        return sequence;
    }

    public static boolean isAlphabet(String str) {
        Pattern pattern = Pattern.compile("[^A-Z]");
        return !pattern.matcher(str).find();
    }

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
