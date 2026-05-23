package com.test.magicalhaven.server

import com.test.magicalhaven.server.config.AppConfig
import org.apache.catalina.startup.Tomcat
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import java.io.File

fun main() {
    val port = 8080
    val tomcat = Tomcat()

    val tempDir = File(System.getProperty("java.io.tmpdir"), "tomcat-magic")
    tempDir.mkdirs()
    tomcat.setBaseDir(tempDir.absolutePath)
    tomcat.setPort(port)
    tomcat.connector 

    val context = tomcat.addContext("", tempDir.absolutePath)

    val springContext = AnnotationConfigWebApplicationContext()
    springContext.register(AppConfig::class.java)

    val dispatcherServlet = DispatcherServlet(springContext)

    val wrapper = Tomcat.addServlet(context, "dispatcher", dispatcherServlet)
    wrapper.setLoadOnStartup(1)
    wrapper.addMapping("/*")

    tomcat.start()
    println("=== MagicalHaven Server started on port $port (PURE SPRING, NO BOOT) ===")
    tomcat.server.await()
}
