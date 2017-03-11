import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.multipart.commons.CommonsMultipartResolver

beans {

    xmlns([
            context: 'http://www.springframework.org/schema/context',
            mvc    : 'http://www.springframework.org/schema/mvc'
    ])

    context.'annotation-config'()
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.config')
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.repository')
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.service')
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.controller')
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.parser')
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.listener')

    mvc.'resources'(mapping: '/resources/**', location: 'classpath:/theme/')
    mvc.'resources'(mapping: '/profile/**', location: 'classpath:${profile.photo.location}')
    mvc.'resources'(mapping: '/individual-plan/**', location: 'classpath:${uploaded.files.location}')

    messageSource(ReloadableResourceBundleMessageSource) {
        basename = 'classpath:messages'
        defaultEncoding = 'UTF-8'
    }

    multipartResolver(CommonsMultipartResolver)
}