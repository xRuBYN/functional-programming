package com.endava.internship.service;

import com.endava.internship.domain.Privilege;
import com.endava.internship.domain.User;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserServiceImpl implements UserService {

    @Override
    public List<String> getFirstNamesReverseSorted(List<User> users) {
        return users.stream()
                .map(User::getFirstName)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    @Override
    public List<User> sortByAgeDescAndNameAsc(final List<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(User::getAge).reversed().thenComparing(User::getFirstName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Privilege> getAllDistinctPrivileges(final List<User> users) {
        return users.stream()
                .map(User::getPrivileges)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());

    }

    @Override
    public Optional<User> getUpdateUserWithAgeHigherThan(final List<User> users, final int age) {
        return users.stream()
                .filter(user -> user.getAge() > age && user.getPrivileges().contains(Privilege.UPDATE))
                .findFirst();
    }

    @Override
    public Map<Integer, List<User>> groupByCountOfPrivileges(final List<User> users) {
        return users.stream()
                .map(u -> Pair.of(u.getPrivileges().size(),u))
                .collect(Collectors.groupingBy(Pair::getKey,Collectors.mapping(Pair::getValue,Collectors.toList())));
    }

    @Override
    public double getAverageAgeForUsers(final List<User> users) {
        return users.stream()
                .mapToInt(User::getAge)
                .average()
                .orElse(-1);
    }

    @Override
    public Optional<String> getMostFrequentLastName(final List<User> users) {
        Map<String,Integer> map = users.stream()
                .map(u-> Pair.of(u.getLastName(),1))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.summingInt(Pair::getValue)));
        if(map.values().stream().distinct().collect(Collectors.toList()).size() == 1) {
            return Optional.empty();
        }
        Optional<Map.Entry<String, Integer>> maxEntry = map.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue));
        return Optional.ofNullable(maxEntry.get().getKey());
    }

    @Override
    public List<User> filterBy(final List<User> users, final Predicate<User>... predicates) {
        return users.stream()
                .filter(Stream.of(predicates)
                        .reduce(x -> true, Predicate::and))
                .collect(Collectors.toList());
    }

    @Override
    public String convertTo(final List<User> users, final String delimiter, final Function<User, String> mapFun) {
        return users.stream()
                .map(mapFun)
                .collect(Collectors.joining(delimiter));
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
        return users.stream()
                .map(u -> Pair.of(u.getLastName(), 1))
                .collect(Collectors.groupingBy(Pair::getKey,Collectors.summingLong(Pair::getValue)));
    }
}
