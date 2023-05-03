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
				login();
				break;
			case "0":
				System.exit(0);
				break;
			default:
				break;
			}
		}
	}

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

	public void loginMenu() {
		while (true) {
			System.out.println(loginName + "님 환영합니다.");
			System.out.println("이용하실 메뉴를 선택해주세요.");
			System.out.println("1. 휴가신청  2. 휴가신청목록  3. 휴가결제  0. 로그아웃");
			System.out.print(">> ");
			String menuNum = sc.nextLine();
			switch (menuNum) {
			case "1":
				applyVacation();
				break;
			case "2":
				List<EapplyDTO> vacationList = eapply.getMyVacationList(loginId);
				printVacation(vacationList);
				break;
			case "3":
				break;
			case "0":
				logout();
				return;
			default:
				break;
			}
		}
	}

	public void applyVacation() {
		EapplyDTO apDTO = new EapplyDTO();
		apDTO.setEmpno(loginId);
		System.out.println("휴가 유형을 선택하세요");
		System.out.println("1. 공가  2. 병가  3. 경조사");
		System.out.print(">> ");
		apDTO.setHolidayno(Integer.parseInt(sc.nextLine()));
		System.out.print("휴가 시작일을 입력해주세요 (YY-MM-DD) : ");
		apDTO.setStart_date(Date.valueOf(sc.nextLine()));
		System.out.print("휴가 종료일을 입력해주세요 (YY-MM-DD) : ");
		apDTO.setEnd_date(Date.valueOf(sc.nextLine()));
		System.out.print("휴가 사유를 입력해주세요 : ");
		apDTO.setReason(sc.nextLine());
		
		eapply.applyVacation(apDTO);
		
	}
	
	public void logout() {
		loginId = 0;
		loginName = "";
	}

	public void printVacation(List<EapplyDTO> vacationList) {
		for (EapplyDTO vacation : vacationList) {
			System.out.println(vacation.toString());
		}
	}
}
