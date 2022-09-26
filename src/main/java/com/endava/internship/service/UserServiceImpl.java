package com.endava.internship.service;

import com.endava.internship.domain.Privilege;
import com.endava.internship.domain.User;
import org.apache.commons.lang3.tuple.Pair;

import javax.print.DocFlavor;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserServiceImpl implements UserService {

    @Override
    public List<String> getFirstNamesReverseSorted(List<User> users) {
        return users.stream().map(u -> u.getFirstName()).sorted((String u1, String u2) -> (u2.compareTo(u1))).collect(Collectors.toList());
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
        Optional<User> user = users.stream().filter(x->x.getAge() > age).filter(u -> u.getPrivileges().contains(Privilege.UPDATE)).findFirst();
        return user;
    }

    @Override
    public Map<Integer, List<User>> groupByCountOfPrivileges(final List<User> users) {
        return users.stream().map(u -> Pair.of(u.getPrivileges().size(),u))
                .collect(Collectors.groupingBy(Pair::getKey,Collectors.mapping(Pair::getValue,Collectors.toList())));
    }

    @Override
    public double getAverageAgeForUsers(final List<User> users) {
        return users.stream().mapToInt(u -> u.getAge()).average().orElse(-1);
    }

    @Override
    public Optional<String> getMostFrequentLastName(final List<User> users) {
        Map<String,Integer> map = users.stream().map(u-> Pair.of(u.getLastName(),1))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.summingInt(Pair::getValue)));
        if(map.values().stream().distinct().collect(Collectors.toList()).size() == 1) {
            return Optional.empty();
        }
        Optional<Map.Entry<String, Integer>> maxEntry = map.entrySet()
                .stream()
                .max((Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) -> e1.getValue()
                        .compareTo(e2.getValue())
                );
        return Optional.ofNullable(maxEntry.get().getKey());
    }

    @Override
    public List<User> filterBy(final List<User> users, final Predicate<User>... predicates) {
        return users.stream().filter(Stream.of(predicates).reduce(x -> true, Predicate::and)).collect(Collectors.toList());
    }

    @Override
    public String convertTo(final List<User> users, final String delimiter, final Function<User, String> mapFun) {
        return users.stream().map(mapFun).collect(Collectors.joining(delimiter));
    }

    @Override
    public Map<Privilege, List<User>> groupByPrivileges(List<User> users) {
        return users.stream()
                .flatMap(u -> u.getPrivileges().stream()
                        .map(privilege -> Pair.of(privilege,u)))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())));
    }

    @Override
    public Map<String, Long> getNumberOfLastNames(final List<User> users) {
       Map<String, Long> map = users.stream().map(u -> Pair.of(u.getLastName(), 1))
                .collect(Collectors.groupingBy(Pair::getKey,Collectors.summingLong(Pair::getValue)));
        return map;
    }
}
