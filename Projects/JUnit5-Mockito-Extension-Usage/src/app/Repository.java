package app;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public interface Repository {
    public List<String> getData() throws SQLException;
}
