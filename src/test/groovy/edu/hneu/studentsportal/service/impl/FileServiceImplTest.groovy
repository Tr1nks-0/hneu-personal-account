package edu.hneu.studentsportal.service.impl

import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class FileServiceImplTest extends Specification {

    static final String FILE_NAME = 'file name'

    def multipartFileMock = Mock(MultipartFile)

    def fileService = new FileServiceImpl()

    def 'should return imported file and stored to the temporary local storage' () {
        given:
            multipartFileMock.getBytes() >> new byte[0]
            multipartFileMock.getOriginalFilename() >> FILE_NAME
        when:
            def file = fileService.getFile(multipartFileMock)
        then:
            FILE_NAME == file.getName()
    }

    def cleanupSpec() {
        Files.deleteIfExists(Paths.get(FILE_NAME))
    }
}
