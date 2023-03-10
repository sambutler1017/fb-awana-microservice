package com.awana.app.user.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.awana.app.user.client.domain.User;
import com.awana.app.user.client.domain.WebRole;
import com.awana.app.user.client.domain.request.UserGetRequest;
import com.awana.common.exception.NotFoundException;
import com.awana.test.factory.annotations.AwanaDaoTest;
import com.awana.utility.AwanaDAOTestConfig;
import com.google.common.collect.Sets;

/**
 * Test class for the User DAO.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@Sql("/scripts/user/userProfileDAO/init.sql")
@ContextConfiguration(classes = AwanaDAOTestConfig.class)
@AwanaDaoTest
public class UserProfileDAOTest {

    @Autowired
    private UserProfileDAO dao;

    @Test
    public void testGetUserList() {
        List<User> user = dao.getUsers(new UserGetRequest());

        assertEquals(3, user.size(), "User Size should be 3");
        assertEquals("Test", user.get(0).getFirstName(), "User 1 first name");
        assertEquals("Bill", user.get(1).getFirstName(), "User 2 first name");
        assertEquals("Fake", user.get(2).getFirstName(), "User 3 first name");
    }

    @Test
    public void testGetUserListWithFilter() {
        UserGetRequest request = new UserGetRequest();
        request.setWebRole(Sets.newHashSet(WebRole.USER));
        List<User> user = dao.getUsers(request);

        assertEquals(2, user.size(), "User Size should be 2");
        assertEquals("Test", user.get(0).getFirstName(), "User 1 first name");
        assertEquals("Bill", user.get(1).getFirstName(), "User 2 first name");
    }

    @Test
    public void testGetUserListNoResults() {
        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet("noUser@mail.com"));

        assertTrue(dao.getUsers(request).isEmpty(), "User list should be empty");
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = dao.getUserById(1);

        assertEquals("Test", user.getFirstName(), "First name");
        assertEquals("User", user.getLastName(), "Last name");
        assertEquals("test@mail.com", user.getEmail(), "Email");
        assertEquals(WebRole.USER, user.getWebRole(), "Web Role");
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        NotFoundException e = assertThrows(NotFoundException.class, () -> dao.getUserById(12));
        assertEquals("User not found for id: '12'", e.getMessage(), "Message should match");
    }

    @Test
    public void testInsertUser() throws Exception {
        List<User> beforeInsertList = dao.getUsers(new UserGetRequest());

        assertEquals(3, beforeInsertList.size(), "Size should be 3");

        User user = new User();
        user.setFirstName("NewUserInsert");
        user.setLastName("LastName");
        user.setEmail("newEmail@mail.com");
        user.setWebRole(WebRole.ADMIN);

        int newUserId = dao.insertUser(user);
        User insertedUser = dao.getUserById(newUserId);

        assertEquals(4, insertedUser.getId(), "New user Id should be 4");
        assertEquals("NewUserInsert", insertedUser.getFirstName(), "User first name");
        assertEquals("LastName", insertedUser.getLastName(), "User last name");
        assertEquals("newEmail@mail.com", insertedUser.getEmail(), "User Email");
        assertEquals(WebRole.ADMIN, insertedUser.getWebRole(), "User Role");
    }

    @Test
    public void testUpdateUserValues() throws Exception {
        User userProfile = new User();
        assertEquals("Test", dao.getUserById(1).getFirstName());
        userProfile.setFirstName("Randy");
        userProfile.setWebRole(WebRole.SYSTEM);

        User returnedUser = dao.updateUserProfile(1, userProfile);
        assertEquals(userProfile.getFirstName(), returnedUser.getFirstName());
        assertEquals(WebRole.SYSTEM, returnedUser.getWebRole());
    }

    @Test
    public void testUpdateUniqueEmail() {
        User userProfile = new User();
        userProfile.setEmail("Fake123@mail.com");
        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class,
                                                         () -> dao.updateUserProfile(1, userProfile));
        assertTrue(e.getMessage().contains("Duplicate entry 'Fake123@mail.com'"));
    }
}
