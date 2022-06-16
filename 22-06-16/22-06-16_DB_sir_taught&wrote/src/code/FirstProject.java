package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Project_DTO.bookDTO;
import Project_DTO.memberDTO;
import Project_DTO.recommendDTO;
import service.BookService;
import service.MemberService;


public class FirstProject {
	static BookFind bf =new BookFind();
	
	//member 기능
	static MemberService memberService = MemberService.getInstance();
	
	//book 기능
	static BookService bookService = BookService.getInstance();

	public static void main(String[] args) throws SQLException {
		Scanner sc = new Scanner(System.in);		
		
		
			
		int input;
		while(true){
			System.out.println("┌────────────────────────────────────────────-┐");
			System.out.println("│1.도서관안내 2.회원가입 3.로그인  4.책조회 5.책반납 0. 프로그램종료  │");
			System.out.println("└─────────────────────────────────────────────┘");

			input = Integer.parseInt(sc.nextLine());

			if(input == 1){
				System.out.println("도서관안내");
				Guide();
			}else if(input ==2){
				System.out.println("회원가입");
				Join();
			}else if(input ==3){
				System.out.println("로그인");
				Login();
			}else if(input ==4){
				System.out.println("책조회");
				Search();
			}else if(input ==5){
				System.out.println("책반납");
				ReturnBook();
				//평점추가
			}else if(input ==0){
				System.out.println("프로그램종료");
				break;
			}else
				System.out.println("잘못된입력");
		}
	}

	static void Join(){
		Scanner sc = new Scanner(System.in);
		String id, pw1, pw2, nm, tel, reg1, reg2, addr;
		System.out.print("──────회원가입──────");
		System.out.print("id입력");
		id = sc.next();
		System.out.print("pw입력");
		pw1 = sc.next();
		System.out.print("pw재입력");
		pw2 = sc.next();

		System.out.print("이름입력");
		nm = sc.next();

		System.out.print("전화번호입력");
		tel = sc.next();

		System.out.print("주민번호앞자리입력");
		reg1 = sc.next();
		System.out.print("주민번호뒷자리입력");
		reg2 = sc.next();
		System.out.print("주소");
		addr = sc.next();

		if(pw1.equals(pw2)){
			System.out.println("패스워드o");
		}else
			System.out.println("패스워드x");

		//System.out.println(id + " "+pw1 + " " + nm + " "+ tel + " "+reg1 + " "+reg2 + " "+ addr + " ");
	}

	static void Login() throws SQLException{
		Scanner sc = new Scanner(System.in);
		//일반계정 예시
//		String id = "a555";
		String pw = "5555";
		
		//관리자계정 예시
//		String admin = "a555";
//		String pw = "5555";

		String logId, logPw;
		System.out.println("로그인id입력");

		logId = sc.next();

		System.out.println("로그인pw입력");
		logPw = sc.next();
				
		memberDTO member = new memberDTO();
		member.setMem_id(logId);
		member.setMem_pw(logPw);
		
		//로그인 기능 수행
		member = memberService.selectLogin(member);
		
		System.out.println("member : " + member.toString());
		
		//관리자 아니면 일반회원 로그인
//		if(logId.equals("a555")){//일반로그인
//			if(logPw.equals("5555")){
//				System.out.println("일반회원 로그인성공");
//			}else
//				System.out.println("일반회원 비밀번호 안맞음");
//		}else if(logId.equals("admin")){	//관리자로그인
//			if(logPw.equals("admin")){
//				System.out.println("관리자 로그인성공");
//			}else
//				System.out.println("관리자 비밀번호 안맞음");
//		}else{
//			System.out.println("계정정보없음");
//		}
	}

	static void Search(){
//		String book = "DB책";
//		String wri = "홍길동";
//		String type = "소설";
		
		boolean logCheck = false;

//		String a;
		System.out.println("책제목or저자or도서분류");
		
		Scanner sc = new Scanner(System.in);
		String SearchName = sc.next();
		
		bookDTO bookDTO = bookService.Find(SearchName);
		
		System.out.println("bookDTO : " + bookDTO.toString());
		
		System.out.print("도서번호 \t 도서명 \t 저자 \t도서유형 \t 도서소개 ");
//		System.out.println("\t 추천도서별점 \t 추천도서별명");
		
		System.out.println(bookDTO.getBook_num() +"\t"+ bookDTO.getBook_nm() +"\t"+  bookDTO.getWriter() +"\t"+ bookDTO.getBook_type() +"\t"+ bookDTO.getBook_intro());
		
		
		//a = sc.next();
//		bf.Find();
		
		
	}
	static void ReturnBook(){
		Scanner sc = new Scanner(System.in);
		String ph1 = "010-0000-0000";
		String bookNum1 = "A12345678";
		String ph2, bookNum2;

		System.out.println("핸드폰번호 입력");
		ph2 = sc.next();

		System.out.println("책번호입력");
		bookNum2 = sc.next();

		if(ph1.equals(ph2) && bookNum1.equals(bookNum2)){
			System.out.println("책정보찾음");
			System.out.println("반납여부 ?");
		}else
			System.out.println("맞는책정보 없음, 전화번호 or 책번호 확인해주세요");
	}

	static void Guide(){
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@192.168.143.17:1521:xe";
		String user = "bhj";
		String password = "java";

		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null; //SQL명령객체



		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,password);

			String sql = " SELECT * "
					   + " FROM guide ";
			/*
			String sql = " SELECT MEM_ID , MEM_NAME , MEM_MILEAGE "
					   + " FROM MEMBER "
					   + " WHERE SUBSTR(MEM_REGNO2,1,1) = '2' ";*/

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);



			while(rs.next()){
				String ut = rs.getString(1);
				String ud = rs.getString(2);
				String addr = rs.getString(3);
				String dir = rs.getString(4);
				String floor = rs.getString(5);
				String rent = rs.getString(6);

				System.out.println("이용시간안내"+ ut);
				System.out.println("휴관일"+ ud);
				System.out.println("도서관주소"+ addr);
				System.out.println("찾아오는길"+ dir);
				System.out.println("층수안내"+ floor);
				System.out.println("대출반납안내"+ rent);


			}
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try{
					if(rs!=null) rs.close();
					if(stmt!=null) stmt.close();
					if(conn!=null) conn.close(); 
				}catch(Exception e){
					e.printStackTrace();
				}		
			}
	}
}
