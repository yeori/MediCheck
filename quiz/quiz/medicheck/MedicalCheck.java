package quiz.medicheck;

/**
 * 학생의 신체검사를 나타내는 클래스
 *
 */
public class MedicalCheck {
	private String studentId;
	private String name;
	private double height;
	private double weight;
	private String sex;
	
	/**
	 * 신체검사 정보를 입력받는 생성자
	 * @param studentId 학생번호
	 * @param name 이름
	 * @param sex 성별
	 * @param height 키
	 * @param weight 몸무게
	 */
	public MedicalCheck(String studentId, String name, String sex, double height,
			double weight) {
		this.studentId = studentId;
		this.name = name;
		this.sex = sex;
		this.height = height;
		this.weight = weight;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", name=" + name
				+ ", height=" + height + ", weight=" + weight + ", sex=" + sex
				+ "]";
	}

	public String getStudentID() {
		return studentId;
	}
	
	
	
	
}
