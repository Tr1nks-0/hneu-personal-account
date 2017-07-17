package edu.hneu.studentsportal.service.impl

import edu.hneu.studentsportal.domain.User
import edu.hneu.studentsportal.enums.UserRole
import edu.hneu.studentsportal.repository.UserRepository
import edu.hneu.studentsportal.service.UserService
import spock.lang.Specification

class UserServiceSpockTest extends Specification {

    static final String USER_ID = 'id'
    static final UserRole USER_ROLE = UserRole.ADMIN

    def userRepositoryMock = Mock(UserRepository)
    def userMock = Mock(User)

    def userService = new UserService(
            userRepository: userRepositoryMock
    )

    def 'should save user'() {
        when:
            userService.save(userMock)
        then:
            1 * userRepositoryMock.save(userMock)
    }

    def 'should return user by given id'() {
        given:
            userRepositoryMock.findOne(USER_ID) >> userMock
        when:
            def user = userService.getUserForId(USER_ID)
        then:
            userMock == user
    }

    def 'should create and save new user'() {
        when:
            def user = userService.create(USER_ID, USER_ROLE)
        then:
            USER_ID == user.getId()
            USER_ROLE == user.getRole()
            1 * userRepositoryMock.save(*_)
    }

    def 'should return admins list'() {
        given:
            userRepositoryMock.findAllByRole(UserRole.ADMIN) >> [userMock]
        when:
            def admins = userService.getAdmins()
        then:
            [userMock] == admins
    }
}
