import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("loapu.common-conventions")
    id("com.github.johnrengelman.shadow")
}
val libs = the<LibrariesForLibs>()
dependencies {
    implementation(project(":common"))
}
tasks {
    assemble {
        dependsOn(shadowJar)
    }
    shadowJar {
        dependencies {
            include(dependency("${project.group}:.*"))
        }
        archiveClassifier.set("")
    }
}