package com.thoughtworks.users.controller;

import com.google.common.io.Files;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.thoughtworks.users.UserApplication;
import com.thoughtworks.users.model.User;
import com.thoughtworks.users.repository.UserRepository;
import org.apache.http.HttpStatus;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

@ActiveProfiles(profiles = "integration")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(UserApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class UserControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Value(value = "${local.server.port}")
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void should_create_new_user() throws IOException {
        given()
                .body(loadUserPayload())
                .contentType(ContentType.JSON)
                .when()
                .post("/users/")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", Is.is("Juntao Qiu"))
                .body("email", Is.is("juntao.qiu@gmail.com"));
    }

    private String loadUserPayload() throws IOException {
        return Files.toString(new File(getClass().getResource("/payloads/user.json").getPath()), Charset.defaultCharset());
    }

    @Test
    public void should_get_user_list() {
        User u1 = UserBuilder.start().name("Juntao").password("password").email("juntao.qiu@gmail.com").build();
        User u2 = UserBuilder.start().name("Xiaofeng").password("password").email("xiaofeng.wang@gmail.com").build();

        userRepository.save(Arrays.asList(u1, u2));

        when()
                .get("/users/")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", hasItems("Juntao", "Xiaofeng"));
    }

    static class UserBuilder {
        private User user;

        public static UserBuilder start() {
            return new UserBuilder();
        }

        private UserBuilder() {
            user = new User();
        }

        public UserBuilder name(String name) {
            user.setName(name);
            return this;
        }

        public UserBuilder email(String email) {
            user.setEmail(email);
            return this;
        }

        public UserBuilder password(String password) {
            user.setPassword(password);
            return this;
        }

        public User build() {
            return user;
        }
    }
}