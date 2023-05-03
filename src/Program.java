import DAO.DeptDAO;
import DAO.EapplyDAO;
import DAO.EmpDAO;
import DAO.ErankDAO;
import DAO.EstateDAO;
import DAO.HolidayDAO;
import DAO.Rest_holidayDAO;

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
	
	
	public void run() {
		while(true) {
			if(emp.checkLogin()) {
				System.out.println("로그인 성공");
			}else {
				System.out.println("뭔가가 잘못되었습니다");				
			}
		}
	}
	

}
