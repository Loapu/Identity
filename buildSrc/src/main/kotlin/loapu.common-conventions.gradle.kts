import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import java.io.ByteArrayOutputStream

plugins {
    `java-library`
}

val libs = the<LibrariesForLibs>()
fun branch(): String {
    val os = ByteArrayOutputStream()
    project.exec {
        commandLine("git", "branch", "--show-current")
        standardOutput = os
    }
    val branch = String(os.toByteArray()).trim()
    return branch
}

fun fullVersion(): String {
    val suffix = if (branch().contains("release/")) "" else "-SNAPSHOT"
    return rootProject.ext.get("apiVersion").toString() + "." + rootProject.ext.get("patchVersion") + suffix
}
rootProject.ext.set("majorVersion", 1)
rootProject.ext.set("minorVersion", 0)
rootProject.ext.set("patchVersion", 0)
rootProject.ext.set(
    "apiVersion", rootProject.ext.get("majorVersion").toString() + "." + rootProject.ext.get("minorVersion")
)
rootProject.ext.set("fullVersion", fullVersion())
group = "dev.loapu"
version = rootProject.ext.get("fullVersion").toString()

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    implementation(libs.configurate.yaml)
    implementation(libs.adventure)
    implementation(libs.adventure.logger)
    implementation(libs.minimessage)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
tasks {
    withType<JavaCompile> { options.encoding = Charsets.UTF_8.name() }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        expand(
            "version" to project.version,
            "group" to project.group,
            "author" to "Loapu",
            "pluginName" to rootProject.name,
            "name" to project.name,
            "description" to "Loapu's Template Plugin"
        )
    }
    base {
        archivesName.set("${rootProject.name.uppercaseFirstChar()}-${project.name.uppercaseFirstChar()}")
    }
}