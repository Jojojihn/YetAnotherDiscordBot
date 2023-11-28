package database;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.List;
import java.util.Objects;

public class DBManager {
    private static final Logger logger
            = LoggerFactory.getLogger(DBManager.class);
    private static Connection conn = null;
    public void setup(JDA bot) throws SQLException {
        String url = Objects.requireNonNull(getClass().getResource("/Database.db")).toString();
        conn = DriverManager.getConnection("jdbc:sqlite:" +url);
        if(conn!=null){
            logger.info("Connected to Database");
        }
        sendSQL("CREATE TABLE IF NOT EXISTS servers (id BIGINT PRIMARY KEY,name VARCHAR(32))");
        List<Guild> guilds = bot.getGuilds();
        for(Guild g: guilds){
            String id = g.getId();
            String name = g.getName();
            boolean inDB = false;

            if(!inDB){
                sendSQL("INSERT INTO servers (id,name) VALUES ('"+id+"','"+name+"')");
                logger.info("Added server: "+name+" to the database");
            }
        }
    }

    public static void sendSQL(String sql){
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet sendSQLwithResult(String sql){
        Statement statement;
        try {
            statement = conn.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
