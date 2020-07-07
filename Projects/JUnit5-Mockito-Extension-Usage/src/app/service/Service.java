package app.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import app.Repository;

public class Service {
    private Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }
    
    public Repository getRepository()
    {
    	return repository;
    }

    public List<String> getDataFilteredWithLengthLessThanFive() {
        try {
            return repository.getData().stream()
                    .filter(stuff -> stuff.length() < 5)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            return Arrays.asList();
        }
    }
}
