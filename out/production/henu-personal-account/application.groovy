beans {

    xmlns context:"http://www.springframework.org/schema/context"

    context.annotation-config()
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.repository')
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.service')
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.controller')
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.parser')
    context.'component-scan'('base-package': 'edu.hneu.studentsportal.listener')
}