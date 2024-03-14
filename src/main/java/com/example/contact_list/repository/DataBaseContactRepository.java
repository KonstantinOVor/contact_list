package com.example.contact_list.repository;

import com.example.contact_list.exception.ContactNotFoundException;
import com.example.contact_list.mapper.ContactRowMapper;
import com.example.contact_list.model.Contact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
@Slf4j
@Repository
@RequiredArgsConstructor
public class DataBaseContactRepository  implements ContactRepository{
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Contact save(Contact contact) {
        log.debug("Calling DataBaseContactRepository save method, contact: {}", contact);

        String sql = "INSERT INTO contact (first_name, last_name, email, phone) VALUES ( ?, ?, ?, ?)";

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getPhone());
            return ps;
        });
        return contact;
    }


    @Override
    public void deleteById(Long id) {
        log.debug("Calling DataBaseContactRepository deleteById method, id: {}", id);

        String sql = "DELETE FROM contact WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Contact> findAll() {
        log.debug("Calling DataBaseContactRepository findAll method");

        String sql = "SELECT * FROM contact";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("Calling DataBaseContactRepository findById method, id: {}", id);

        String sql = "SELECT * FROM contact WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                )
        );
        return Optional.ofNullable(contact);
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Calling DataBaseContactRepository update method, contact: {}", contact);

        Contact oldContact = findById(contact.getId()).orElse(null);
        if (oldContact != null) {
            String sql = "UPDATE contact SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(),
                    contact.getPhone(), contact.getId());
            return contact;
        }
        log.warn("Contact with id {} not found", contact.getId());
        throw new ContactNotFoundException("Contact with id " + contact.getId() + " not found");
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("Calling DataBaseContactRepository batchInsert method, contacts: {}", contacts);

        String sql = "INSERT INTO contact (id, first_name, last_name, email, phone) VALUES (nextval('contact_id_seq'), ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, contacts, contacts.size(), (ps, contact) -> {
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getPhone());
        });
    }
}
