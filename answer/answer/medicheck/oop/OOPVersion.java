package answer.medicheck.oop;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import answer.medicheck.oop.context.MediCheckContext;
import answer.medicheck.oop.filter.Filter;
import answer.medicheck.oop.map.Mapper;
import answer.medicheck.oop.sorting.CompositeComparator;
import answer.medicheck.oop.sorting.GenderComparator;
import answer.medicheck.oop.sorting.HeightComparator;
import answer.medicheck.oop.sorting.NameComparator;
import quiz.medicheck.MedicalCheck;

/**
 * 
 * 1. filtering - 일련의 집합(collection)에서 원하는 element들만 뽑아내는 역할.
 * 
 * 2. mapping - 각각의 대상마다 특정 값을 뽑아내는 역할
 * 
 * 3. sorting - java에서 제공해줌.
 * 
 * 
 * 해볼 것
 * 
 * 1. printStudentByWeight와 printStudentByHeight 를 추상화해서 하나의 메소드를 사용하게 해봅시다.
 * 
 * 2. 다중정렬
 *
 */
public class OOPVersion {

	public static void main(String[] args) throws FileNotFoundException {
		
		List<MedicalCheck> medicalCheckList = loadMedicalData();
		
		MediCheckContext ctx = new MediCheckContext("answer/medicheck/oop/config.txt");
		
		Mapper<Double, MedicalCheck> heightMapper = ctx.<Double>getMapper("height");
		Mapper<Double, MedicalCheck> weightMapper = ctx.<Double>getMapper("weight");
		Mapper<Double, MedicalCheck> bmiMapper = ctx.<Double>getMapper("bmi");
		
		double avrWeight = average(medicalCheckList, weightMapper);
		double avrHeight = average( medicalCheckList, heightMapper);
		
		System.out.println("전체 학생의 평균 키     : " + avrHeight);
		System.out.println("전체 학생의 평균 몸무게 : " + avrWeight);
		
		// 성별 평균키 출력
		Filter<MedicalCheck> girlFilter = ctx.getFilter("girl");
		Filter<MedicalCheck> boyFilter = ctx.getFilter("boy");
		double avrGirlHeight = average ( filtering ( medicalCheckList, girlFilter), heightMapper);
		double avrBoyHeight = average ( filtering ( medicalCheckList, boyFilter), weightMapper);
		
		System.out.println("여학생의 평균 키 : " + avrGirlHeight);
		System.out.println("남학생의 평균 키 : " + avrBoyHeight);
		
		
		double avrGrilBmi = average ( filtering(medicalCheckList, girlFilter), bmiMapper);
		System.out.println("여학생들 bmi 평균 : " + avrGrilBmi);
		System.out.println();

		
		// 키가 가장 작은 학생, 가장 무거운 학생
//		printLowestStudent(medicalCheckList, "가장 키가 작은 학생");		
//		printBiggestStudent(medicalCheckList, "가장 무거운 학생");

		
		// 정렬 - 키의 오름차순으로 출력하기 .... < ... < ....
		printStudents("키순서(오름차순)",
				medicalCheckList, 
				ctx.getComparator("height"), 
				new OutputStreamWriter(System.out) );
		
		// 정렬 - 몸무게의 오름차순으로 출력하기
		printStudents("몸무게순서(오름차순)",
				medicalCheckList, 
				ctx.getComparator("weight"), 
				new OutputStreamWriter(System.out) );

		// 정렬 - 여학생들만 몸무게의 내림차순으로 출력하기
		
		printStudents("여학생 몸무게순서(내림차순)",
				filtering(medicalCheckList, girlFilter),
				ctx.getComparator("weight.desc"),
				new OutputStreamWriter(System.out) );
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
		
		// ORDER BY gender, height desc, name asc
		CompositeComparator ccomp = new CompositeComparator(
				new GenderComparator(), 
				new HeightComparator(false), 
				new NameComparator());
		
		printStudents("다중정렬(여자먼저 > 키내림차순> 이름오름차순)", 
				medicalCheckList, 
				ccomp, 
				new OutputStreamWriter(System.out) );
		
	}
	
	public static List<MedicalCheck> filtering ( List<MedicalCheck> mChecks, Filter<MedicalCheck> filter ) {
		
		ArrayList<MedicalCheck> checks = new ArrayList<MedicalCheck>();
		for(int i = 0 ; i < mChecks.size(); i++) {
			MedicalCheck mc = mChecks.get(i);
			if ( filter.isAcceptable(mc)) {
				checks.add(mc);
			}
		}
	
		return checks;
	}
	/**
	 * 
	 * @param mChecks
	 * @return
	 */
	public static double average (List<MedicalCheck> mChecks, Mapper<Double, MedicalCheck> mapper ) {
		double total = 0.0;
		
		for( int i = 0 ;  i < mChecks.size(); i++) {
			total += mapper.map(mChecks.get(i));
		}
		
		return total / mChecks.size();
	}
	
	/**
	 * input.txt 를 읽어서 학생들의 신체검사 데이터를 List로 반환하는 메소드
	 * @return 신체검사 정보가 담긴 List
	 * @throws FileNotFoundException
	 */
	private static List<MedicalCheck> loadMedicalData() throws FileNotFoundException {

		ArrayList<MedicalCheck> medicalCheckList = new ArrayList<MedicalCheck>();
		
		ClassLoader cloader = OOPVersion.class.getClassLoader();
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
	 * 주어진 out을 PrintWriter로 캐스팅 또는 wrapping하는 메소드입니다.
	 * 
	 * PrintWriter는 print(..)와 println(...)의 메소드들이 있어서 문자들을 출력할때 자주 사용됩니다.
	 * 
	 * 현재 건네받은 out 변수가 참조하는 인스턴스의 타입이 PrintWriter이면 명시적으로 캐스팅을 하고
	 * 그렇지 않으면 PrintWriter로 감싸서 반환해줍니다. 
	 * @param out
	 * @return
	 */
	final private static PrintWriter asPrintWriter ( Writer out) {
		PrintWriter writer = null;
		if ( out instanceof PrintWriter) {
			writer = (PrintWriter) out;
		} else {
			writer = new PrintWriter ( out ) ;
		}
		return writer;
	}
	
	/**
	 * 주어진 신체검사 정보들을 정렬해서 출력하는 메소드입니다.
	 * 
	 * @param title - 타이틀
	 * @param mChecks - 신체검사 정보들
	 * @param comp - 신체검사 정보 정렬에 사용되는 comparator
	 * @param out - 출력 스트림
	 */
	private static void printStudents (String title,
			List<MedicalCheck> mChecks , 
			Comparator<MedicalCheck> comp, 
			Writer out) {
		/*
		 * printStudents 를 호출하는 쪽(여기서는 main메소드)에서는 구체적인 
		 * 출력 방향(파일인지, 콘솔인지, 네트워크인지)을 지정해서 호출하고,
		 * 이 메소드안에서는 출력의 실제 구현을 추상 클래스인 Writer 타입으로 참조함으로써
		 * 구체적인 출력방향에 대해서는 알 필요가 없게 됩니다.
		 * 
		 * out 파라미터를 통해서 스트림 참조를 건네받은 printStudents메소드 안에서는
		 * Writer.println 등의 메소드로 뭔가를 스트림에 써넣을 수 있다는 것만 알면 되고
		 * 써 넣은 데이터들이 어디로 흘러가는지는 신경쓸 필요가 없습니다.
		 * 
		 * 이를 통해서 호출하는 쪽(main 메소드)과 호출되는 쪽(printStudents)의 책임을 분리해서
		 * 유연한 코드를 작성할 수 있습니다.
		 * 
		 * 
		 */
		PrintWriter writer = asPrintWriter(out);
		
		Collections.sort(mChecks, comp);
		
		Iterator<MedicalCheck> itr = mChecks.iterator();
		int cnt = 1;
		writer.printf("[%s]\n", title);
		while ( itr.hasNext() ) {
			MedicalCheck mc = itr.next();
			writer.println(String.format("%2d %4s %4s %3.1fcm %3.1fkg",
					cnt++,
					mc.getStudentID(),
					mc.getName(),
					mc.getHeight(),
					mc.getWeight()));
		}
		writer.println();
		writer.flush();
		// 여기서는 스트림을 닫지 않습니다.
		// writer 변수가 참조하는 스트림이 printStudents 메소드 안에서 생성되지 않았기 때문에
		// 함부로 아래와같이 스트림을 닫는 것은 오작동을 초래할 수 있습니다.
		// 스트림은 반드시 생성한 쪽에서만 close() 하도록 코드를 작성하는 것이 좋습니다.
		//writer.close();
	}

}
