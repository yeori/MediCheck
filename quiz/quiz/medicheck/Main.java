package quiz.medicheck;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * 아래 코드는 메소드 구현의 중요 부분들이 지워져 있습니다.
 * (지워진 부분은 TODO로 표시해놓았습니다.)
 * 
 * 구현을 채워서 다음과 같이 콘솔 화면에 출력되도록 해봅니다.
 * 
===============================================
[전체학생 평균 키]
 171.28 cm

[전체학생 평균 몸무게]
 62.778571 kg

[남학생들 평균 키]
 175.10 cm

[여학생들 평균 키]
 164.40 cm

[가장 키가 작은 학생]
 2014 방은진 : 155.9

[가장 무거운 학생]
 2008 장혁수 : 88.3

[키순서(내림차순)]
 1 2002  최진철 183.2cm
 2 2006  박경혁 180.3cm
 3 2008  장혁수 180.3cm
 4 2009  임근형 180.3cm
 5 2005  김인수 174.9cm
 6 2010  서재연 173.5cm
 7 2003  최효철 170.8cm
 8 2011  김민욱 170.8cm
 9 2004  박정미 169.9cm
10 2013  강영호 169.0cm
11 2012  홍민규 166.3cm
12 2007  민지영 162.4cm
13 2001  김영미 160.3cm
14 2014  방은진 155.9cm

[몸무게순서(오름차순)]
 1 2001  김영미 45.0cm
 2 2014  방은진 48.2cm
 3 2012  홍민규 48.3cm
 4 2004  박정미 50.2cm
 5 2007  민지영 52.7cm
 6 2013  강영호 55.0cm
 7 2010  서재연 55.8cm
 8 2003  최효철 59.3cm
 9 2011  김민욱 60.3cm
10 2006  박경혁 72.9cm
11 2009  임근형 78.3cm
12 2002  최진철 78.4cm
13 2005  김인수 86.2cm
14 2008  장혁수 88.3cm
===============================================

구현의 전체적인 패턴은 printAverageHeight 메소드와 거의 유사합니다.
반복문 안에서 조건의 대상만 다를 뿐이니 printAverageHeight 를 참고하면
구현하기 수월합니다.
 
 */
public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		List<MedicalCheck> medicalCheckList = loadMedicalData();
		
		// 전체 학생의 평균키와 평균몸무게
		printAverageHeight(medicalCheckList, "전체학생 평균 키");
		printAverageWeight(medicalCheckList, "전체학생 평균 몸무게");
		
		// 성별 평균키 출력
		printBoysHeight(medicalCheckList, "남학생들 평균 키");
		printGirlsHeight(medicalCheckList, "여학생들 평균 키");
		
		// 키가 가장 작은 학생, 가장 무거운 학생
		printLowestStudent(medicalCheckList, "가장 키가 작은 학생");		
		printBiggestStudent(medicalCheckList, "가장 무거운 학생");

		
		
		// 정렬 - 키의 오름차순으로 출력하기 .... < ... < ....
		printStudentByHeight(medicalCheckList, "키순서(내림차순)");
		// 정렬 - 몸무게의 오름차순으로 출력하기
		printStudentByWeight(medicalCheckList, "몸무게순서(오름차순)");

		/*도전 과제 : 다중정렬
		 * 
		 * input.txt에는 키가 같은 학생이 세 명 있습니다.
		 * 
		 *  2 2006  박경혁 180.3cm
		 *  3 2008  장혁수 180.3cm
		 *  4 2009  임근형 180.3cm
		 * 
		 * 키의 내림차순으로 출력할 때 키가 같은 경우 이름의 오름차순으로
		 * 출력해달라는 요구사항이 추가되었습니다.(즉 아래처럼...)
		 * 
		 *  2 2006  박경혁 180.3cm
		 *  3 2009  임근형 180.3cm
		 *  4 2008  장혁수 180.3cm
		 * 
		 * 위와 같이 출력될 수 있도록 Comparator구현을 만들어보세요.
		 * 
		 */
	}
	
	/**
	 * b/medicheck/input.txt 를 읽어서 학생들의 신체검사 데이터를 List로 반환하는 메소드
	 * @return 신체검사 정보가 담긴 List
	 * @throws FileNotFoundException
	 */
	private static List<MedicalCheck> loadMedicalData() throws FileNotFoundException {

		ArrayList<MedicalCheck> medicalCheckList = new ArrayList<MedicalCheck>();
		
		ClassLoader cloader = Main.class.getClassLoader();
		InputStream fileStream = cloader.getResourceAsStream("quiz/medicheck/input.txt");
		Scanner scanner = new Scanner(fileStream);
		while (scanner.hasNext()) {
			MedicalCheck s = new MedicalCheck(
					scanner.next(), // 학생 번호 
					scanner.next(), // 이름 
					scanner.next(), // 성별
					scanner.nextDouble(), // 키 
					scanner.nextDouble()); // 몸무게
			medicalCheckList.add(s);
		}
		
		scanner.close();
		return medicalCheckList;
	}
	
	/**
	 * 전체 학생의 평균 키를 구해 화면으로 출력하는 메소드
	 * @param medicalCheckList 각 학생의 신체검사 정보를 모은 리스트
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printAverageHeight(
			List<MedicalCheck> medicalCheckList,
			String title ) {
		
		double totalH = 0;
		for (int i = 0; i < medicalCheckList.size(); i++) {
			totalH += medicalCheckList.get(i).getHeight();
		}
		
		totalH = totalH / medicalCheckList.size();
		// 화면으로 출력
		System.out.println(String.format("[%s]", title));
		System.out.println(String.format(" %.2f cm",totalH) );
		System.out.println();
	}
	
	/**
	 * 전체 학생의 평균 몸무게를 구해 화면으로 출력하는 메소드
	 * @param medicalCheckList 각 학생의 신체검사 정보를 모은 리스트
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printAverageWeight(
			List<MedicalCheck> medicalCheckList,
			String title) {
		
		// TODO printAverageHeight 를 참고해서 구현하면 됩니다.
		double totalW = 0;
		for (int i = 0 ; i < medicalCheckList.size(); i++  ){
			MedicalCheck mcheck = medicalCheckList.get(i);
			totalW += mcheck.getWeight();
		}
		
		totalW = totalW / medicalCheckList.size();
		// 화면으로 출력
		System.out.println(String.format("[%s]", title));
		System.out.println(String.format(" %.2f kg",totalW) );
		System.out.println();
	}
	
	/**
	 * 주어진 신체검사 정보 중 여학생들의 평균키를 화면으로 출력
	 * @param medicalCheckList 모든 학생들의 신체검사 정보
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printGirlsHeight(
			List<MedicalCheck> medicalCheckList, 
			String title) {
		
		// TODO printAverageHeight 를 참고해서 구현하면 됩니다.
		// 화면으로 출력
		// TODO 구현 힌트 : 메소드 재사용
	}
	
	/**
	 * 주어진 신체검사 정보 중 남학생들의 평균키를 화면으로 출력
	 * @param medicalCheckList 모든 학생들의 신체검사 정보
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printBoysHeight(
			List<MedicalCheck> medicalCheckList,
			String title) {
		
		// TODO printAverageHeight 를 참고해서 구현하면 됩니다.
		// 화면으로 출력
		// TODO 구현 힌트 : 메소드 재사용
	}
	
	/**
	 * 가장 키가 작은 학생을 화면에 출력
	 * @param medicalCheckList 각 학생의 신체검사 정보를 모은 리스트
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printLowestStudent(
			List<MedicalCheck> medicalCheckList, 
			String title) {
		
		// TODO 이곳에 구현을 넣으면 됩니다.

		// 화면으로 출력
		// TODO 아래와 같이 나오면 됩니다.
		// [가장 키가 작은 학생]
		//  2014 방은진 : 155.9
	}
	
	/**
	 * 가장 무거운 학생을 화면에 출력
	 * @param medicalCheckList 각 학생의 신체검사 정보를 모은 리스트
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printBiggestStudent(
			List<MedicalCheck> medicalCheckList,
			String title) {
		// TODO 이곳에 구현을 넣으면 됩니다.
		
		// 화면으로 출력
		// TODO 아래와 같이 나오면 됩니다.
		// [가장 무거운 학생]
		// 2008 장혁수 : 88.3
	}
	
	/**
	 * 키가 큰 순서대로 화면에 출력(내림차순)
	 * @param medicalCheckList 각 학생의 신체검사 정보를 모은 리스트
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printStudentByHeight(
			List<MedicalCheck> medicalCheckList,
			String title) {
		
		HeightComparator comp = new HeightComparator();
		Collections.sort(medicalCheckList, comp); // students.sort(comp);

		// 화면으로 출력
		System.out.printf("[%s]\n", title);
		for( int i = 0 ; i < medicalCheckList.size(); i++) {
			MedicalCheck aStudentData = medicalCheckList.get(i);
			System.out.println(String.format("%2d %4s %4s %3.1fcm",
					(i+1),
					aStudentData.getStudentID(),
					aStudentData.getName(),
					aStudentData.getHeight()));
		}
		System.out.println();
	}
	
	/**
	 * 몸무게가 가벼운 순서대로 화면에 출력(오름차순)
	 * @param medicalCheckList
	 * @param title
	 */
	private static void printStudentByWeight(
			List<MedicalCheck> medicalCheckList,
			String title) {

		Comparator<MedicalCheck> c = new WeightComparator();
		Collections.sort(medicalCheckList, c);

		// 화면으로 출력
		System.out.printf("[%s]\n", title);
		for (int i = 0; i < medicalCheckList.size(); i++) {
			MedicalCheck aStudentData = medicalCheckList.get(i);
			System.out.println(String.format("%2d %4s %4s %3.1fcm", (i + 1),
					aStudentData.getStudentID(), aStudentData.getName(),
					aStudentData.getWeight()));
		}
		System.out.println();
	}
	
	
	/**
	 * 몸무게를 비교하는 comparator
	 *
	 */
	static class WeightComparator implements Comparator<MedicalCheck> {

		public int compare(MedicalCheck o1, MedicalCheck o2) {
			
			double Weight1 = o1.getWeight();
			double Weight2 = o2.getWeight();
			if (Weight1 > Weight2) {
				return 1;
			} else if (Weight2 > Weight1) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * 키를 비교하는 comparator
	 *
	 */
	static class HeightComparator implements Comparator<MedicalCheck> {

		public int compare(MedicalCheck s1, MedicalCheck s2) {
			
			double h1 = s1.getHeight();
			double h2 = s2.getHeight();

			if (h1 > h2) {
				return -1;
			} else if (h2 > h1) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
