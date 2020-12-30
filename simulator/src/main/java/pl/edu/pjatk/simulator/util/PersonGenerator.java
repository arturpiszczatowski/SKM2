package pl.edu.pjatk.simulator.util;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.repository.StationRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PersonGenerator {
    private static final Faker faker = new Faker(new Locale("pl-PL"));
    private static final Random generator = new Random();
    private static StationRepository stationRepository;

    public static Person generateNewPerson(Station station) {
        Name name = faker.name();
        OptionalLong any = generator.longs(1,15).findAny();
        Station destinationStationOrdinal = stationRepository.getOne(any.getAsLong());

        return new Person(name.firstName(), name.lastName(), destinationStationOrdinal);
    }

    public static List<Person> generatePeople(Station station) {
        int count = generator.ints(1, 8).findAny().orElse(0);

        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> generateNewPerson(station))
                .collect(Collectors.toList());
    }
}
