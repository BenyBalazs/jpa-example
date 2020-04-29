package person;

import com.github.javafaker.Faker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.ZoneId;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();

        try {
            for(int i = 0; i<1000; i++) {
                em.getTransaction().begin();
                Person tmp = randomPerson();
                em.persist(tmp);
                em.getTransaction().commit();
                System.out.println(tmp);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            em.close();
            emf.close();
        }
    }

    static Faker faker = new Faker();

    private static Person randomPerson() {

        Person person = Person.builder()
                .name(faker.name().fullName())
                .dob(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .gender(faker.options().option(Person.Gender.class))
                .address(randomAddress())
                .email(faker.internet().emailAddress())
                .profession(faker.company().profession())
                .build();
        return person;
    }

    private static Address randomAddress() {
        Address address = Address.builder()
                .country(faker.address().country())
                .state(faker.address().state())
                .city(faker.address().city())
                .streetAddress(faker.address().streetAddress())
                .zip(faker.address().zipCode())
                .build();
        return address;
    }
}
