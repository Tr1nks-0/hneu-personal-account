package edu.hneu.studentsportal.service.impl

import edu.hneu.studentsportal.service.FileService
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths
import java.util.function.Function

class FileServiceSpockTest extends Specification {

    static final String FILE_NAME = 'file name'

    def fileService = new FileService()

    def 'should return imported file and stored to the temporary local storage' () {
        given:
            def multipartFileMock = Mock(MultipartFile)
            multipartFileMock.getBytes() >> new byte[0]
            multipartFileMock.getOriginalFilename() >> FILE_NAME
        when:
            def file = fileService.getFile(multipartFileMock)
        then:
            FILE_NAME == file.getName()
    }

    def 'should perform given operation on a file and then remove it' () {
        given:
            def file = new File(FILE_NAME)
            def functionMock = Mock(Function)
            def expected = Mock(Collection)
            functionMock.apply(file) >> expected
        when:
            def actual = fileService.<File>perform(file, functionMock)
        then:
            expected == actual
            !file.exists()
    }

    def cleanupSpec() {
        Files.deleteIfExists(Paths.get(FILE_NAME))
    }
}
