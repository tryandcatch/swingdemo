import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Huangxiutao on 2017/7/10.
 */
public class DBUtils {

    public static Connection getMySqlCon(String ip,Integer port,String db,String userName,String passWord){
        Connection connection=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://"+ip+":"+port+"/"+db;
            System.out.println("数据库url:"+url);
            connection= DriverManager.getConnection(url,userName,passWord);
        } catch (ClassNotFoundException e) {
            System.out.println("mysql驱动加载失败！");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


}
