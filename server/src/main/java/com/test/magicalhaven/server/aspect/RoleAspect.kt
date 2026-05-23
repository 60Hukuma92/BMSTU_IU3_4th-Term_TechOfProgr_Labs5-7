package com.test.magicalhaven.server.aspect

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.server.ResponseStatusException

@Aspect
@Component
class RoleAspect {

    @Around("@annotation(requiresRole)")
    fun checkRole(joinPoint: ProceedingJoinPoint, requiresRole: RequiresRole): Any? {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
        val request: HttpServletRequest? = requestAttributes?.request

        val userRole = request?.getHeader("X-Role") ?: "USER" // Default to USER

        if (requiresRole.role == "ADMIN" && userRole != "ADMIN") {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: Requires ADMIN role")
        }

        return joinPoint.proceed()
    }
}
