package algorithm.undefined;
import java.util.*;

/**
 * 팀 이름을 등록하려고 하는데, 이미 등록된 팀 이름은 사용 할 수 없다.
 * 
 * 다음 인자가 주어졌을 때 성공적으로 추가된 팀 이름(들)을 String 배열로 리턴한다.
 * String[] teamIDs : 이미 등록된 팀 이름 배열 
 * String[] additional : 추가하고자 하는 팀 이름 배열 
 * 
 * additional은 선착순이라고 가정한다. 
 * 
 * @author song-jaeho
 *
 */
public class T팀이름중복 {

	public static void main(String[] args) {
		T팀이름중복 t = new T팀이름중복();
		String[] resultArr = t.solution(new String[] {"apple", "banana", "carrot", "songjaeho"}, 
										new String[] {"banana", "carrot", "zeroson", "banana", "good"});
		for (String teamName : resultArr) {
			System.out.println(teamName);
		}
	}

	public String[] solution(String[] teamIDs, String[] additional) {
        List<String> answerList = new ArrayList<String>();

        Map<String, String> map = new HashMap<String, String>();
        for (String teamID : teamIDs) {
            map.put(teamID, "1");
        }

        for (String add : additional) {
            if (map.get(add) == null) {
                answerList.add(add);
                map.put(add, "1");
            }
        }

        return answerList.toArray(new String[answerList.size()]);
    }
}

