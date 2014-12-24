package answer.medicheck.oop.filter;

import quiz.medicheck.MedicalCheck;

public class GirlFilter implements Filter<MedicalCheck> {

	@Override
	public boolean isAcceptable(MedicalCheck type) {
		return "F".equals(type.getSex());
	}

}
