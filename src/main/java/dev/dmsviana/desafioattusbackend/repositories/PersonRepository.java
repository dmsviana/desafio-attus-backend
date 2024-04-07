package dev.dmsviana.desafioattusbackend.repositories;

import dev.dmsviana.desafioattusbackend.domain.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
}
