package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise1 {

    @Test
    public void ageExtractorFromPersonUsingMethodReference() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, Integer> ageExtractor = (p -> p.getAge());

        assertEquals(33, ageExtractor.apply(person).intValue());
    }

    @Test
    public void sameAgesCheckerUsingBiPredicate() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Дмитрий", "Гущин", 33);
        Person person3 = new Person("Илья", "Жирков", 22);

        BiPredicate<Person, Person> sameAgesChecker = ((p1, p2) -> p1.getAge() == p2.getAge());

         assertTrue(sameAgesChecker.test(person1, person2));
         assertFalse(sameAgesChecker.test(person1, person3));
         assertFalse(sameAgesChecker.test(person2, person3));
    }

    private static String getFullName(Person person) {
        return person.getFirstName() + " " + person.getLastName();
    }

    private static BiFunction<Person, Person, Integer> createExtractorAgeOfPersonWithTheLongestFullName(Function<Person, String> fullNameFunction) {
        return new BiFunction<Person, Person, Integer>() {
            @Override
            public Integer apply(Person person, Person person2) {
                if (fullNameFunction.apply(person).length() >= fullNameFunction.apply(person2).length()) {
                    return person.getAge();
                } else {
                    return person2.getAge();
                }
            }
        };
    }

    @Test
    public void getAgeOfPersonWithTheLongestFullName() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Илья", "Жирков", 22);

        Function<Person, String> getFullName = Exercise1::getFullName;

        BiFunction<Person, Person, Integer> extractorAgeOfPersonWithTheLongestFullName = createExtractorAgeOfPersonWithTheLongestFullName(getFullName);

        assertEquals(33, extractorAgeOfPersonWithTheLongestFullName.apply(person1, person2).intValue());
    }
}
