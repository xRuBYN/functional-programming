package com.endava.internship.service;

import com.endava.internship.domain.Privilege;
import com.endava.internship.domain.User;
import com.endava.internship.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    @Override
    public List<String> getFirstNamesReverseSorted(List<User> users) {
        List<String> listOfName = users.stream().map(x -> x.getFirstName()).collect(Collectors.toList());
        return listOfName.stream().sorted((String u1, String u2) -> (u2.compareTo(u1))).collect(Collectors.toList());
    }

    @Override
    public List<User> sortByAgeDescAndNameAsc(final List<User> users) {
        List<User> list = users.stream().sorted((User u1,User u2) ->(u1.getFirstName().compareTo(u2.getFirstName()))).sorted((User u1, User u2) -> u2.getAge().
                compareTo(u1.getAge())).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Privilege> getAllDistinctPrivileges(final List<User> users) {
        List<List<Privilege>> list = users.stream().map(x -> x.getPrivileges()).collect(Collectors.toList());
        List<Privilege> list1 = list.stream().flatMap(x -> x.stream().distinct()).distinct().collect(Collectors.toList());
        return list1;
    }

    @Override
    public Optional<User> getUpdateUserWithAgeHigherThan(final List<User> users, final int age) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Map<Integer, List<User>> groupByCountOfPrivileges(final List<User> users) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public double getAverageAgeForUsers(final List<User> users) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Optional<String> getMostFrequentLastName(final List<User> users) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<User> filterBy(final List<User> users, final Predicate<User>... predicates) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String convertTo(final List<User> users, final String delimiter, final Function<User, String> mapFun) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Map<Privilege, List<User>> groupByPrivileges(List<User> users) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Map<String, Long> getNumberOfLastNames(final List<User> users) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
