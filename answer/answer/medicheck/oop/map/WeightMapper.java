package answer.medicheck.oop.map;
import quiz.medicheck.MedicalCheck;

/**
 * 신체검사 정보에서 몸무게 값만 뽑아줍니다.
 * @author Suhkyung
 *
 */
public class WeightMapper implements Mapper<Double, MedicalCheck> {

	@Override
	public Double map(MedicalCheck type) {
		return type.getWeight();
	}
	
}