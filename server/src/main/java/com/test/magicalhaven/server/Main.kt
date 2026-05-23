package com.test.magicalhaven.server

import com.test.magicalhaven.server.config.AppConfig
import org.apache.catalina.startup.Tomcat
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import java.io.File

fun main() {
    val port = 8080
    val tomcat = Tomcat()

    // Create a temporary directory for Tomcat
    val tempDir = File(System.getProperty("java.io.tmpdir"), "tomcat-magic")
    tempDir.mkdirs()
    tomcat.setBaseDir(tempDir.absolutePath)
    tomcat.setPort(port)
    tomcat.connector // Force initialization of the connector

    // Add an empty context (Tomcat requires a context)
    val context = tomcat.addContext("", tempDir.absolutePath)

    // Initialize our "pure" Spring IoC container
    val springContext = AnnotationConfigWebApplicationContext()
    springContext.register(AppConfig::class.java)

    // Create the DispatcherServlet (the face of Spring MVC)
    val dispatcherServlet = DispatcherServlet(springContext)

    // Register DispatcherServlet in Tomcat
    val wrapper = Tomcat.addServlet(context, "dispatcher", dispatcherServlet)
    wrapper.setLoadOnStartup(1)
    wrapper.addMapping("/*") // Intercept all API requests

    // Start the server
    tomcat.start()
    println("=== MagicalHaven Server started on port \$port (PURE SPRING, NO BOOT) ===")
    tomcat.server.await()
}
