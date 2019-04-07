package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vo.GuestVo;
//BaseDao클래스를 상속받고 GuestDao인터페이스를 구현하는
//GuestDaoImpl
//BaseDao클래스의 DB연결 관련 메서드를 사용할 수 있고
//ex)getConnection()
//GuestDao인터페이스에서 선언, 명명했던 메서드들을 정의.
//최종적으로 GuestDaoImpl을 JSP 속 scriptlet에서 불러와
//객체화하여 이 메서드들을 사용할 수 있도록하는 것이 목표

public class GuestDaoImpl extends BaseDao implements GuestDao  {

	public GuestDaoImpl(String dbUser, String dbPass) {
		//superClass인 BaseDao의 생성자를 가져와 사용
		super(dbUser, dbPass);
	}

	@Override
	public List<GuestVo> getList() {
		//방명록들을 불러와 리스트로 만들어 반환해주는 메서드를 정의.
		Connection conn = null;
		//커넥션 객체
		//기능을 수행할 때마다 커넥션 객체를 생성하여 DB에 접근.
		Statement stmt = null;
		//Statement 객체 
		//DB에 내리는 명령 그 자체에 해당.
		ResultSet rs = null;
		//ResultSet 객체
		//아래에서 executeQuery메서드를 이용하여 DB에 명령을 내리면
		//ResultSet 형태로 반환을 해줌.
		//그 속엔 DB가 준 값들이 들어있다.
		List<GuestVo> list = new ArrayList();
		//GuestVo 객체들을 담을 리스트
		//ResultSet을 분해하여 GuestVo형태로 만든 뒤,
		//list에 담아 최종적으로 list를 리턴시킬 것이다.

		try {
			conn = getConnection();
			//커넥션 객체에 BaseDao에서 만들어놨던 getConnection메서드를 사용하여
			//DB에 연결하여 객체를 만든 뒤 담음.
			String sql = "SELECT no,name,password,content FROM guest ORDER BY no DESC";
			//sql문을 문자열 형태로 선언, 정의
			stmt = conn.createStatement();
			//커넥션 객체로부터 statement 객체를 만들어오는 createStatement메서드를 사용하여
			//stmt에 statement 객체를 담음
			rs = stmt.executeQuery(sql);
			//명령객체 stmt의 메서드 executeQuery에 위에서 만들어 두었던 sql문을 담아
			//명령을 내려 그 반환값들을 ResultSet인 rs에 담아준다.
			while(rs.next()) {
				//rs의 다음값이 있으면 반복.
				//기본적으로 rs는 첫 값이 없어서 rs.next를 한번 해준 뒤부터 값이 나옴.
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String content = rs.getString(4);
				//rs의 각 값들을 vo에 넣기 위해 형태를 맞춰서 변수로 만듬.
				GuestVo vo = new GuestVo(no,name,password,content);
				//위에서 만든 변수들을 생성자 파라미터로 넣어서 GuestVo형태인 vo로 만듬.
				list.add(vo);
				//vo를 list에 넣어준다.
			}

		}catch (SQLException e) {
			//SQL 진행 관련된 에러가 발생하면 catch문으로 들어와 그 에러에 대해 출력해준다.
			System.err.println("SQL Error!");
			System.err.println("ERROR : " + e.getMessage());
		}finally {
			//try든 catch든 수행이 다 끝난 뒤, finally를 진행
			//rs, stmt, conn을 모두 close해 다음 DB작업에 차질이 없게 닫아준다.
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//close에 문제가 생길 경우 에러 출력.
				System.err.println("SQL Error!");
				System.err.println("ERROR : " + e.getMessage());
			}
		}
		return list;
		//최종적으로 받아온 방명록 리스트들을 출력.
	}
	
	@Override
	public List<GuestVo> getListAsc() {
		//방명록들을 불러와 리스트로 만들어 반환해주는 메서드를 정의.
		Connection conn = null;
		//커넥션 객체
		//기능을 수행할 때마다 커넥션 객체를 생성하여 DB에 접근.
		Statement stmt = null;
		//Statement 객체 
		//DB에 내리는 명령 그 자체에 해당.
		ResultSet rs = null;
		//ResultSet 객체
		//아래에서 executeQuery메서드를 이용하여 DB에 명령을 내리면
		//ResultSet 형태로 반환을 해줌.
		//그 속엔 DB가 준 값들이 들어있다.
		List<GuestVo> list = new ArrayList();
		//GuestVo 객체들을 담을 리스트
		//ResultSet을 분해하여 GuestVo형태로 만든 뒤,
		//list에 담아 최종적으로 list를 리턴시킬 것이다.

		try {
			conn = getConnection();
			//커넥션 객체에 BaseDao에서 만들어놨던 getConnection메서드를 사용하여
			//DB에 연결하여 객체를 만든 뒤 담음.
			String sql = "SELECT no,name,password,content FROM guest ORDER BY no ASC";
			//sql문을 문자열 형태로 선언, 정의
			stmt = conn.createStatement();
			//커넥션 객체로부터 statement 객체를 만들어오는 createStatement메서드를 사용하여
			//stmt에 statement 객체를 담음
			rs = stmt.executeQuery(sql);
			//명령객체 stmt의 메서드 executeQuery에 위에서 만들어 두었던 sql문을 담아
			//명령을 내려 그 반환값들을 ResultSet인 rs에 담아준다.
			while(rs.next()) {
				//rs의 다음값이 있으면 반복.
				//기본적으로 rs는 첫 값이 없어서 rs.next를 한번 해준 뒤부터 값이 나옴.
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String content = rs.getString(4);
				//rs의 각 값들을 vo에 넣기 위해 형태를 맞춰서 변수로 만듬.
				GuestVo vo = new GuestVo(no,name,password,content);
				//위에서 만든 변수들을 생성자 파라미터로 넣어서 GuestVo형태인 vo로 만듬.
				list.add(vo);
				//vo를 list에 넣어준다.
			}

		}catch (SQLException e) {
			//SQL 진행 관련된 에러가 발생하면 catch문으로 들어와 그 에러에 대해 출력해준다.
			System.err.println("SQL Error!");
			System.err.println("ERROR : " + e.getMessage());
		}finally {
			//try든 catch든 수행이 다 끝난 뒤, finally를 진행
			//rs, stmt, conn을 모두 close해 다음 DB작업에 차질이 없게 닫아준다.
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//close에 문제가 생길 경우 에러 출력.
				System.err.println("SQL Error!");
				System.err.println("ERROR : " + e.getMessage());
			}
		}
		return list;
		//최종적으로 받아온 방명록 리스트들을 출력.
	}

	@Override
	public boolean write(GuestVo vo) {
		//방명록을 작성하는 write메서드
		//파라미터로 GuestVo인 vo를 받아와야 한다.
		Connection conn = null;
		PreparedStatement pstmt = null;
		//Statement 처럼 명령 그 자체인 객체 PreparedStatement
		//Statement와 다른 점
		//PreparedStatement를 이용하면 sql문에 ? 를 넣은 뒤
		//? 위치에 자바 변수를 담아서 사용할 수 있다.
		//SQL문에 WHERE 등의 조건이 필요한 경우에는 대부분 PreparedStatement 를 이용하면 편리
		int sqlCount = 0;
		//INSERT, DELETE, UPDATE 등 성공유무가 존재하는, 성공유무를 반환하는 SQL문의 경우
		//SQL명령을 실행하면 반환값을 int형으로 반환
		//성공 시 1, 실패시 0을 반환한다.
		boolean success = false;
		//실패 성공 유무를 최종적으로 반환하기 위해 boolean 변수를 따로 만듬.

		try {
			conn = getConnection();
			String sql = "INSERT INTO guest VALUES(seq_guest_pk.nextval,?,?,?)";
			//pstmt를 사용하기 위해서 ?를 각각 name,password,content 자리에 넣음
			//no는 시퀀스nextval을 이용하여 자동적으로 1씩 증가
			pstmt = conn.prepareStatement(sql);
			//커넥션 객체로부터 PreparedStatement를 만들어주는 prepareStatement메서드
			//sql문을 파라미터로 넣어주어야한다.
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());
			//pstmt의 sql문 속 ?부분을 인덱스 1,2,3으로 지정하여
			//파라미터인 vo에서 getter를 이용, name,password,content를 가져와 삽입해준다.
			sqlCount = pstmt.executeUpdate();
			//pstmt의 내장 메서드 executeUpdate를 이용하여 명령을 실행
			//명령의 성공값을 받아서 int형 변수 sqlCount에 담음.
			success = sqlCount==1;
			//sqlCount가 1과 비교하여 같으면 true, 다르면 false를 success에 담아준다.

		}catch (SQLException e) {
			System.err.println("SQL Error!");
			System.err.println("ERROR : " + e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					//stmt와 마찬가지로 pstmt도 닫아줘야한다.
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.err.println("SQL Error!");
				System.err.println("ERROR : " + e.getMessage());
			}
		}

		return success;
	}

	@Override
	public boolean delete(Long no) {
		//방명록을 삭제하는 delete메서드
		//파라미터로 방명록의 번호를 받아와야해서 Long no받아옴
		Connection conn = null;
		PreparedStatement pstmt = null;
		int sqlCount = 0;
		boolean success = false;

		try {
			conn = getConnection();
			String sql = "DELETE FROM guest WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			//no는 Long이므로 setLong
			sqlCount = pstmt.executeUpdate();
			success = sqlCount==1;

		}catch (SQLException e) {
			System.err.println("SQL Error!");
			System.err.println("ERROR : " + e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.err.println("SQL Error!");
				System.err.println("ERROR : " + e.getMessage());
			}
		}

		return success;
	}

	@Override
	public boolean modify(GuestVo vo) {
		//방명록을 수정하는 modify 메서드
		Connection conn = null;
		PreparedStatement pstmt = null;
		int sqlCount = 0;
		boolean success = false;
		try {
			conn = getConnection();
			String sql = "UPDATE guest SET name=?, content=? WHERE no = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			
			sqlCount = pstmt.executeUpdate();
			success = sqlCount==1;

		}catch (SQLException e) {
			System.err.println("SQL Error!");
			System.err.println("ERROR : " + e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.err.println("SQL Error!");
				System.err.println("ERROR : " + e.getMessage());
			}
		}

		return success;
	}
	
	

}
