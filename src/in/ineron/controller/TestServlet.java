package in.ineron.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(
		urlPatterns = { "/test" }, 
		initParams = { 
				@WebInitParam(name = "url", value = "jdbc:mysql:///octbatch"), 
				@WebInitParam(name = "user", value = "root"), 
				@WebInitParam(name = "password", value = "Seeraj1234")
		},loadOnStartup=10)
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
 private Connection connection=null;
 private PreparedStatement pstmt=null;
	
	static {
		
	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded sucessfully..");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void init() throws ServletException{
		System.out.println("Config object is created and got the values from config objects");
		
		String jdbcUrl=getInitParameter("url");
		String user=getInitParameter("user");
		String password=getInitParameter("password");
		
	
		try {
			connection=DriverManager.getConnection(jdbcUrl,user,password);
			if(connection!=null)
				System.out.println("Connection Etablished sucessfully..");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String username = request.getParameter("username");
		String userage = request.getParameter("userage");
		String useraddress = request.getParameter("useradds");
		
		String sqlInsertQuery="insert into student( sname,sage,saddress) values(?,?,?)";
		if(connection!=null) {
			
			
			try {
				
				if(connection!=null)
					
					pstmt=connection.prepareStatement(sqlInsertQuery);
				if(pstmt!=null) {
					
					pstmt.setString(1, username);
					pstmt.setInt(2,Integer.parseInt(userage));
					pstmt.setString(3, useraddress);
					
					
				}
				
				if(pstmt!=null) {
					
					int rowAffected =pstmt.executeUpdate();
					
					PrintWriter out=null;
					out=response.getWriter();
					
					if(rowAffected==1) {
						
						
						
						out.println("<h1 style='color:green; text-align:center;'>REGISTRATION SUCCESSFULLY </h1>");
						
						
						
					}else {
						
						out.println("<h1style='color:red' text-align:'center'>REGISTRATION FAILED TRY IT AGAIN FOLLOWING LINK</h1>");
						out.println("<a href='./reg.html'>REGISTRATION</a>");
					}
					out.close();
				}
				
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
	}
			
		
		
		@Override
		public void destroy() {
			
			
			try {
				if(connection!=null) 
					connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			
			
		}
		
		
	}


