package answer.medicheck.oop.filter;

import quiz.medicheck.MedicalCheck;

/**
 * 자바 제네릭에 대한 이야기 
 * 
 * java 1.5 에서 처음 나왔습니다.
 * 
 * http://docs.oracle.com/javase/tutorial/java/generics/types.html
 * 
 * @author Suhkyung
 *
 */
public class BoyFilter implements Filter<MedicalCheck> {

	@Override
	public boolean isAcceptable(MedicalCheck type) {
		return "M".equals(type.getSex());
	}

}
