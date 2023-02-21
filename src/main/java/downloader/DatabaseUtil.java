package downloader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
//    https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.32
   // docker install _>
//    https://mvnrepository.com/artifact/com.mysql/mysql-connector-j/8.0.32
    public static void saveToDatabase(String name,String dateNow,String downlaodUrl) throws SQLException {
        // try catch -> try with resources
         final String INSERTSQL = "insert into download_information(name,download_url,download_date)" +
                "values(?,?,?)";

        try(Connection connection = MySQLDatabase.getConnection()){
//            Statement -> DOESN'T CACHES QUERY, PRONE TO SQL INJECTION
            // 2MB
            PreparedStatement preparedStatement = connection.prepareStatement(INSERTSQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, downlaodUrl); //extra characters used in sql injection is replaced with empty string
            preparedStatement.setString(3, dateNow);

            boolean result = preparedStatement.execute();
            if (result) System.out.println("successfully added to database");
            else System.out.println("failed to add to database");
        }
    }
    public static List<DownloadInformation> getAllDownloadInformation() throws SQLException{
        final String SELECT_SQL = "select name,download_url, download_date from download_information";
        List<DownloadInformation> downloadList = new ArrayList<DownloadInformation>();
        try(Connection connection = MySQLDatabase.getConnection()){

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_SQL);
            while(rs.next()){
                downloadList.add(new DownloadInformation(rs.getString(1),rs.getString(2),rs.getString(3)));
            }

        }
        return downloadList;
    }
}
