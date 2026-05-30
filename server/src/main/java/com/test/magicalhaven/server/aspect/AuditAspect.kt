package com.test.magicalhaven.server.aspect

import com.test.magicalhaven.server.service.AuditService
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class AuditAspect(private val auditService: AuditService) {

    @Before("within(com.test.magicalhaven.server.controller..*)")
    fun logAction(joinPoint: JoinPoint) {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
        val request: HttpServletRequest? = requestAttributes?.request

        val userRole = request?.getHeader("X-Role") ?: "USER"
        val action = if (request != null) {
            "${request.method} ${request.requestURI}"
        } else {
            joinPoint.signature.name
        }

        auditService.logAction(action, userRole)
    }
}
