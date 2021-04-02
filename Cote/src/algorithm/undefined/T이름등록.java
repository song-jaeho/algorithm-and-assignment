package algorithm.undefined;
import java.util.*;

/**
 * 이름을 등록하려고 하는데, 이미 등록된 이름은 등록 할 수 없다.
 * 
 * 다음 인자가 주어졌을 때 성공적으로 추가된 이름(들)을 String 배열로 리턴한다.
 * String[] names : 이미 등록된 이름 배열 
 * String[] addNames : 추가하고자 하는 이름 배열 
 * 
 * addNames는 선착순이다. 
 * 
 * @author song-jaeho
 *
 */
public class T이름등록 {

	public static void main(String[] args) {
		T이름등록 t = new T이름등록();
		String[] resultArr = t.solution(new String[] {"apple", "banana", "carrot", "songjaeho"}, 
										new String[] {"banana", "carrot", "zeroson", "banana", "good"});
		for (String teamName : resultArr) {
			System.out.println(teamName);
		}
	}

	public String[] solution(String[] names, String[] addNames) {
        List<String> answerList = new ArrayList<String>();

        Map<String, String> map = new HashMap<String, String>();
        for (String teamID : names) {
            map.put(teamID, "1");
        }

        for (String add : addNames) {
            if (map.get(add) == null) {
                answerList.add(add);
                map.put(add, "1");
            }
        }

        return answerList.toArray(new String[answerList.size()]);
    }
}

