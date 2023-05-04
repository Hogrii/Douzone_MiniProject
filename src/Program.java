import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import DAO.DeptDAO;
import DAO.EapplyDAO;
import DAO.EmpDAO;
import DAO.ErankDAO;
import DAO.EstateDAO;
import DAO.HolidayDAO;
import DAO.Rest_holidayDAO;
import DTO.EapplyDTO;

public class Program {
	public static int loginId;
	public static String loginName;

	DeptDAO dept = new DeptDAO();
	EapplyDAO eapply = new EapplyDAO();
	EmpDAO emp = new EmpDAO();
	ErankDAO erank = new ErankDAO();
	EstateDAO estate = new EstateDAO();
	HolidayDAO holiday = new HolidayDAO();
	Rest_holidayDAO rest = new Rest_holidayDAO();

	Scanner sc = new Scanner(System.in);

	public void run() {
		while (true) {
			System.out.println("*******************");
			System.out.println("원하시는 메뉴를 선택해주세요");
			System.out.println("1. 로그인  0. 종료");
			System.out.print(">> ");
			String menu = sc.nextLine();
			switch (menu) {
			case "1":
				login(); // 로그인
				break;
			case "0":
				System.exit(0); // 종료
				break;
			default:
				break;
			}
		}
	}

	// 로그인
	public void login() {
		System.out.print("사번을 입력해주세요 : ");
		int empno = Integer.parseInt(sc.nextLine());
		System.out.print("이름을 입력해주세요 : ");
		String ename = sc.nextLine();
		if (emp.checkLogin(empno, ename)) {
			loginId = empno;
			loginName = ename;
			loginMenu();
		} else {
			System.out.println("뭔가가 잘못되었습니다");
		}
	}

	// 로그인시 나오는 메뉴
	public void loginMenu() {
		while (true) {
			System.out.println(loginName + "님 환영합니다.");
			System.out.println("이용하실 메뉴를 선택해주세요.");
			System.out.println("1. 휴가신청  2. 휴가신청목록  3. 휴가결제  4. 잔여 휴가 일수  0. 로그아웃");
			System.out.print(">> ");
			String menuNum = sc.nextLine();
			switch (menuNum) {
			case "1":
				applyVacation(); // 휴가 신청
				break;
			case "2":
				getMyVacationList(); // 휴가 신청 목록
				break;
			case "3":
				vacationConfirm(); // 휴가 결제
				break;
			case "4":
				getResdtDay();
				break;
			case "0":
				logout(); // 로그아웃
				return;
			default:
				break;
			}
		}
	}
	
	// 휴가 신청
	public void applyVacation() {
		EapplyDTO apDTO = new EapplyDTO();
		apDTO.setEmpno(loginId);
		System.out.println("휴가 유형을 선택하세요");
		System.out.println("1. 공가  2. 병가  3. 경조사");
		System.out.print(">> ");
		apDTO.setHolidayno(Integer.parseInt(sc.nextLine()));
		System.out.print("휴가 시작일을 입력해주세요 (YYYY-MM-DD) : ");
		apDTO.setStart_date(Date.valueOf(sc.nextLine()));
		System.out.print("휴가 종료일을 입력해주세요 (YYYY-MM-DD) : ");
		apDTO.setEnd_date(Date.valueOf(sc.nextLine()));
		System.out.print("휴가 사유를 입력해주세요 : ");
		apDTO.setReason(sc.nextLine());
		
		// 신청한 휴가일수
		int vacationDay = eapply.getVacationDay(apDTO.getStart_date(), apDTO.getEnd_date());
		System.out.println("vacationDay : " + vacationDay);
		// 나의 잔여휴가일수
		int restVacationDay = rest.getRestDay(loginId);
		System.out.println("restVacationDay : " + restVacationDay);
		
		if(restVacationDay-vacationDay > 0) {
			eapply.applyVacation(apDTO);			
		}else {
			System.out.println("잔여 휴가일이 부족합니다");
		}
	}
	
	// 휴가 신청 목록
	public void getMyVacationList() {
		List<EapplyDTO> vacationList = eapply.getMyVacationList(loginId);
		System.out.println("신청하신 휴가리스트입니다");
		printVacation(vacationList);
		System.out.println("사용할 메뉴를 선택해주세요");
		System.out.println("1. 수정  2. 삭제  0. 취소");
		System.out.print(">> ");
		String menuNum = sc.nextLine();
		switch (menuNum) {
		case "1":
			updateMyVacation(); // 내 휴가 수정
			break;
		case "2":
			deleteMyVacation(); // 내 휴가 삭제
			break;
		default:
			break;
		}
	}

	// 내 휴가 수정
	public void updateMyVacation() {
		System.out.println("수정하실 휴가 번호를 입력해주세요");
		System.out.print(">>");
		int applyno = Integer.parseInt(sc.nextLine());
		
		EapplyDTO bfApDTO = eapply.getMyVacation(applyno);
		
		int dateCheck = eapply.checkVacationDay(bfApDTO.getApplyno(), bfApDTO.getStart_date());
		System.out.println("dateCheck : " + dateCheck);
		if(dateCheck>0) {
			System.out.println("이미 지난 휴가입니다");
			return;
		}
		
		EapplyDTO apDTO = new EapplyDTO();

		apDTO.setApplyno(applyno);
		System.out.println("휴가 유형을 변경하세요");
		System.out.println("1. 공가  2. 병가  3. 경조사");
		System.out.print(">> ");
		apDTO.setHolidayno(Integer.parseInt(sc.nextLine()));
		System.out.print("휴가 시작일을 변경해주세요 (YYYY-MM-DD) : ");
		apDTO.setStart_date(Date.valueOf(sc.nextLine()));
		System.out.print("휴가 종료일을 변경해주세요 (YYYY-MM-DD) : ");
		apDTO.setEnd_date(Date.valueOf(sc.nextLine()));
		System.out.print("휴가 사유를 변경해주세요 : ");
		apDTO.setReason(sc.nextLine());
		
		eapply.updateMyVacation(apDTO);
		
		List<EapplyDTO> vacationList = eapply.getMyVacationList(loginId);
		printVacation(vacationList);
	}
	
	// 내 휴가 삭제
	public void deleteMyVacation() {
		System.out.println("삭제하실 휴가 번호를 입력해주세요");
		System.out.print(">>");
		int applyno = Integer.parseInt(sc.nextLine());
		eapply.deleteMyVacation(applyno);
		List<EapplyDTO> vacationList = eapply.getMyVacationList(loginId);
		printVacation(vacationList);
	}
	
	// 휴가 결제
	public void vacationConfirm() {
		EapplyDTO apDTO = new EapplyDTO();
		List<EapplyDTO> vacationList = eapply.vacationList(loginId);
		printVacation(vacationList);
		System.out.println("결재를 처리할 휴가신청번호를 입력해주세요");
		System.out.print(">> ");
		int applyno = Integer.parseInt(sc.nextLine());
		System.out.println("휴가신청 상태를 변경해주세요");
		System.out.println("0. 대기  1. 승인  2. 반려");
		System.out.print(">> ");
		int stateno = Integer.parseInt(sc.nextLine());
		eapply.vacationConfirm(applyno, stateno); // 수정
		
		EapplyDTO afApDTO = eapply.getMyVacation(applyno);
		int afStateno = afApDTO.getStateno();
		if(afStateno==1) { // 승인시 차감
			restVacationApply(applyno);			
		}
	}	

	// 휴가일수 차감
	public void restVacationApply(int applyno) {
		EapplyDTO apDTO = eapply.getMyVacation(applyno);
		int vacationDay = eapply.getVacationDay(applyno, apDTO.getStart_date(), apDTO.getEnd_date());
		rest.restVacationApply(apDTO.getEmpno(), vacationDay);
	}
	
	// 내 잔여휴가일수 확인
	public void getResdtDay() {
		System.out.println(loginName + "님의 잔여 휴가일수는 " + rest.getRestDay(loginId) + "일 입니다");
	}

	// 로그아웃
	public void logout() {
		loginId = 0;
		loginName = "";
	}
	
	// 휴가 프린트
	public void printVacation(List<EapplyDTO> vacationList) {
		for (EapplyDTO vacation : vacationList) {
			System.out.println(vacation.toString());
		}
	}
}
